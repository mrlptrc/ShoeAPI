package myshoes.ecom.Shoe;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shoes")
@CrossOrigin(origins = "http://localhost:4200")
public class ShoeController {

    @Autowired
    private ShoeService shoeService;

    @GetMapping("/{id}")
    public ResponseEntity<ShoeDTO> getShoe(@PathVariable ObjectId id) {
        Optional<ShoeDTO> shoe = shoeService.getShoe(id);
        return shoe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShoeDTO>> getAllShoes() {
        List<ShoeDTO> shoeList = shoeService.getAllShoes();
        return ResponseEntity.ok(shoeList);
    }

    @PostMapping("/create")
    public ResponseEntity<ShoeDTO> createShoe(@RequestBody ShoeModel shoe) {
        ShoeDTO newShoe = shoeService.createShoe(shoe);
        return ResponseEntity.status(HttpStatus.CREATED).body(newShoe);
    }

    @PutMapping("/update/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<ShoeDTO> updateShoe(@PathVariable ObjectId id, @RequestBody ShoeDTO updatedShoe) {
        Optional<ShoeDTO> optionalShoe = shoeService.updateShoe(id, updatedShoe);
        return optionalShoe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteShoe(@PathVariable ObjectId id) {
        if (shoeService.deleteShoe(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
