package at.technikumwien.webshop.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import at.technikumwien.webshop.dto.ProductDTO;
import at.technikumwien.webshop.model.Product;
import at.technikumwien.webshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    // /////////////////////////////////////////////////////////////////////////
    // Init
    // /////////////////////////////////////////////////////////////////////////

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Product> findAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/active")
    public List<Product> findAllActiveProducts(@RequestParam(name = "type", required = false)String type) {
        List<Product> activeFilteredProducts = productService.findFilteredActiveProducts(type);
        return activeFilteredProducts;
    }

    @GetMapping("/{type}")
    public List<Product> findAllProductsByType(@PathVariable String type) {
        return productService.findByType(type);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductDTO productDTO) {
        Product product = productService.save(fromDTO(productDTO));
        return ResponseEntity.created(URI.create("http://localhost:8080/products")).body(product);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid ProductDTO productDTO) {
        Optional<Product> optionalProduct = productService.findById(productDTO.getId());
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            Product updatedProduct = productService.updateProductFromDTO(existingProduct, productDTO);
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // /////////////////////////////////////////////////////////////////////////
    // Util
    // /////////////////////////////////////////////////////////////////////////

    private static Product fromDTO(ProductDTO productDTO) {
        return new Product(productDTO.getName(),
                           productDTO.getDescription(),
                           productDTO.getImageUrl(),
                           productDTO.getPrice(),
                           productDTO.getQuantity(),
                           productDTO.getType(),
                           productDTO.isActive());
    }
}
