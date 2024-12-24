package menu.dtos;

import menu.model.Category;

import java.nio.file.Paths;

public record CategoryResponseDTO(String id, String name, String image) {
    public CategoryResponseDTO(Category category) {
        this(category.getId(), category.getName(), "/images/" + Paths.get(category.getImagePath()).getFileName().toString());
    }
}