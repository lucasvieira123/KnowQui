package Model;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class Resposta {
    private Integer id;
    private String descricao;
    private Integer idPergunda;
    private Boolean EhCorreta;

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

    public Integer getIdPergunda() {
        return idPergunda;
    }

    public void setIdPergunda(Integer idPergunda) {
        this.idPergunda = idPergunda;
    }

    public Boolean getEhCorreta() {
        return EhCorreta;
    }

    public void setEhCorreta(Boolean ehCorreta) {
        EhCorreta = ehCorreta;
    }
}
