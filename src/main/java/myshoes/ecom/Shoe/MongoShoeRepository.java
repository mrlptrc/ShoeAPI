package myshoes.ecom.Shoe;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoShoeRepository extends MongoRepository<ShoeModel, String> {
    List<ShoeModel> findByBrand(String brand);
    List<ShoeModel> findByModel(String model);
    List<ShoeModel> findByBrandContaining(String keyword);
    List<ShoeModel> findByModelContaining(String keyword);
}

