package pl.mantiscrab.budgetr.registration;

class UserAlreadyExistsException extends RuntimeException{
    UserAlreadyExistsException(String message) {
        super(message);
    }
}
