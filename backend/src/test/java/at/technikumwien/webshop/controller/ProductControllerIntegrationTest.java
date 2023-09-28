package at.technikumwien.webshop.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;

import at.technikumwien.webshop.config.SecurityConfig;
import at.technikumwien.webshop.model.Product;
import at.technikumwien.webshop.repository.ProductRepository;
import at.technikumwien.webshop.service.ProductService;
import at.technikumwien.webshop.service.StorageService;
import at.technikumwien.webshop.service.TokenService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ExtendWith(SpringExtension.class)
@Import({ ProductService.class, SecurityConfig.class })
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private StorageService storageService;

    @MockBean
    private ProductService productService;

    @MockBean
    private TokenService tokenService;
    // Intergrarion Test

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testFindAllProductsWithRoleAdmin() throws Exception {
        List<Product> testProducts = new ArrayList<>();
        testProducts.add(new Product());
        testProducts.add(new Product());
        Gson gson = new Gson();

        when(productRepository.findAll()).thenReturn(testProducts);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/products"))
                .andExpect(status().isOk())
                .andExpect(result -> gson.toJson(testProducts));
    }

    @Test
    public void testFindAllProductsWithNoRole() throws Exception {
        List<Product> testProducts = new ArrayList<>();
        testProducts.add(new Product());
        testProducts.add(new Product());
        Gson gson = new Gson();

        when(productRepository.findAll()).thenReturn(testProducts);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/products"))
                .andExpect(status().isForbidden())
                .andExpect(result -> gson.toJson(testProducts));
    }

    @Test
    public void testFindAllProductsByTypeWhitRoleAdmin() throws Exception {
        List<Product> testProducts = new ArrayList<>();
        Product product1 = new Product();
        product1.setType("Rings");
        Product product2 = new Product();
        product2.setType("Rings");
        testProducts.add(product1);
        testProducts.add(product2);
        Gson gson = new Gson();

        when(productRepository.findByActive(true)).thenReturn(testProducts);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/products/active?type=Rings"))
                .andExpect(status().isOk())
                .andExpect(result -> gson.toJson(testProducts));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteProductWhitRoleAdmin() throws Exception {
        Long testProductId = 1L;

        doNothing().when(productService).deleteProduct(testProductId);

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/products/" + testProductId))
                .andExpect(status().isNoContent());
    }
    /*
     * @Test
     * 
     * @WithMockUser(roles = "ADMIN")
     * public void shloudCreateProductAsAdmin() throws Exception {
     * ProductDTO productDTO = new ProductDTO();
     * productDTO.setActive(false);
     * productDTO.setName("testName");
     * productDTO.setDescription("testDescription");
     * productDTO.setImageUrl("1");
     * productDTO.setType("w");
     * productDTO.setPrice(1);
     * productDTO.setQuantity(1);
     * 
     * Product product = new Product();
     * Gson gson = new Gson();
     * 
     * when(productRepository.save(any())).thenReturn(product);
     * 
     * this.mockMvc.perform(MockMvcRequestBuilders
     * .post("/products")
     * .content(gson.toJson(productDTO))
     * .contentType(MediaType.APPLICATION_JSON))
     * .andExpect(status().isCreated())
     * .andExpect(result -> gson.toJson(product));
     * }
     */
}
