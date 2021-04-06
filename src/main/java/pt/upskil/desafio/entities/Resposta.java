package pt.upskil.desafio.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "pergunta_id")
    private Pergunta pergunta;

    private String texto;
    private boolean certa;
    private boolean removedBy5050;

    public Resposta() {
    }

    public Resposta(Pergunta pergunta, String texto, boolean certa) {
        this.pergunta = pergunta;
        this.texto = texto;
        this.certa = certa;
    }

    @Override
    public String toString() {
        return "Resposta{" +
                "id=" + id +
                ", pergunta=" + pergunta.getDescricao() +
                ", texto='" + texto + '\'' +
                ", certa=" + certa +
                '}';
    }
}