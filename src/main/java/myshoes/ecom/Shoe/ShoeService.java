package myshoes.ecom.Shoe;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoeService {

    @Autowired
    private MongoShoeRepository shoeRepository;

    public Optional<ShoeDTO> getShoe(ObjectId id) {
        return shoeRepository.findById(id).map(this::convertToDTO);
    }

    public List<ShoeDTO> getAllShoes() {
        return shoeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ShoeDTO createShoe(ShoeModel shoe) {
        ShoeModel savedShoe = shoeRepository.save(shoe);
        return convertToDTO(savedShoe);
    }

    public Optional<ShoeDTO> updateShoe(ObjectId id, ShoeDTO updatedShoe) {
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

    public boolean deleteShoe(ObjectId id) {
        if (shoeRepository.existsById(id)) {
            shoeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ShoeDTO convertToDTO(ShoeModel shoeModel) {
        return new ShoeDTO(
                shoeModel.getId().toString(),
                shoeModel.getBrand(),
                shoeModel.getModel(),
                shoeModel.getPrice()
        );
    }
}
