package at.technikumwien.webshop.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import at.technikumwien.webshop.model.File;
import at.technikumwien.webshop.repository.FileRepository;

@ExtendWith(SpringExtension.class)
public class StorageServiceTest {
    @Mock
    private FileRepository fileRepository;

    private StorageService storageService;

    @BeforeEach
    public void setUp() {
        storageService = new StorageService(fileRepository);
    }

    @Test
    public void testDeleteFileById() {
        Long fileId = 1L;
        String filename = "testFile.txt";
        File mockFile = new File();
        mockFile.setPath(filename);
        Optional<File> optionalFile = Optional.of(mockFile);

        when(fileRepository.findById(fileId)).thenReturn(optionalFile);

        storageService.deleteFileById(fileId);

        verify(fileRepository).deleteById(fileId);
    }

}
