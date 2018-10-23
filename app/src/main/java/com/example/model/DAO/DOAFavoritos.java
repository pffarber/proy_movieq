package com.example.model.DAO;

import com.example.model.pojo.Categoria;
import com.example.model.pojo.CategoriaRecycleViewFragment;
import com.example.model.pojo.Pelicula;
import com.example.model.pojo.PeliculaContainer;
import com.example.utils.ResultListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DOAFavoritos {

    public void obtenerListaFavoritos(ArrayList<CategoriaRecycleViewFragment> listaCategoria,
                                      final ResultListener<List<Pelicula>> escuchadorDelControlador) {
        escuchadorDelControlador.finish(entregarListaFavoritos(listaCategoria));
    }


    public ArrayList<Pelicula> entregarListaFavoritos(ArrayList<CategoriaRecycleViewFragment> listaCategoria) {
        ArrayList<Pelicula> listaFavoritos = new ArrayList<>();
        for (Categoria CategoriaActual : listaCategoria) {
            for (Pelicula peliculaActual : CategoriaActual.getPeliculas()) {
                if (peliculaActual.getEstaFavorito()) {
                    if(!listaFavoritos.contains(peliculaActual)) {
                        listaFavoritos.add(peliculaActual);
                    }
                }
            }
        }
        return listaFavoritos;
    }


}