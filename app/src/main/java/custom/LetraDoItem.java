package custom;

/**
 * Created by lucas-vieira on 11/04/18.
 */

public enum LetraDoItem {

    levraA("A"),levraB("B"),levraC("C"),levraD("D");

    LetraDoItem(String letraResposta) {
        this.letraResposta = letraResposta;
    }

    public String letraResposta;

    public String getLetraResposta() {
        return letraResposta;
    }


}
