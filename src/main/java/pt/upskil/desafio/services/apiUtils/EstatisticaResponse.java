package pt.upskil.desafio.services.apiUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstatisticaResponse {

    private String total;
    private String faceis;
    private String medias;
    private String dificeis;
    private String impossiveis;
    public String status;
    public String error;
}
