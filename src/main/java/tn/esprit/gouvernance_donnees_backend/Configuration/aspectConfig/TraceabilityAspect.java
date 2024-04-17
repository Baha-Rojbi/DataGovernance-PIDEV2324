package tn.esprit.gouvernance_donnees_backend.Configuration.aspectConfig;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.gouvernance_donnees_backend.implementation.services.tracelog.TraceLogService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
public class TraceabilityAspect {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TraceLogService traceLogService;

    @Around("execution(* tn.esprit.gouvernance_donnees_backend.controllers.importation.MetaDataContoller.uploadFile(..)) && args(file, description, ..)")
    public Object logMetadataExtraction(ProceedingJoinPoint joinPoint, MultipartFile file, String description) throws Throwable {
        // Retrieve the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null) ? authentication.getName() : "Unknown User";

        // Log metadata extraction details
        logger.info("[{}] Metadata extraction started for file: {} with description {}. Uploaded by: {}",
                LocalDateTime.now(), file.getOriginalFilename(), description, username);

        // Call the original method
        Object result = joinPoint.proceed();

        // Log metadata extraction completion
        logger.info("[{}] Metadata extraction completed successfully for file: {}",
                LocalDateTime.now(), file.getOriginalFilename());

        // Save log in the database
        traceLogService.saveLog("Metadata extraction completed", file.getOriginalFilename(), description, username);

        return result;
    }


    @AfterReturning(pointcut = "execution(* tn.esprit.gouvernance_donnees_backend.controllers.importation.MetaDataContoller.uploadFile(..))", returning = "result")
    public void logMetadataExtractionCompleted(JoinPoint joinPoint, Object result) {
        String fileName = getFileName(joinPoint);
        String timestamp = LocalDateTime.now().format(dateTimeFormatter);
        logger.info("[{}] Metadata extraction completed successfully for file: {}", timestamp, fileName);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null) ? authentication.getName() : "Unknown User";
        traceLogService.saveLog("Metadata extraction completed successfully", fileName, "Success",username);
    }

    @AfterThrowing(pointcut = "execution(* tn.esprit.gouvernance_donnees_backend.controllers.importation.MetaDataContoller.uploadFile(..))", throwing = "exception")
    public void logMetadataExtractionError(JoinPoint joinPoint, Exception exception) {
        String fileName = getFileName(joinPoint);
        String timestamp = LocalDateTime.now().format(dateTimeFormatter);
        logger.error("[{}] Error during metadata extraction for file: {}", timestamp, fileName, exception);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null) ? authentication.getName() : "Unknown User";
        traceLogService.saveLog("Error during metadata extraction", fileName, exception.getMessage(),username);
    }

    private String getFileName(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0 && args[0] instanceof MultipartFile) {
            MultipartFile file = (MultipartFile) args[0];
            if (file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()) {
                return file.getOriginalFilename();
            }
        }
        return "Unknown File";
    }

}
