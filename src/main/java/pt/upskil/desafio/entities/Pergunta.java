package pt.upskil.desafio.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Pergunta {
    public static final int NUM_RESPOSTAS = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String descricao;

    @OneToMany(mappedBy = "pergunta")
    private List<Resposta> respostas;

    private Dificuldade dificuldade;

    public Pergunta() {
    }

    @Override
    public String toString() {
        return "Pergunta{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", dificuldade=" + dificuldade +
                '}';
    }

    public static Pergunta criarPergunta(String pergunta,
                                         String resposta1,
                                         String resposta2,
                                         String resposta3,
                                         String resposta4,
                                         int respostaCerta,
                                         int dificuldadeType) {
        Pergunta perguntaObj = new Pergunta();

        ArrayList<Resposta> respostas = new ArrayList<>();
        respostas.add(new Resposta(perguntaObj, resposta1, respostaCerta == 1));
        respostas.add(new Resposta(perguntaObj, resposta2, respostaCerta == 2));
        respostas.add(new Resposta(perguntaObj, resposta3, respostaCerta == 3));
        respostas.add(new Resposta(perguntaObj, resposta4, respostaCerta == 4));

        perguntaObj.setDescricao(pergunta);
        perguntaObj.setRespostas(respostas);
        perguntaObj.setDificuldade(Dificuldade.getDificuldadeFromType(dificuldadeType));

        return perguntaObj;
    }
}