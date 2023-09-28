package at.technikumwien.webshop.service;

import at.technikumwien.webshop.model.File;
import at.technikumwien.webshop.repository.FileRepository;
import at.technikumwien.webshop.service.StorageService;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class StorageService {

    private final Path storageDirectory;// The directory where files will be stored

    private FileRepository fileRepository;

    public StorageService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
        storageDirectory = Path.of("frontend/Image/"); // Replace with your desired directory path
    }

    public File store(MultipartFile file) throws IOException {
        // Generate a unique filename
        String filename = generateUniqueFileName(file.getOriginalFilename());

        // Store the file in the storage directory
        Path filePath = storageDirectory.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        File fileEntity = new File();
        fileEntity.setPath(filename);

        return fileEntity;
    }

    public Resource serve(File fileEntity) throws IOException {
        String filename = fileEntity.getPath();
        // Retrieve the file from the storage directory
        Path filePath = storageDirectory.resolve(filename);
        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new IOException("File not found or cannot be read: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new IOException("Invalid file path: " + filename, e);
        }
    }

    private String generateUniqueFileName(String originalFilename) {
        long timestamp = System.currentTimeMillis();
        return timestamp + "_" + originalFilename;
    }

    public void deleteFileById(Long fileId) {
        // Zuerst das File-Objekt aus der Datenbank abrufen
        Optional<File> optionalFile = fileRepository.findById(fileId);

        File fileEntity = optionalFile.get();
        String filename = fileEntity.getPath();
        Path filePath = storageDirectory.resolve(filename);

        // Zuerst aus der Datenbank löschen
        fileRepository.deleteById(fileId);

        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
