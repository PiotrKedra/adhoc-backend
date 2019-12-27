package piotr.kedra.adhoc.auth;

public class RegisterResponse {
    private Long id;
    private String message;

    public RegisterResponse(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
