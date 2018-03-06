package model;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class Historico extends Bean{
    private Integer id;
    private String descricao;
    private Integer Usuario_id;
    private Boolean acertou;
    private String data;

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

    public Integer getUsuario_id() {
        return Usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.Usuario_id = usuario_id;
    }

    public Boolean getAcertou() {
        return acertou;
    }

    public void setAcertou(Boolean acertou) {
        this.acertou = acertou;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
