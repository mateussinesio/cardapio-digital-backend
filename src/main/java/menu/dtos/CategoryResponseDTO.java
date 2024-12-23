package menu.dtos;

import menu.model.Category;
import java.util.Base64;

public record CategoryResponseDTO(String id, String name, String image) {
    public CategoryResponseDTO(Category category) {
        this(category.getId(), category.getName(), Base64.getEncoder().encodeToString(category.getImage()));
    }
}