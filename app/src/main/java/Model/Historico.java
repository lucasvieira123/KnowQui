package Model;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class Historico {
    private Integer id;
    private String descricao;
    private Integer idUsuario;
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

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
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
