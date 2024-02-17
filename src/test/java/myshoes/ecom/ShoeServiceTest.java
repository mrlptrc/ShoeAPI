package myshoes.ecom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import myshoes.ecom.Shoe.MongoShoeRepository;
import myshoes.ecom.Shoe.ShoeDTO;
import myshoes.ecom.Shoe.ShoeModel;
import myshoes.ecom.Shoe.ShoeService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ShoeServiceTest {

    @Mock
    private MongoShoeRepository shoeRepository;

    @InjectMocks
    private ShoeService shoeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetShoe() {
        String shoeId = "1";
        ShoeModel shoeModel = new ShoeModel("1", "Nike", "Air Max", 100.0);
        when(shoeRepository.findById(shoeId)).thenReturn(Optional.of(shoeModel));

        Optional<ShoeDTO> result = shoeService.getShoe(shoeId);

        assertTrue(result.isPresent());
        assertEquals(shoeId, result.get().getId());
        assertEquals("Nike", result.get().getBrand());
        assertEquals("Air Max", result.get().getModel());
        assertEquals(100.0, result.get().getPrice());
    }

    @Test
    void testGetAllShoes() {
        ShoeModel shoeModel1 = new ShoeModel("1", "Nike", "Air Max", 100.0);
        ShoeModel shoeModel2 = new ShoeModel("2", "Adidas", "Superstar", 80.0);
        when(shoeRepository.findAll()).thenReturn(Arrays.asList(shoeModel1, shoeModel2));

        List<ShoeDTO> result = shoeService.getAllShoes();

        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("Nike", result.get(0).getBrand());
        assertEquals("Air Max", result.get(0).getModel());
        assertEquals(100.0, result.get(0).getPrice());
        assertEquals("2", result.get(1).getId());
        assertEquals("Adidas", result.get(1).getBrand());
        assertEquals("Superstar", result.get(1).getModel());
        assertEquals(80.0, result.get(1).getPrice());
    }

    @SuppressWarnings("null")
    @Test
    void testCreateShoe() {
        ShoeModel shoeModel = new ShoeModel("1", "Nike", "Air Max", 100.0);

        when(shoeRepository.findById(shoeModel.getId())).thenReturn(Optional.empty());
        when(shoeRepository.save(any(ShoeModel.class))).thenAnswer(i -> i.getArguments()[0]);

        ShoeDTO result = shoeService.createShoe(shoeModel);

        assertEquals(shoeModel.getId(), result.getId());
        assertEquals(shoeModel.getBrand(), result.getBrand());
        assertEquals(shoeModel.getModel(), result.getModel());
        assertEquals(shoeModel.getPrice(), result.getPrice());
    }

    @SuppressWarnings("null")
    @Test
    void testCreateShoeThrowsException() {
        ShoeModel shoeModel = new ShoeModel("1", "Nike", "Air Max", 100.0);

        when(shoeRepository.findById(shoeModel.getId())).thenReturn(Optional.of(shoeModel));

        assertThrows(IllegalArgumentException.class, () -> shoeService.createShoe(shoeModel));
    }

    @SuppressWarnings("null")
    @Test
    void testUpdateShoe() {
        String id = "1";
        ShoeDTO updatedShoe = new ShoeDTO("1", "Nike", "Air Max", 100.0);
        updatedShoe.setId(id);
        updatedShoe.setBrand("Nike");
        updatedShoe.setModel("Air Max");
        updatedShoe.setPrice(100.0);

        ShoeModel existingShoe = new ShoeModel("2", "Adidas", "Superstar", 80.0);
        existingShoe.setId(id);
        existingShoe.setBrand("Adidas");
        existingShoe.setModel("Superstar");
        existingShoe.setPrice(80.0);

        when(shoeRepository.findById(id)).thenReturn(Optional.of(existingShoe));
        when(shoeRepository.save(any(ShoeModel.class))).thenAnswer(i -> i.getArguments()[0]);

        Optional<ShoeDTO> result = shoeService.updateShoe(id, updatedShoe);

        assertTrue(result.isPresent());
        assertEquals(updatedShoe.getBrand(), result.get().getBrand());
        assertEquals(updatedShoe.getModel(), result.get().getModel());
        assertEquals(updatedShoe.getPrice(), result.get().getPrice());
    }

    @Test
    void testUpdateShoeThrowsException() {
        String id = "1";
        ShoeDTO updatedShoe = new ShoeDTO("", "Air Max", "", 100.0);

        when(shoeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> shoeService.updateShoe(id, updatedShoe));
    }

    @Test
    void testDeleteShoe() {
        String id = "1";

        when(shoeRepository.existsById(id)).thenReturn(true);

        shoeService.deleteShoe(id);

        verify(shoeRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteShoeThrowsException() {
        String id = "testId";

        when(shoeRepository.existsById(id)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> shoeService.deleteShoe(id));

        verify(shoeRepository, times(0)).deleteById(id);
    }
}