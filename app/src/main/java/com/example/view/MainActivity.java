package com.example.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.controller.FavoritosController;

import com.example.controller.PeliculaController;
import com.example.model.pojo.Categoria;
import com.example.model.pojo.CategoriaRecycleViewFragment;
import com.example.model.pojo.Pelicula;
import com.example.model.pojo.Trailer;
import com.example.recyclerviewbase.R;
import com.example.utils.ResultListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements PeliculasFragment.NotificadorPelicula,
        BottomNavigationView.OnNavigationItemSelectedListener,
        ListViewCategoriasBuscar.NotificadorSearch,
        UnaCaegoriaViewPageFragment.SolicitudUnaPagina,
YouTubeFragment.NotificadorTrailer ,
DetallePeliculaFragment.NotificadorVerTrailer{

    private static final String FRAGMENT_TODAS_CATEEGORIAS_TAG = "Todas categorias";
    private static final String FRAGMENT_SEARCH_TAG = "Search";
    private static final String KEY_TRAILER = "keyTrailer";
    private TodasCategoriasFragment todasCategoriasfragment;

    public static ArrayList<Pelicula> listadoFavoritos;
    private int idContenedorFragments;
    private Fragment fragmentActual;
    private YouTubeFragment youTubeFragment;
    private UnaCaegoriaViewPageFragment unaCaegoriaViewPageFragment;
    private BottomNavigationView mNavigationView;
    private final int HOME = 1;


    @SuppressLint("WrongViewCast")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializo el listado de favoritos
        listadoFavoritos= new ArrayList<>();
        //Cargo el fragment categorías
        todasCategoriasfragment = new TodasCategoriasFragment();
        idContenedorFragments = R.id.contenedor_Fragments;
        cargarFramgent(todasCategoriasfragment, idContenedorFragments,FRAGMENT_TODAS_CATEEGORIAS_TAG);
        mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        Fragment fragmentACargar = null;
        switch (item.getItemId()) {
            case R.id.navigation_home: {
                //INICIO LA ACTIVIDAD
                limpiarPantalla(HOME);
                cargarFramgent(todasCategoriasfragment, idContenedorFragments, FRAGMENT_TODAS_CATEEGORIAS_TAG);
                break;
            }
            case R.id.navigation_watchlist: {
                CategoriaRecycleViewFragment categoriaFavorito =  new CategoriaRecycleViewFragment
                        (listadoFavoritos,"Favoritos",true) ;
                abrirGrilla(categoriaFavorito);
                break;
            }
            case R.id.navigation_search: {
                abrirFragmentSearch();
                break;
            }
        }
        //esto es para cerrar el menu ( para ocultarlo)
        return true;

    }


    void abrirFragmentSearch(){
        FragmentSearch fragmentSearch = new FragmentSearch();
        cargarFramgent(fragmentSearch,idContenedorFragments,FRAGMENT_SEARCH_TAG);

    }

    void limpiarPantalla(int flag_destino){

        switch (flag_destino){
            case HOME :{

                FragmentManager fragmentManager = getSupportFragmentManager();
                int numero = fragmentManager.getBackStackEntryCount();
                for(int i = 0; i < numero-1; i++) {
                    fragmentManager.popBackStack();
                }

            }

        }


    }

   private void cargarFramgent(Fragment fragmentACargar, int idContenedor,String tag) {

        //INICIO LA ACTIVIDAD
       FragmentManager fragmentManager = getSupportFragmentManager();
    //   Fragment fragmentAMostrar = fragmentManager.findFragmentByTag(fragmentACargar.getClass().getName());
      //¿el fragment que quiero cargar (fragmentACargar) que posee el tag tag,
      //fue cargado anteriormente y lo tengo almacenado en fragmentManager?
       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       Fragment fragmentBuscado = fragmentManager.findFragmentByTag(tag);
       if(fragmentBuscado == null){
          // FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
          // fragmentTransaction.addToBackStack(fragmentACargar.getClass().getName());
          //¿cargaste alguno otro fragment desde que iniciamos la app?
            if(fragmentActual!=null) {
                fragmentTransaction.hide(fragmentActual);
                fragmentTransaction.add(idContenedor,fragmentACargar,tag).commit();
                fragmentActual = fragmentACargar;
            }
            else{
                fragmentTransaction.add(idContenedor, fragmentACargar,tag).commit();
                fragmentActual = fragmentACargar;
            }

        }else{

            fragmentTransaction.hide(fragmentActual).show(fragmentBuscado).commit();
          //  fragmentTransaction.hide(fragmentActual).show(idContenedor,fragmentBuscado,tag).commit();
            fragmentActual = fragmentBuscado;
        }
       fragmentTransaction.addToBackStack(fragmentACargar.getClass().getName());
    }


        @Override
    public void notificar(Categoria categoria, int posicion) {


            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment fragmentBuscado = fragmentManager.findFragmentByTag(categoria.getNombreCategoria()+"_detalle");
            if(fragmentBuscado == null){

    //   if (unaCaegoriaViewPageFragment==null) {
           unaCaegoriaViewPageFragment = new UnaCaegoriaViewPageFragment();
           Bundle unBundle = new Bundle();
           unBundle.putInt(UnaCaegoriaViewPageFragment.CLAVE_POSICION, posicion);
           unBundle.putSerializable(UnaCaegoriaViewPageFragment.CLAVE_CATEGORIAS, categoria);
           unaCaegoriaViewPageFragment.setArguments(unBundle);
        }else{
           unaCaegoriaViewPageFragment.setPosicion(posicion);
           unaCaegoriaViewPageFragment.setCategoria(categoria);
       }
        cargarFramgent(unaCaegoriaViewPageFragment,idContenedorFragments,categoria.getNombreCategoria()+"_detalle");
    }


    @Override
    public void abrirGrilla(Categoria categoria){
        //CARGO EL BUNDLE PARA Enviar al fragment
        CategoriaRecycleViewFragment categoriaAbrir = new CategoriaRecycleViewFragment
                (categoria.getPeliculas(),categoria.getNombreCategoria(),true);
        PeliculasFragment peliculasFragment = new PeliculasFragment();
        //a partir de que declaramos el numero de pagina como static esto no sería necesario

        if(categoria.getNombreCategoria()!="Favoritos"){
            int posicion= todasCategoriasfragment.encontrarCategoria(categoria.getNombreCategoria());
            PeliculaController peliculaController = todasCategoriasfragment.getListaPeliculasFragment().get(posicion).getPeliculaController();
            peliculasFragment.setPeliculaController(peliculaController);

        }
        else{
            peliculasFragment.setFragmentPaginable(false);
        }
        Bundle unBundle = new Bundle();
        unBundle.putSerializable(PeliculasFragment.CLAVE_CATEGORIA, categoriaAbrir);
        peliculasFragment.setArguments(unBundle);

        cargarFramgent(peliculasFragment,idContenedorFragments,categoria.getNombreCategoria());

    }

    @Override
    public void solicituddeActualizarDatosFragmentsPelicula(Pelicula pelicula) {
        if (pelicula.getEstaFavorito()) {
            if (!listadoFavoritos.contains(pelicula)) {
                listadoFavoritos.add(pelicula);
            }}
            else {
                if (listadoFavoritos.contains(pelicula)) {
                    listadoFavoritos.remove(pelicula);
                }
            }
            todasCategoriasfragment.actualizarPelicula(pelicula);
        }


    @Override
    public void solicitarPagina(String nombreCategoria, final ResultListener<List<Pelicula>> escuchadorFragmentUnaCat) {
            todasCategoriasfragment.solicitarPagina(nombreCategoria,
                new ResultListener<List<Pelicula>>()
                {
                    @Override
                    public void finish(List<Pelicula> resultado) {
                        escuchadorFragmentUnaCat.finish(resultado);
                    }

                });
            }

    @Override
    public void abrirCategoria(String nombreCategoria) {
        Categoria categoriaACargar= todasCategoriasfragment.getCategoriaXTitulo(nombreCategoria);
        abrirGrilla(categoriaACargar);

    }

    @Override
    public void notificarTrailer(String keyTrailer) {
  //      BlankFragment blankFragment = new BlankFragment();
        Intent unIntent = new Intent(this, YouTubeActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString(KEY_TRAILER, keyTrailer);

        //blankFragment.setArguments(bundle);
        unIntent.putExtras(bundle);
        startActivity(unIntent);
        /*
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedor_Fragments, blankFragment);
        fragmentTransaction.commit();
*/


    }
    public void notificarVerTrailer(String keyTrailer) {
        //      BlankFragment blankFragment = new BlankFragment();
        Intent unIntent = new Intent(this, YouTubeActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString(KEY_TRAILER, keyTrailer);

        //blankFragment.setArguments(bundle);
        unIntent.putExtras(bundle);
        startActivity(unIntent);
        /*
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedor_Fragments, blankFragment);
        fragmentTransaction.commit();
*/
    }
}
