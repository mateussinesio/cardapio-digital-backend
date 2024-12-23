package menu.controllers;

import menu.dtos.CategoryRequestDTO;
import menu.dtos.CategoryResponseDTO;
import menu.model.Category;
import menu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = {"Content-Type", "Authorization"})
    @GetMapping
    public List<CategoryResponseDTO> showCategories() {
        return categoryService.showCategories().stream().map(CategoryResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = {"Content-Type", "Authorization"})
    @PostMapping
    public CategoryResponseDTO addCategory(@RequestParam("name") String name, @RequestParam("image") MultipartFile image) {
        byte[] imageBytes = null;
        try {
            imageBytes = image.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao ler a imagem", e);
        }

        CategoryRequestDTO data = new CategoryRequestDTO(name, imageBytes);
        Category category = new Category(data);
        Category savedCategory = categoryService.addCategory(category);
        return new CategoryResponseDTO(savedCategory);
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = {"Content-Type", "Authorization"})
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") String id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Successfully deleted!");
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = {"Content-Type", "Authorization"})
    @PutMapping("{id}")
    public CategoryResponseDTO updateCategory(@PathVariable("id") String id, @RequestBody CategoryRequestDTO data) {
        Category updatedCategory = categoryService.updateCategory(id, data);
        return new CategoryResponseDTO(updatedCategory);
    }
}