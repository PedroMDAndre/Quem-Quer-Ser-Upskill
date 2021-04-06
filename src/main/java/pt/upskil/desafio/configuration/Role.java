package pt.upskil.desafio.configuration;

public enum Role {
    ROLE_USER("/player/dashboard");

    private String mainPage;

    Role(String mainPage) {
        this.mainPage = mainPage;
    }

    public String getMainPage() {
        return mainPage;
    }
}
