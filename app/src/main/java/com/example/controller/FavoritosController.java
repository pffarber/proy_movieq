package com.example.controller;

import com.example.model.DAO.DOAFavoritos;
import com.example.model.pojo.CategoriaRecycleViewFragment;
import com.example.model.pojo.Pelicula;
import com.example.utils.ResultListener;

import java.util.ArrayList;
import java.util.List;

public class FavoritosController {



    public void getFavoritosLista(ArrayList<CategoriaRecycleViewFragment> listaCategorias,
                                  final ResultListener<List<Pelicula>> escuchadorDeLaVista) {

        if (hayInternet()) {
            DOAFavoritos daoFavoritos = new DOAFavoritos();

            daoFavoritos.obtenerListaFavoritos(listaCategorias, new
                    ResultListener<List<Pelicula>>() {
                @Override
                public void finish(List<Pelicula> resultado) {

                    escuchadorDeLaVista.finish(resultado);
                }
            });
        }else{
            //por el momento nada
        }
    }


    public Boolean hayInternet(){
        return true;
    }
}
