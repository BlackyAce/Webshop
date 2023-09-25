package at.technikumwien.webshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import at.technikumwien.webshop.dto.ProductDTO;
import at.technikumwien.webshop.model.Product;
import at.technikumwien.webshop.repository.ProductRepository;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private StorageService storageService;

    // /////////////////////////////////////////////////////////////////////////
    // Init
    // /////////////////////////////////////////////////////////////////////////

    public ProductService(ProductRepository productRepository, StorageService storageService) {
        this.productRepository = productRepository;
        this.storageService = storageService;

    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findFilteredActiveProducts(String type) {
        List<Product> activeFilteredProducts = new ArrayList<>();
        List<Product> allActiveProducts = productRepository.findByActive(true);
        if (type == null) {
            return allActiveProducts;
        }
        for (Product product : allActiveProducts) {
            if (product.getType().equals(type)) {
                activeFilteredProducts.add(product);
            }
        }
        return activeFilteredProducts;
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findByType(String type) {
        return productRepository.findByType(type);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(long id) {

        Optional<Product> productOptional = productRepository.findById(id);

        Product product = productOptional.get();

        Long imageUrl = Long.parseLong(product.getImageUrl());

        productRepository.deleteById(id);
        storageService.deleteFileById(imageUrl);
    }

    public Product updateProductFromDTO(Product existingProduct, ProductDTO productDTO) {
        // Führe hier die Logik zur Aktualisierung des Produkts basierend auf den Daten
        // aus dem DTO durch
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setQuantity(productDTO.getQuantity());
        existingProduct.setType(productDTO.getType());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setActive(productDTO.isActive());

        // Speichere das aktualisierte Produkt
        return productRepository.save(existingProduct);

    }
}
