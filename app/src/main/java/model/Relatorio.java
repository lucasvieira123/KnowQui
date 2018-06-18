package model;

/**
 * Created by lucas-vieira on 18/06/18.
 */

public class Relatorio {

    private Integer id;
    private Boolean acertou;
    private String  tipo;



    public Boolean getAcertou() {
        return acertou;
    }

    public void setAcertou(Boolean acertou) {
        this.acertou = acertou;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
