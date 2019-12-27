package piotr.kedra.adhoc.ahpproblem.entity;

public class SubscriberAndDataDTO {

    private String email;
    private boolean isData;

    public SubscriberAndDataDTO(String email, boolean isData) {
        this.email = email;
        this.isData = isData;
    }

    public String getEmail() {
        return email;
    }

    public boolean isData() {
        return isData;
    }
}
