package menu.service;

import menu.dtos.CategoryRequestDTO;
import menu.dtos.ItemRequestDTO;
import menu.model.Item;
import menu.model.Category;

import java.util.List;

public interface ItemService {
    List<Item> showItemsInCategory(Category category);

    Item addItem(ItemRequestDTO data);

    Item updateItem(String id, ItemRequestDTO data);

    void deleteItem(String id);
}