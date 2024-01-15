package myshoes.ecom.Shoe;

import myshoes.ecom.Shoe.ShoeModel;
import myshoes.ecom.Shoe.MongoShoeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoeService {

    @Autowired
    private MongoShoeRepository shoeRepository;

    public Optional<ShoeModel> getShoe(Long id) {
        return shoeRepository.findById(id);
    }

    public List<ShoeModel> getAllShoes() {
        return shoeRepository.findAll();
    }

    public ShoeModel createShoe(ShoeModel shoe) {
        return shoeRepository.save(shoe);
    }

    public Optional<ShoeModel> updateShoe(Long id, ShoeModel updatedShoe) {
        Optional<ShoeModel> optionalShoe = shoeRepository.findById(id);
        if (optionalShoe.isPresent()) {
            ShoeModel existingShoe = optionalShoe.get();
            existingShoe.setBrand(updatedShoe.getBrand());
            existingShoe.setModel(updatedShoe.getModel());
            existingShoe.setPrice(updatedShoe.getPrice());
            return Optional.of(shoeRepository.save(existingShoe));
        }
        return Optional.empty();
    }

    public boolean deleteShoe(Long id) {
        if (shoeRepository.existsById(id)) {
            shoeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
