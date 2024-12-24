package menu.model;

import jakarta.persistence.*;
import lombok.*;
import menu.dtos.CategoryRequestDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String imagePath;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Item> items;

    public Category(CategoryRequestDTO data) {
        this.name = data.name();
        this.imagePath = data.imagePath();
    }
}