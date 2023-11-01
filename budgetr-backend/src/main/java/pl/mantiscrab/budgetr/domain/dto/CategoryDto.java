package pl.mantiscrab.budgetr.domain.dto;

import lombok.Getter;
import pl.mantiscrab.budgetr.domain.Category;
import pl.mantiscrab.budgetr.domain.CategoryId;

@Getter
public class CategoryDto {
    private final CategoryId id;
    private final String name;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
