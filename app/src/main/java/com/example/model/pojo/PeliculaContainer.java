package com.example.model.pojo;



import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PeliculaContainer {

    /* Si el nombre de mi atributo no quiero hacerlo coincidir
    con el que viene en el Json, agrego un SerializedName
    que si coincida con el nombre que está en el json y a mi atributo le pongo el nombre que quiero */
    @SerializedName("results")
    private List<Pelicula> listaDePeliculas;


    public List<Pelicula> getListaDePeliculas() {
        return this.listaDePeliculas;
    }

    public void setListaDePeliculas(List<Pelicula> listaDeProductos) {
        this.listaDePeliculas = listaDePeliculas;
    }

    public List<Pelicula> getResults() {
        return listaDePeliculas;
    }


}
