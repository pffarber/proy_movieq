package com.example.model.DAO;

import com.example.model.pojo.Pelicula;
import com.example.model.pojo.PeliculaContainer;
import com.example.model.pojo.Trailer;
import com.example.model.pojo.TrailerContainer;
import com.example.utils.ResultListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DAOPeliculaRetrofit {


    private static final String BASE_URL = "https://api.themoviedb.org";
      private static final String API_KEY = "61a79d269cb6589d31b134b7d4597e55";
    private static final String LANGUAGE = "en-EN";

        private Retrofit retrofit;
        private ServicePeliculas servicePelicula;

        public DAOPeliculaRetrofit() {
            //Retrofit usa OkHttpClient y pido un constructor
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

            retrofit = builder.client(httpClient.build()).build();
            servicePelicula = retrofit.create(ServicePeliculas.class);

        }

    public void obtenerPeliculaDeInternetPaginado(String nombreCategoria,Integer paginaASolicitar,
                                                  final ResultListener<List<Pelicula>> escuchadorDelControlador) {
        Call<PeliculaContainer> llamada = servicePelicula.getPeliculasPaginables(nombreCategoria, API_KEY, LANGUAGE, paginaASolicitar);
        llamada.enqueue(new Callback<PeliculaContainer>() {
            @Override
            public void onResponse(Call<PeliculaContainer> call, Response<PeliculaContainer> response) {
                escuchadorDelControlador.finish(response.body().getResults());
            }

            @Override
            public void onFailure(Call<PeliculaContainer> call, Throwable t) {
                escuchadorDelControlador.finish(new ArrayList<Pelicula>());
            }
        });
    }

    public void searchTexto(String textoABuscar, final ResultListener<List<Pelicula>> otroescuchadorDelControlador) {
        Call<PeliculaContainer> llamada = servicePelicula.getSearchMovie(API_KEY, LANGUAGE,textoABuscar);
        llamada.enqueue(new Callback<PeliculaContainer>() {
            @Override
            public void onResponse(Call<PeliculaContainer> call, Response<PeliculaContainer> response) {
                otroescuchadorDelControlador.finish(response.body().getResults());

            }

            @Override
            public void onFailure(Call<PeliculaContainer> call, Throwable t) {
                otroescuchadorDelControlador.finish(new ArrayList<Pelicula>());
            }
        });
    }


    public void obtenerTrailers(Integer movie_id, final ResultListener<List<Trailer>> otroescuchadorDelControlador) {
        Call<TrailerContainer> llamada2 = servicePelicula.getTrailers(movie_id,API_KEY, LANGUAGE);
        llamada2.enqueue(new Callback<TrailerContainer>() {
            @Override
            public void onResponse(Call<TrailerContainer> call, Response<TrailerContainer> responseTrailer) {
                otroescuchadorDelControlador.finish(responseTrailer.body().getListaDeTrailers());
            }

            @Override
            public void onFailure(Call<TrailerContainer> call, Throwable t) {
                otroescuchadorDelControlador.finish(new ArrayList<Trailer>());
            }
        });
    }
}



