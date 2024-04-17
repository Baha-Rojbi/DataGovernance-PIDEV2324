package tn.esprit.gouvernance_donnees_backend.implementation.interfaces.exportation;

import org.springframework.web.multipart.MultipartFile;

public interface IFileProcessService {
    public String processFile(MultipartFile file, String description) throws Exception;


}
