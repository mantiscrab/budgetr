package pl.mantiscrab.budgetr.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CategoryId {
    private final UUID id;

    static CategoryId getNew() {
        return new CategoryId(UUID.randomUUID());
    }
}
