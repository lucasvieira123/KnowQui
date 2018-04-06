package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class Pergunta extends Bean{
    private Integer id;
    private String descricao;
    private Integer tempo;
    @SerializedName("img")
    @Expose
    private String imagem;
    @Expose
    @SerializedName("diretorioImg")
    private String diretorioImagem;
    private String tipo;
    private String nivel;
    private String complemento;
   // private List<Resposta> _respostas;


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

    public Integer getTempo() {
        return tempo;
    }

    public void setTempo(Integer tempo) {
        this.tempo = tempo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getDiretorioImagem() {
        return diretorioImagem;
    }

    public void setDiretorioImagem(String diretorioImagem) {
        this.diretorioImagem = diretorioImagem;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

//    public List<Resposta> get_respostas() {
//        return _respostas;
//    }
//
//    public void set_respostas(List<Resposta> _respostas) {
//        this._respostas = _respostas;
//    }
}
