package pl.mantiscrab.budgetr.domain;

class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String categoryName) {
        super("Category with name " + categoryName + " already exists");
    }
}
