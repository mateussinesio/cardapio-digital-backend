package menu.repository;

import menu.model.Item;
import menu.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, String> {
    List<Item> findByCategory(Category category);
}