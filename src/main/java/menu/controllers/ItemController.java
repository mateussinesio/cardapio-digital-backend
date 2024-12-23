package menu.controllers;

import menu.dtos.CategoryRequestDTO;
import menu.dtos.ItemRequestDTO;
import menu.dtos.ItemResponseDTO;
import menu.model.Item;
import menu.model.Category;
import menu.service.ItemService;
import menu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = {"Content-Type", "Authorization"})
    @GetMapping("/{category}")
    public ResponseEntity<?> showItemsInCategory(@PathVariable String category) {
        Category categoryName = categoryService.findByName(category);
        if (category == null) {
            throw new RuntimeException("Category not found.");
        }

        List<Item> items = itemService.showItemsInCategory(categoryName);
        if (items.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada.");
        }

        return ResponseEntity.ok(items);
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = {"Content-Type", "Authorization"})
    @PostMapping
    public ItemResponseDTO addItem(@RequestBody ItemRequestDTO data) {
        Item item = itemService.addItem(data);
        return new ItemResponseDTO(item);
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = {"Content-Type", "Authorization"})
    @PutMapping("{id}")
    public Item updateItem(@PathVariable("id") String id, @RequestBody ItemRequestDTO data) {
        return itemService.updateItem(id, data);
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = {"Content-Type", "Authorization"})
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") String id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok("Item successfully deleted!");
    }
}