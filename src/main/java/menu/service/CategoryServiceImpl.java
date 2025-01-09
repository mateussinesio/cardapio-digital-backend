package menu.service;

import menu.dtos.CategoryRequestDTO;
import menu.model.Category;
import menu.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> showCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(String id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            categoryRepository.delete(category.get());
        } else {
            throw new RuntimeException("Category not found.");
        }
    }

    @Override
    public Category updateCategory(String id, CategoryRequestDTO data) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found."));

        category.setName(data.name());

        if (data.imagePath() != null) {
            category.setImagePath(data.imagePath());
        } else if (data.imagePath() == null && data.removeImage()) {
            category.setImagePath(null);
        }

        return categoryRepository.save(category);
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category getCategoryById(String id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found."));
    }
}
