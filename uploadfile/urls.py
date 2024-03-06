from django.contrib import admin
from django.urls import path
from filemanagment import views

urlpatterns = [
    path('upload-csv/', views.create_database, name='upload_csv'),
    path('get-database-metadata/<str:db_name>/', views.get_database_metadata_by_name, name='get-database-metadata'),  # Add <str:db_name> parameter
]
