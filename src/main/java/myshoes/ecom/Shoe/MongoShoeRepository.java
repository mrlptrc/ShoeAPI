package myshoes.ecom.Shoe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoShoeRepository extends MongoRepository<ShoeModel, Long> {

}
