package model;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class Usuario {
    private Integer Login_id;
    private String nome;
    private Integer idade;
    private Integer id;

    public Integer getLogin_id() {
        return Login_id;
    }

    public void setLogin_id(Integer login_id) {
        this.Login_id = login_id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
