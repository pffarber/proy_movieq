package com.example.controller;

import com.example.model.DAO.DAOPeliculaRetrofit;
import com.example.model.pojo.Genero;
import com.example.model.pojo.Pelicula;
import com.example.model.pojo.Trailer;
import com.example.utils.ResultListener;

import java.util.ArrayList;
import java.util.List;

public class PeliculaController {


    private static final int POPULARES = 0;
    private static final int ESTRENOS = 3;
    private static final int CARTELERA = 1;
    private static final int RANKING =2 ;
    private static ArrayList<Integer> numeroPaginas;
    private int indiceCategoria;
    private int pagina = 1;
    private Integer offset = 0;
    private static final Integer PAGE_SIZE = 20;
    private boolean hayPaginas;
    private String nombreCategoria;
    private String category;
    private Boolean paginable=true;

    public PeliculaController() {
        hayPaginas = true;
    }


    public PeliculaController(String nombreCategoria)
    {
        hayPaginas = true;
        this.nombreCategoria=nombreCategoria;
        category=encontrarCategoryApi(nombreCategoria);
        if(numeroPaginas==null) {
            numeroPaginas= new ArrayList<>();
            numeroPaginas.add(1);
            numeroPaginas.add(1);
            numeroPaginas.add(1);
            numeroPaginas.add(1);
        }
    }


    private String encontrarCategoryApi(String nombreCategoria) {
        String resultado=new String();
        switch (nombreCategoria) {
            case ("Populares"): {
                indiceCategoria=POPULARES;
                return "popular";

            }
            case ("Cartelera"): {
                indiceCategoria=CARTELERA;
                return  "now_playing";
            }
            case ("Ranking"): {
                indiceCategoria=RANKING;
                return  "top_rated";
            }
            case ("Proximos Estrenos"): {
                indiceCategoria=ESTRENOS;
                return "upcoming";

            }
        }
        return "Favoritos";

    }


    //El controller se encarga de pedirle la lista al DAO y luego le avisa al listener de la vista
    // que ya esta disponible para que la use.
    public void getPeliculaLista(final ResultListener<List<Pelicula>> escuchadorDeLaVista) {

        if (category!="Favoritos") {
            if (hayInternet()) {
                DAOPeliculaRetrofit daoPeliculaRetrofit = new DAOPeliculaRetrofit();

                daoPeliculaRetrofit.obtenerPeliculaDeInternetPaginado(category, numeroPaginas.get(indiceCategoria), new ResultListener<List<Pelicula>>() {
                    @Override
                    public void finish(List<Pelicula> resultado) {
                        if (resultado.size() < PAGE_SIZE) {
                            hayPaginas = false;
                        }
                        numeroPaginas.set(indiceCategoria,numeroPaginas.get(indiceCategoria)+1);
                        escuchadorDeLaVista.finish(resultado);
                    }
                });
            } else {
                //por el momento nada
            }
        }
    }

    public void dameUnaPagina(final ResultListener<List<Pelicula>> escuchadorDeLaVista) {

        if (category != "Favoritos") {
            if (hayInternet()) {
                DAOPeliculaRetrofit daoPeliculaRetrofit = new DAOPeliculaRetrofit();

                daoPeliculaRetrofit.obtenerPeliculaDeInternetPaginado(category, numeroPaginas.get(indiceCategoria), new ResultListener<List<Pelicula>>() {
                    @Override
                    public void finish(List<Pelicula> resultado) {
                        if (resultado.size() < PAGE_SIZE) {
                            hayPaginas = false;
                        }
                        numeroPaginas.set(indiceCategoria,numeroPaginas.get(indiceCategoria)+1);
                        escuchadorDeLaVista.finish(resultado);
                    }
                });
            } else {
                //por el momento nada
            }
        }
    }


    public void getTrailersPelicula(Integer id, final ResultListener<List<Trailer>> escuchador){
        if(hayInternet()){
            DAOPeliculaRetrofit daoPeliculaRetrofit = new DAOPeliculaRetrofit();
            daoPeliculaRetrofit.obtenerTrailers(id, new ResultListener<List<Trailer>>() {
                @Override
                public void finish(List<Trailer> resultado) {
                    escuchador.finish(resultado);


                }
            });
        }
    }

    /*public void getGenerosPelicula(Integer id, final ResultListener<List<Genero>> escuchador){
        if(hayInternet()){
            DAOPeliculaRetrofit daoPeliculaRetrofit = new DAOPeliculaRetrofit();
            daoPeliculaRetrofit.obtenerGeneros(id, new ResultListener<List<Trailer>>() {
                @Override
                public void finish(List<Trailer> resultado) {
                    escuchador.finish(resultado);


                }
            });
        }
    }
*/
    public void searchPelicula(String textoABuscar, final ResultListener<List<Pelicula>> escuchador){
        if(hayInternet()){
            DAOPeliculaRetrofit daoPeliculaRetrofit = new DAOPeliculaRetrofit();
            daoPeliculaRetrofit.searchTexto(textoABuscar, new ResultListener<List<Pelicula>>() {
                @Override
                public void finish(List<Pelicula> resultado) {
                    escuchador.finish(resultado);


                }
            });
        }
    }






        public Boolean hayInternet(){

        return true;
        }

    public boolean isHayPaginas() {

        if (category == "Favoritos"){
            return false;
        }
        return hayPaginas;
    }


}

