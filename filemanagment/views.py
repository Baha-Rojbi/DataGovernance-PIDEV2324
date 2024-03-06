from django.http import JsonResponse, HttpResponseBadRequest
import pandas as pd
import psycopg2
from django.views.decorators.csrf import csrf_exempt
from statistics import mean, median, stdev, variance, mode
from scipy.stats import skew, kurtosis
import time
from collections import Counter
import pdfkit
#import yappi
import cProfile
import pstats
from django.http import HttpRequest  # Import HttpRequest

def get_database_metadata(conn, db_name):
    try:
        
        # Connect to the specific database using db_name
        cursor = conn.cursor()

        # Get list of tables in the specific database
        cursor.execute(f"SELECT table_name FROM information_schema.tables WHERE table_schema = 'public';")
        tables = [table[0] for table in cursor.fetchall()]

        # Generate HTML content for the report specific to the database
        report_data = []
        for table in tables:
            table_data = {
                "table_name": table,
                "metadata": []
            }
            cursor.execute(f"SELECT * FROM {table};")
            metadata = cursor.fetchall()
            for row in metadata:
                table_data["metadata"].append(list(row))  # Convert row to list
            report_data.append(table_data)

        return report_data

    except psycopg2.Error as err:
        raise Exception(f"PostgreSQL Error: {err}")

@csrf_exempt
def create_database(request):
    if request.method == 'POST' and request.FILES.get('csv_file') :
        csv_file = request.FILES['csv_file']
        print('CSV file:', csv_file)
        if csv_file is None:
            return HttpResponse('No CSV file uploaded.', status=400)

        try:
           # yappi.start()  # Start profiling
            df = pd.read_csv(csv_file)

           
            conn = psycopg2.connect(
                dbname='profilemetadata',
                user='postgres',
                password='1234',
                host='localhost',
                port='5432'
            )
            cursor = conn.cursor()


            for column_name in df.columns:
                column_data = df[column_name]

                if column_data.dtype == 'object':
                    column_type = 'TEXT'
                    unique_values = column_data.dropna().unique()
                    num_unique_values = len(unique_values)
                    value_counts = dict(Counter(column_data.dropna()))
                    mode_value = column_data.mode().iloc[0]
                    mode_frequency = column_data.value_counts().max()
                    frequency_of_other_values = dict(column_data.value_counts())
                    missing_values_count = str(column_data.isnull().sum())
                else:
                    column_type = 'NUMERIC'
                    try:
                        column_data = pd.to_numeric(column_data.dropna())
                        column_min = str(column_data.min())
                        column_max = str(column_data.max())
                        column_mean = str(column_data.mean())
                        column_median = str(column_data.median())
                        column_stdev = str(stdev(column_data))
                        column_variance = str(variance(column_data))
                        mode_value = str(column_data.mode().values[0]) if len(column_data.mode()) > 0 else None
                        skewness = str(column_data.skew())
                        kurtosis = str(column_data.kurtosis())
                        interquartile_range = str(column_data.quantile(0.75) - column_data.quantile(0.25))
                        data_type = str(column_data.dtype)
                        missing_values_count = str(column_data.isnull().sum())
                    except ValueError:
                        column_min = None
                        column_max = None
                        column_mean = None
                        column_median = None
                        column_stdev = None
                        column_variance = None
                        mode_value = None
                        skewness = None
                        kurtosis = None
                        interquartile_range = None
                        data_type = None
                        missing_values_count = None

                create_table_query = f"CREATE TABLE IF NOT EXISTS {column_name} (id SERIAL PRIMARY KEY, metadata_name TEXT, metadata_value TEXT);"
                cursor.execute(create_table_query)

                if column_data.dtype == 'object':
                    cursor.execute("INSERT INTO {} (metadata_name, metadata_value) VALUES ('{}', '{}');".format(column_name, 'unique_values_count', num_unique_values))
                    cursor.execute("INSERT INTO {} (metadata_name, metadata_value) VALUES ('{}', '{}');".format(column_name, 'mode', mode_value))
                    cursor.execute("INSERT INTO {} (metadata_name, metadata_value) VALUES ('{}', '{}');".format(column_name, 'mode_frequency', mode_frequency))
                    for value, count in value_counts.items():
                        cursor.execute("INSERT INTO {} (metadata_name, metadata_value) VALUES ('{}', '{}');".format(column_name, value, count))
                else:
                    cursor.execute("INSERT INTO {} (metadata_name, metadata_value) VALUES ('{}', '{}');".format(column_name, 'min', column_min))
                    cursor.execute("INSERT INTO {} (metadata_name, metadata_value) VALUES ('{}', '{}');".format(column_name, 'max', column_max))
                    cursor.execute("INSERT INTO {} (metadata_name, metadata_value) VALUES ('{}', '{}');".format(column_name, 'mean', column_mean))
                    cursor.execute("INSERT INTO {} (metadata_name, metadata_value) VALUES ('{}', '{}');".format(column_name, 'median', column_median))
                    cursor.execute("INSERT INTO {} (metadata_name, metadata_value) VALUES ('{}', '{}');".format(column_name, 'stdev', column_stdev))
                    cursor.execute("INSERT INTO {} (metadata_name, metadata_value) VALUES ('{}', '{}');".format(column_name, 'variance', column_variance))
                    cursor.execute("INSERT INTO {} (metadata_name, metadata_value) VALUES ('{}', '{}');".format(column_name, 'mode', mode_value))
                    cursor.execute("INSERT INTO {} (metadata_name, metadata_value) VALUES ('{}', '{}');".format(column_name, 'skewness', skewness))
                    cursor.execute("INSERT INTO {} (metadata_name, metadata_value) VALUES ('{}', '{}');".format(column_name, 'kurtosis', kurtosis))
                    cursor.execute("INSERT INTO {} (metadata_name, metadata_value) VALUES ('{}', '{}');".format(column_name, 'interquartile_range', interquartile_range))
                    cursor.execute("INSERT INTO {} (metadata_name, metadata_value) VALUES ('{}', '{}');".format(column_name, 'data_type', data_type))
                    cursor.execute("INSERT INTO {} (metadata_name, metadata_value) VALUES ('{}', '{}');".format(column_name, 'missing_values_count', missing_values_count))

            conn.commit()
            generate_report()
            cursor.close()
            conn.close()
            
            #yappi.get_func_stats().print_all()  # Print profiling results
            #yappi.get_func_stats().save("profile_results.txt")
            #yappi.get_thread_stats().print_all()  # Print additional thread stats (optional)
            #yappi.stop()  # Stop profiling
            
            return JsonResponse({'message': 'Database created successfully.'})

        except psycopg2.Error as err:
            return JsonResponse({'error': f"PostgreSQL Error: {err}"}, status=500)
        except pd.errors.ParserError as err:
            return JsonResponse({'error': f"CSV Parsing Error: {err}"}, status=400)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    return HttpResponseBadRequest('No CSV file uploaded ')
