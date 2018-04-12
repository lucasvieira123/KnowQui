package model;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class Usuario extends Bean{
    private String nome;
    private Integer id;
    private String login;
    private String senha;
    private Integer rede_ensino;
    private String sexo;

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getRede_ensino() {
        return rede_ensino;
    }

    public void setRede_ensino(Integer rede_ensino) {
        this.rede_ensino = rede_ensino;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
