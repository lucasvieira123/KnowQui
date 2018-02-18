package Model;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class Usuario {
    private Login login;
    private String nome;
    private Integer idade;

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }
}
