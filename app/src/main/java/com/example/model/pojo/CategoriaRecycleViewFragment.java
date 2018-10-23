package com.example.model.pojo;

import com.example.model.pojo.Categoria;

import java.io.Serializable;
import java.util.ArrayList;


public class CategoriaRecycleViewFragment extends Categoria implements Serializable {
    Boolean GrillaActiva;



    public CategoriaRecycleViewFragment() {
        super();
        GrillaActiva = false;
    }



    public CategoriaRecycleViewFragment(ArrayList<Pelicula> listadoPeliculas, String tituloCategoria, Boolean grillaActiva ) {
        super(listadoPeliculas,tituloCategoria);
        GrillaActiva = grillaActiva;

    }



    public Boolean getGrillaActiva() {
        return GrillaActiva;
    }

    public void setGrillaActiva(Boolean grillaActiva) {
        GrillaActiva = grillaActiva;
    }


}
