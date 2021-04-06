package pt.upskil.desafio.services.apiUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PerguntaResponse {

    String pergunta;
    String resposta1;
    String resposta2;
    String resposta3;
    String resposta4;
    String certa;
    String dificuldade;

}
