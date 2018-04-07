package model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class Resposta extends Bean{
    private Integer id;
    private String descricao;
    private Integer id_pergunta;
    private Integer EhCorreta;
    @SerializedName("_index")
    private String letra;

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

    public Integer getId_pergunta() {
        return id_pergunta;
    }

    public void setId_pergunta(Integer id_pergunta) {
        this.id_pergunta = id_pergunta;
    }

    public Integer getEhCorreta() {
        return EhCorreta;
    }

    public void setEhCorreta(Integer ehCorreta) {
        EhCorreta = ehCorreta;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }
}
