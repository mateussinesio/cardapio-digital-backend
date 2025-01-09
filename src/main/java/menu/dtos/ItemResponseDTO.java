package menu.dtos;

import menu.model.Item;

import java.math.BigDecimal;

public record ItemResponseDTO(String categoryId, String categoryName, String id, String name, String description, String image, BigDecimal price) {
    public ItemResponseDTO(Item item) {
        this(item.getCategory().getId(), item.getCategory().getName(), item.getId(), item.getName(), item.getDescription(), item.getImage(), item.getPrice());
    }
}
