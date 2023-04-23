package tuk.mentor.domain.mentor.entity;

public enum Role {

    MENTOR("멘토"),
    MENTEE("멘티");

    private final String role;

    Role(String role) {
        this.role = role;
    }
}
