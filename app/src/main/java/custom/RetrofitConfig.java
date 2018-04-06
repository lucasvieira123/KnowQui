package custom;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lucas-vieira on 05/04/18.
 */

public class RetrofitConfig {
    private Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit =  new Retrofit.Builder()
                .baseUrl(RequestAndResponseUrlConst.LISTA_PERGUNTA)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public PerguntaService getPerguntaService() {
        return retrofit.create(PerguntaService.class);
    }
}
