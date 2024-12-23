package menu.model;

import jakarta.persistence.*;
import lombok.*;
import menu.dtos.ItemRequestDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.math.BigDecimal;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String description;
    private String image;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

    public Item(ItemRequestDTO data) {
        name = data.name();
        description = data.description();
        image = data.image();
        price = data.price();
    }
}