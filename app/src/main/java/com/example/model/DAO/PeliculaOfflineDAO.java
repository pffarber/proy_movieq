package com.example.model.DAO;

import android.content.Context;
import android.content.res.AssetManager;


import com.example.model.pojo.Categoria;
import com.example.model.pojo.Pelicula;
import com.example.model.pojo.PeliculaContainer;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class PeliculaOfflineDAO {


   public ArrayList<Categoria> getTodasCategoriasIniciales(Context context) {
        int i;
        ArrayList<Categoria> listadoCategorias = new ArrayList<>();
        for (i = 1; i < 7; i++)
        {
            Categoria categoria = new Categoria((ArrayList<Pelicula>) getListaDePeliculaFromArchivo(context), "Categoria " + i);
            listadoCategorias.add(categoria);
        }
        return listadoCategorias;
    }


    //Creo un método para pedir la lista de productos del archivo Json
    public List<Pelicula> getListaDePeliculaFromArchivo(Context context){
        List<Pelicula> listaDePeliculas = new ArrayList<>();

        //Pedimos el assetManager para acceder a los archivos dentro de la carpeta assets
        AssetManager assetManager = context.getAssets();
        try {
            //Creamos un stream para leer el archivo Json.
            InputStream archivoJsonPeliculas = assetManager.open("listapeliculaspopulares.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(archivoJsonPeliculas));

            //Creamos un Objeto de la clase Gson que me permitirá sencillamente parsear el Json.
            Gson gson = new Gson();

            //Utilizando el objeto gson y el método fromJson, realizamos el parsing el archivo
            // que tenemos en el bufferReaderIn y utilizando como “molde” la clase ProductoContainer.
            PeliculaContainer peliculasContainer = gson.fromJson(bufferedReader, PeliculaContainer.class);
            listaDePeliculas = peliculasContainer.getListaDePeliculas();

        } catch (IOException e) {
            e.printStackTrace();
        }
        //Devuelvo la lista cargada
        return listaDePeliculas;
    }


}



