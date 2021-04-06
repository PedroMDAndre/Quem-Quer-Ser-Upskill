package pt.upskil.desafio.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Ronda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int numero;

    @OneToOne
    private Pergunta pergunta;

    @OneToOne
    private Resposta respostaEscolhida;

    @ManyToOne
    @JoinColumn(name = "jogo_id")
    private Jogo jogo;

    private LocalDateTime startTime;

    public Ronda() {
    }

    public Ronda(int numero, Pergunta pergunta, Jogo jogo) {
        this.numero = numero;
        this.pergunta = pergunta;
        this.jogo = jogo;
    }
}