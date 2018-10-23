package com.example.model.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TrailerContainer {

    /* Si el nombre de mi atributo no quiero hacerlo coincidir
    con el que viene en el Json, agrego un SerializedName
    que si coincida con el nombre que está en el json y a mi atributo le pongo el nombre que quiero */
   /* @SerializedName("id")
    @Expose
    private Integer id;
*/
    @SerializedName("results")
    @Expose
    private List<Trailer> listaDeTrailers;


    public List<Trailer> getListaDeTrailers() {
        //if(listaDeTrailers == null){
        //listaDeTrailers = new ArrayList<>() ;
            //}

        return listaDeTrailers;
    }

    public void setListaDeTrailers(List<Trailer> listaDeTrailers) {
        this.listaDeTrailers = listaDeTrailers;
    }

    public TrailerContainer() {
        listaDeTrailers = new ArrayList<>();
    }

    //public List<Trailer> getResults() {
    //  return listaDeTrailers;
    //}


/*    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }*/
}
