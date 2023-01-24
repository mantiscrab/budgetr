package pl.mantiscrab.budgetr.registration.exception;

public record ConstraintViolation(String field, String message) {
}