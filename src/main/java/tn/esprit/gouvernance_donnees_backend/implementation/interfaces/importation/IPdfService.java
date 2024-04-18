package tn.esprit.gouvernance_donnees_backend.implementation.interfaces.importation;

public interface IPdfService {
    public byte[] generateDataTablePdf(Long tableId);
}
