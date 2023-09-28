package at.technikumwien.webshop.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import at.technikumwien.webshop.model.Cart;
import at.technikumwien.webshop.repository.CartRepository;
import at.technikumwien.webshop.service.CartService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CartService.class)  // Fokus auf CartService
public class CartServiceIntergrationTest {
  
    
@Autowired
    private CartService cartService;  

    @MockBean
    private CartRepository cartRepository; 



    @Test
    public void testSaveCart() {
        Cart mockCart = new Cart();


        when(cartRepository.save(any(Cart.class))).thenReturn(mockCart);

        Cart savedCart = cartService.save(new Cart());

        assertNotNull(savedCart);

    }

    @Test
    public void testFindByUserId() {
        Cart mockCart = new Cart();

        when(cartRepository.findByUserId(anyLong())).thenReturn(mockCart);

        Cart foundCart = cartService.findByUserId(1L);

        assertNotNull(foundCart);

    }

}
