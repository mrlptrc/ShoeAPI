package myshoes.ecom.Shoe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shoes")
public class ShoeController {
    @Autowired
    private ShoeService shoeService;

    @GetMapping("/{id}")
    public ResponseEntity<ShoeModel> getShoe(@PathVariable Long id) {
        Optional<ShoeModel> shoe = shoeService.getShoe(id);
        return shoe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShoeModel>> getAllShoes() {
        List<ShoeModel> shoeList = shoeService.getAllShoes();
        return ResponseEntity.ok(shoeList);
    }

    @PostMapping("/create")
    public ResponseEntity<ShoeModel> createShoe(@RequestBody ShoeModel shoe) {
        ShoeModel newShoe = shoeService.createShoe(shoe);
        return ResponseEntity.status(HttpStatus.CREATED).body(newShoe);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ShoeModel> updateShoe(@PathVariable Long id, @RequestBody ShoeModel updatedShoe) {
        Optional<ShoeModel> optionalShoe = shoeService.updateShoe(id, updatedShoe);
        return optionalShoe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteShoe(@PathVariable Long id) {
        if (shoeService.deleteShoe(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
