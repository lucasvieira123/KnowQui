package custom;

import model.Pergunta;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by lucas-vieira on 05/04/18.
 */

public interface PerguntaService {

    @GET("listapergunta")
    Call<Pergunta[]> listaDePerguntas();

}