def generate_report():
    try:
        # Fixed database name
        db_name = "profilemetadata"

        # Connect to psycopg2 server
        conn = psycopg2.connect(
            dbname=db_name,
            user='postgres',  # Default PostgreSQL user (adjust if needed)
            password='1234',  # Default PostgreSQL password (adjust if needed)
            host='localhost',  # Default host
            port='5432'       # Default port
        )
        cursor = conn.cursor()

        # Get list of tables in the specific database
        cursor.execute("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public';")
        tables = [table[0] for table in cursor.fetchall()]

        # Generate HTML content for the report specific to the database
        html_content = f"<h1>Metadata Report for Database: {db_name}</h1>"
        for table in tables:
            html_content += f"<h2>Table: {table}</h2>"
            cursor.execute(f"SELECT * FROM {table};")
            metadata = cursor.fetchall()
            for row in metadata:
                html_content += f"<p>{row}</p>"

        # Close connection
        conn.close()

        # Specify the path to wkhtmltopdf
        config = pdfkit.configuration(wkhtmltopdf='C:\\Program Files\\wkhtmltopdf\\bin\\wkhtmltopdf.exe')

        # Generate PDF from HTML content
        pdf_filename = f"{db_name}.pdf"
        pdfkit.from_string(html_content, pdf_filename, configuration=config)

        return JsonResponse({'message': 'Report generated successfully.'})

    except psycopg2.Error as err:
        return JsonResponse({'error': f"PostgreSQL Error: {err}"}, status=500)


@csrf_exempt
def get_database_metadata_by_name(request, db_name):
    try:
        # Connect to DB using db_name
        conn = psycopg2.connect(
            dbname=db_name,
            user='postgres',  # Default PostgreSQL user (adjust if needed)
            password='1234',  # Default PostgreSQL password (adjust if needed)
            host='localhost',  # Default host
            port='5432'       # Default port
        )
        cursor = conn.cursor()

        # Fetch metadata
        metadata = get_database_metadata(conn, db_name)

        return JsonResponse({'metadata': metadata})

    except psycopg2.Error as err:
        return JsonResponse({'error': f"PostgreSQL Error: {err}"}, status=500)
    except Exception as e:
        return JsonResponse({'error': str(e)}, status=500)
def profile_create_database(request: HttpRequest):  # Add request argument
    # Create a cProfile object
    profiler = cProfile.Profile()
    
    # Start profiling
    profiler.enable()
    
    # Call the create_database method with the request argument
    create_database(request)
    
    # Stop profiling
    profiler.disable()
    
    # Create a Stats object from the profiler
    stats = pstats.Stats(profiler)
    
    # Sort the profiling results by cumulative time
    stats.sort_stats('cumulative')
    
    # Print the profiling results
    stats.print_stats()

# Call the profiling function with a dummy request object
profile_create_database(HttpRequest())
