package menu.dtos;

import java.math.BigDecimal;

public record ItemRequestDTO(String name, String category, String description, String image, BigDecimal price, boolean removeImage) {
}
