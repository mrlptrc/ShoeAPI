package myshoes.ecom.Shoe;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoShoeRepository extends MongoRepository<ShoeModel, ObjectId> {

}
