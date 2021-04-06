package pt.upskil.desafio.entities;

import lombok.Getter;

import java.time.Duration;

@Getter
public enum Dificuldade {
    FACIL("fácil", "Fácil", 1, Duration.ofSeconds(20), 50, 0.7),
    MEDIA("média","Média", 2, Duration.ofSeconds(30), 75, 0.5),
    DIFICIL("difícil","Difícil", 3, Duration.ofSeconds(40), 100, 0.3),
    IMPOSSIVEL("impossível","Impossível", 4, Duration.ofSeconds(60), 200, 0.2);


    private final String apiText;
    private final String buttonText;
    private final int type;
    private final Duration duration;
    private final int pontos;
    private final double precisaoAjuda;

    Dificuldade(String apiText,
                String buttonText,
                int type,
                Duration duration,
                int pontos,
                double precisaoAjuda) {
        this.apiText = apiText;
        this.buttonText = buttonText;
        this.type = type;
        this.duration = duration;
        this.pontos = pontos;
        this.precisaoAjuda = precisaoAjuda;
    }

    public static Dificuldade getDificuldadeFromApiText(String apiText) {
        for (Dificuldade dificuldade : Dificuldade.values()) {
            if (dificuldade.apiText.equals(apiText)) {
                return dificuldade;
            }
        }
        return null;
    }

    public static Dificuldade getDificuldadeFromType(int type) {
        for (Dificuldade dificuldade : Dificuldade.values()) {
            if (dificuldade.type == type) {
                return dificuldade;
            }
        }
        return null;
    }
}