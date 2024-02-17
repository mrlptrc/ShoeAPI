package myshoes.ecom.Shoe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoeService {

    @Autowired
    private MongoShoeRepository shoeRepository;

    @SuppressWarnings("null")
    public Optional<ShoeDTO> getShoe(String id) {
        return shoeRepository.findById(id).map(this::convertToDTO);
    }

    public List<ShoeDTO> getAllShoes() {
        return shoeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("null")
    public ShoeDTO createShoe(ShoeModel shoe) {
        if (shoeRepository.findById(shoe.getId()).isPresent()) {
            throw new IllegalArgumentException("ID " + shoe.getId() + " already exists");
        }
        ShoeModel savedShoe = shoeRepository.save(shoe);
        return convertToDTO(savedShoe);
    }

    @SuppressWarnings("null")
    public Optional<ShoeDTO> updateShoe(String id, ShoeDTO updatedShoe) {
        validateUpdatedShoe(updatedShoe);

        return shoeRepository.findById(id)
                .map(existingShoe -> updateAndSaveShoe(existingShoe, updatedShoe))
                .map(this::convertToDTO);
    }

    private void validateUpdatedShoe(ShoeDTO updatedShoe) {
        Double price = updatedShoe.getPrice();

        if (updatedShoe.getBrand() == null || updatedShoe.getBrand().isEmpty()) {
            throw new IllegalArgumentException("Brand is missing");
        }
        if (updatedShoe.getModel() == null || updatedShoe.getModel().isEmpty()) {
            throw new IllegalArgumentException("Model is missing");
        }
        if (price == -1) {
            throw new IllegalArgumentException("Price is missing");
        }
    }

    private ShoeModel updateAndSaveShoe(ShoeModel existingShoe, ShoeDTO updatedShoe) {
        existingShoe.setBrand(updatedShoe.getBrand());
        existingShoe.setModel(updatedShoe.getModel());
        existingShoe.setPrice(updatedShoe.getPrice());

        return shoeRepository.save(existingShoe);
    }

    @SuppressWarnings("null")
    public boolean deleteShoe(String id) {
        if (!shoeRepository.existsById(id)) {
            throw new IllegalArgumentException("The ID cannot be deleted because it's already deleted");
        }
        shoeRepository.deleteById(id);
        return false;
    }

    public ShoeDTO convertToDTO(ShoeModel shoeModel) {
        ShoeDTO shoeDTO = new ShoeDTO("", "", "", 0);
        shoeDTO.setId(shoeModel.getId());
        shoeDTO.setBrand(shoeModel.getBrand());
        shoeDTO.setModel(shoeModel.getModel());
        shoeDTO.setPrice(shoeModel.getPrice());
        return shoeDTO;
    }
    
}
