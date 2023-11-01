package pl.mantiscrab.budgetr.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class CategoryRepository {
    private final Map<CategoryId, Category> categories = new HashMap<>();


    Category save(Category category) {
        categories.put(category.getId(), category);
        return category;
    }

    List<Category> findCategories() {
        return categories.values().stream().toList();
    }

    Optional<Category> findCategory(CategoryId categoryId) {
        return Optional.ofNullable(categories.get(categoryId));
    }

    Optional<Category> findCategoryByName(String categoryName) {
        return categories.values().stream().filter(c -> categoryName.equals(c.getName())).findFirst();
    }

    void deleteById(CategoryId categoryId) {
        categories.remove(categoryId);
    }
}
