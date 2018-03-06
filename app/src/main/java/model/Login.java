package model;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class Login extends Bean{

    private String login;
    private String senha;
    //nao é boa pratica guardar senha no banco
    private Integer id;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
