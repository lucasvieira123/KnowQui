package model;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class Pergunta extends Bean{
    private Integer id;
    private String descricao;
    private Integer Nivel_id;
    private Integer Tipo_id;
    private Integer Resposta_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getNivel_id() {
        return Nivel_id;
    }

    public void setNivel_id(Integer nivel_id) {
        this.Nivel_id = nivel_id;
    }

    public Integer getTipo_id() {
        return Tipo_id;
    }

    public void setTipo_id(Integer tipo_id) {
        this.Tipo_id = tipo_id;
    }

    public Integer getResposta_id() {
        return Resposta_id;
    }

    public void setResposta_id(Integer resposta_id) {
        this.Resposta_id = resposta_id;
    }
}
