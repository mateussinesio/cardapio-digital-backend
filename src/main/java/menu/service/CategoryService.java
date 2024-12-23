package menu.service;

import menu.dtos.CategoryRequestDTO;
import menu.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> showCategories();

    Category addCategory(Category category);

    Category deleteCategory(String id);

    Category updateCategory(String id, CategoryRequestDTO data);

    Category findByName(String name); // Adicione este método
}