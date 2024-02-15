package myshoes.ecom.Shoe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        if (shoeRepository.existsById(shoe.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "ID " + shoe.getId() + " already exists");
        }
        ShoeModel savedShoe = shoeRepository.save(shoe);
        return convertToDTO(savedShoe);
    }

    public Optional<ShoeDTO> updateShoe(String id, ShoeDTO updatedShoe) {
        @SuppressWarnings("null")
        Optional<ShoeModel> optionalShoe = shoeRepository.findById(id);
        if (optionalShoe.isPresent()) {
            ShoeModel existingShoe = optionalShoe.get();
            existingShoe.setBrand(updatedShoe.getBrand());
            existingShoe.setModel(updatedShoe.getModel());
            existingShoe.setPrice(updatedShoe.getPrice());
            ShoeModel savedShoe = shoeRepository.save(existingShoe);
            return Optional.of(convertToDTO(savedShoe));
        }
        return Optional.empty();
    }

    @SuppressWarnings("null")
    public boolean deleteShoe(String id) {
        if (shoeRepository.existsById(id)) {
            shoeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ShoeDTO convertToDTO(ShoeModel shoeModel) {
        return new ShoeDTO(
                shoeModel.getId(),
                shoeModel.getBrand(),
                shoeModel.getModel(),
                shoeModel.getPrice());
    }
}
