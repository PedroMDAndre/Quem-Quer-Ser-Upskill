package pt.upskil.desafio.services;

public enum ErrorMessage {
    // User validation message error
    ERROR_MESSAGE_FIRSTNAME("Nome inválido"),
    ERROR_MESSAGE_LASTNAME("Nome inválido"),
    ERROR_MESSAGE_CITY("Nome de cidade inválida"),
    ERROR_MESSAGE_PASSWORD("Password inválida"),
    ERROR_MESSAGE_PASSWORD2("Password não coincide"),
    ERROR_MESSAGE_EMAIL("Este endereço já se encontra em utilização"),
    ERROR_MESSAGE_EMAIL2("Email inválido"),
    ERROR_MESSAGE_USERNAME("Username já existe");

    // Attributes
    private final String errorMsg;

    // Constructor
    ErrorMessage(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    // Methods
    public String getErrorMsg() {
        return errorMsg;
    }
}
