package menu.dtos;

import java.math.BigDecimal;

public record ItemRequestDTO(String category, String name, String description, String image, BigDecimal price) {
}
