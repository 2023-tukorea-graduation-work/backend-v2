package server.stutino.domain.member;

public enum Role {

    MENTOR("멘토"),
    MENTEE("멘티"),
    ADMIN("관리자");

    private final String role;

    Role(String role) {
        this.role = role;
    }
}
