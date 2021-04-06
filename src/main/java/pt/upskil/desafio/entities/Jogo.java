package pt.upskil.desafio.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
public class Jogo implements Comparable<Jogo> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "jogo")
    private List<Ronda> rondas;

    @OneToOne
    private Ronda rondaAtual;

    private int gameScore;

    private boolean finished;

    private boolean ajudaPublicoUsed;

    private boolean ajuda5050Used;

    private boolean ajudaTrocaPerguntaUsed;


    // Constructors
    public Jogo() {
        this.finished = false;
        this.ajudaPublicoUsed = false;
        this.ajuda5050Used = false;
        this.ajudaTrocaPerguntaUsed = false;
    }

    public Jogo(long id, User user, Ronda rondaAtual, int gameScore) {
        this.id = id;
        this.user = user;
        this.rondaAtual = rondaAtual;
        this.gameScore = gameScore;
        this.finished = false;
        this.ajudaPublicoUsed = false;
        this.ajuda5050Used = false;
        this.ajudaTrocaPerguntaUsed = false;
    }

    public void addScore(int score) {
        gameScore += score;
        if(gameScore<0){
            gameScore=0;
        }
    }

    public Ronda proximaRonda() {
        int nrRondaActual = rondaAtual.getNumero();

        for (Ronda ronda : rondas) {
            if(ronda.getNumero() == nrRondaActual + 1){
                return ronda;
            }
        }

        return null;
    }


    @Override
    public int compareTo(Jogo o) {
        return this.gameScore - o.gameScore;
    }
}