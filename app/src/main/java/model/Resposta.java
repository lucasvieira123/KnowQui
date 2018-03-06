package model;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class Resposta extends Bean{
    private Integer id;
    private String descricao;
    private Integer Pergunta_id;
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

    public Integer getPergunta_id() {
        return Pergunta_id;
    }

    public void setPergunta_id(Integer pergunta_id) {
        this.Pergunta_id = pergunta_id;
    }

    public Boolean getEhCorreta() {
        return EhCorreta;
    }

    public void setEhCorreta(Boolean ehCorreta) {
        EhCorreta = ehCorreta;
    }
}
