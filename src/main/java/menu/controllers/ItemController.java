package menu.controllers;

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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = {"Content-Type", "Authorization"}, allowCredentials = "true")
    @GetMapping("/{category}")
    public ResponseEntity<?> showItemsInCategory(@PathVariable String category) {
        Category categoryName = categoryService.findByName(category);
        if (categoryName == null) {
            throw new RuntimeException("Category not found.");
        }

        List<Item> items = itemService.showItemsInCategory(categoryName);
        if (items.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada.");
        }

        return ResponseEntity.ok(items);
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = {"Content-Type", "Authorization"}, allowCredentials = "true")
    @PostMapping
    public ItemResponseDTO addItem(
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam("description") String description,
            @RequestParam("price") BigDecimal price,
            @RequestParam("image") MultipartFile image) {
        String imagePath;
        try {
            String userHome = System.getProperty("user.home");
            String uploadDir = userHome + "/images/";
            String fileName = java.util.UUID.randomUUID() + "_" + image.getOriginalFilename();
            imagePath = "/images/" + fileName;

            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            Path filePath = path.resolve(fileName);
            image.transferTo(filePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Error saving the image", e);
        }

        Category categoryName = categoryService.findByName(category);
        if (categoryName == null) {
            throw new RuntimeException("Category not found.");
        }

        ItemRequestDTO data = new ItemRequestDTO(name, category, description, imagePath, price);
        Item item = itemService.addItem(data);
        return new ItemResponseDTO(item);
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = {"Content-Type", "Authorization"}, allowCredentials = "true")
    @PutMapping("{id}")
    public ItemResponseDTO updateItem(
            @PathVariable("id") String id,
            @RequestParam("name") String name,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam("description") String description,
            @RequestParam("price") BigDecimal price,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        String imagePath = null;

        if (image != null && !image.isEmpty()) {
            try {
                String userHome = System.getProperty("user.home");
                String uploadDir = userHome + "/images/";
                String fileName = java.util.UUID.randomUUID() + "_" + image.getOriginalFilename();
                imagePath = "/images/" + fileName;

                Path path = Paths.get(uploadDir);
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }

                Path filePath = path.resolve(fileName);
                image.transferTo(filePath.toFile());
            } catch (IOException e) {
                throw new RuntimeException("Error saving the image", e);
            }
        }

        String finalCategory = category != null && !category.isBlank() ? category : null;

        ItemRequestDTO data = new ItemRequestDTO(name, finalCategory, description, imagePath, price);
        Item updatedItem = itemService.updateItem(id, data);

        if (updatedItem == null) {
            throw new RuntimeException("Item not found.");
        }

        return new ItemResponseDTO(updatedItem);
    }


    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = {"Content-Type", "Authorization"}, allowCredentials = "true")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") String id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok("Item successfully deleted!");
    }
}