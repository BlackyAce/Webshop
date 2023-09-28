package at.technikumwien.webshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import at.technikumwien.webshop.dto.ProductDTO;
import at.technikumwien.webshop.model.Product;
import at.technikumwien.webshop.repository.ProductRepository;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StorageService storageService;

    @BeforeEach
    public void setUP() {
        productService = new ProductService(productRepository, storageService);
    }

    // Unit Test
    @Test
    public void testFindFilteredActiveProducts() {
        List<Product> testProducts = new ArrayList<>();
        testProducts.add(new Product("WoodEaring", "beistpiel Text", "3", 12.99, 10, "ring", true));
        testProducts.add(new Product("SilverRings", "beistpiel Text", "2", 12.99, 10, "ring", true));

        when(productRepository.findByActive(true)).thenReturn(testProducts);

        List<Product> testResult = productService.findFilteredActiveProducts("ring");
        assertEquals(testProducts, testResult);
    }

    @Test
    public void testFindById() {
        Product testProduct = new Product();

        when(productRepository.findById(any())).thenReturn(Optional.of(testProduct));

        Optional<Product> testResulst = productService.findById(1L);
        assertEquals(testProduct, testResulst.get());

    }

    @Test
    public void testFindByType() {
        List<Product> testProducts = new ArrayList<>();
        testProducts.add(new Product());
        testProducts.add(new Product());

        String testType = "someType";

        when(productRepository.findByType(anyString())).thenReturn(testProducts);

        List<Product> testResults = productService.findByType(testType);

        assertEquals(testProducts, testResults);
        verify(productRepository).findByType(eq(testType));
    }

    @Test
    public void testDeleteProduct() {
        long testId = 1L;
        Long testImageUrl = 2L;

        Product testProduct = new Product();
        testProduct.setImageUrl(testImageUrl.toString());

        Optional<Product> optionalProduct = Optional.of(testProduct);

        when(productRepository.findById(eq(testId))).thenReturn(optionalProduct);

        productService.deleteProduct(testId);

        verify(productRepository).deleteById(eq(testId));
        verify(storageService).deleteFileById(eq(testImageUrl));
    }

    @Test
    public void testSaveProduct() {
        Product testProduct = new Product();
        testProduct.setName("Test Product");

        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        Product savedProduct = productService.save(testProduct);

        assertEquals(testProduct, savedProduct);
        verify(productRepository).save(testProduct);
    }

    @Test
    public void testUpdateProductFromDTO() {
        Product existingProduct = new Product();
        existingProduct.setName("Old Name");

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("New Name");
        productDTO.setDescription("New Description");
        productDTO.setQuantity(10);
        productDTO.setType("New Type");
        productDTO.setPrice(100.0);
        productDTO.setActive(true);

        when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArguments()[0]);

        Product updatedProduct = productService.updateProductFromDTO(existingProduct, productDTO);

        assertEquals("New Name", updatedProduct.getName());
        assertEquals("New Description", updatedProduct.getDescription());
        assertEquals(10, updatedProduct.getQuantity());
        assertEquals("New Type", updatedProduct.getType());
        assertEquals(100.0, updatedProduct.getPrice(), 0.01);
        assertTrue(updatedProduct.isActive());

        verify(productRepository).save(existingProduct);
    }

}
