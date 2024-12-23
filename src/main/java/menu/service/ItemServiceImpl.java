package menu.service;

import menu.dtos.ItemRequestDTO;
import menu.model.Category;
import menu.model.Item;
import menu.repository.CategoryRepository;
import menu.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Item> showItemsInCategory(Category category) {
        return itemRepository.findByCategory(category);
    }

    @Override
    public Item addItem(ItemRequestDTO data) {
        Category category = categoryRepository.findByName(data.category());
        if (category != null) {
            Item item = new Item(data);
            item.setCategory(category);
            return itemRepository.save(item);
        } else {
            throw new RuntimeException("Category not found");
        }
    }

    @Override
    public Item updateItem(String id, ItemRequestDTO data) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item não encontrada."));
        item.setName(data.name());
        item.setDescription(data.description());
        item.setImage(data.image());
        item.setPrice(data.price());
        return itemRepository.save(item);
    }

    @Override
    public void deleteItem(String id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            itemRepository.deleteById(id);
        } else {
            throw new RuntimeException("Item not found");
        }
    }
}