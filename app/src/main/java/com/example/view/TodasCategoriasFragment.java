package com.example.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.model.pojo.Categoria;
import com.example.model.pojo.CategoriaRecycleViewFragment;
import com.example.model.pojo.Pelicula;
import com.example.recyclerviewbase.R;
import com.example.utils.ResultListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodasCategoriasFragment extends Fragment  {
//implements PeliculasFragment.NotificarEntreFragment

    private ArrayList<PeliculasFragment> listaPeliculasFragment;
    private ArrayList<CategoriaRecycleViewFragment> listadoCategorias;

    public static final String CONTAINER1 = "Proximos Estrenos";
    public static final String CONTAINER2 = "Populares";
    public static final String CONTAINER3 = "Cartelera";
    public static final String CONTAINER4 = "Ranking";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.todascategoriasfragment, container, false);
        //variables

        if(listaPeliculasFragment!=null) {
       /*     cargarViejos(listaPeliculasFragment.get(0), R.id.container_proximos_estrenos);
            cargarViejos(listaPeliculasFragment.get(1), R.id.container_populares);
            cargarViejos(listaPeliculasFragment.get(2), R.id.container_cartelera);
            cargarViejos(listaPeliculasFragment.get(3), R.id.container_x_ranking);
*/


            cargarFragmentNuevo(R.id.container_proximos_estrenos,listaPeliculasFragment.get(0));
            cargarFragmentNuevo(R.id.container_populares,listaPeliculasFragment.get(1));
            cargarFragmentNuevo(R.id.container_cartelera,listaPeliculasFragment.get(2));
            cargarFragmentNuevo(R.id.container_x_ranking,listaPeliculasFragment.get(3));


        }else{

            listaPeliculasFragment = new ArrayList<>();
            listadoCategorias = new ArrayList<>();
            ArrayList<Pelicula> listadoPelicula = new ArrayList<>();
            CategoriaRecycleViewFragment categoriaAcargar = new CategoriaRecycleViewFragment
                    (listadoPelicula, CONTAINER1, false);
            listadoCategorias.add(categoriaAcargar);
            cargarDatosFragmenNuevo(categoriaAcargar, R.id.container_proximos_estrenos);

            listadoPelicula = new ArrayList<>();
            categoriaAcargar = new CategoriaRecycleViewFragment
                    (listadoPelicula, CONTAINER2, false);
            listadoCategorias.add(categoriaAcargar);
            cargarDatosFragmenNuevo(categoriaAcargar, R.id.container_populares);

            listadoPelicula = new ArrayList<>();
            categoriaAcargar = new CategoriaRecycleViewFragment
                    (listadoPelicula, CONTAINER3, false);
            listadoCategorias.add(categoriaAcargar);
            cargarDatosFragmenNuevo(categoriaAcargar, R.id.container_cartelera);

            listadoPelicula = new ArrayList<>();
            categoriaAcargar = new CategoriaRecycleViewFragment
                    (listadoPelicula, CONTAINER4, false);
            listadoCategorias.add(categoriaAcargar);
            //listadoCategorias.add(categoriaAcargar);
            cargarDatosFragmenNuevo(categoriaAcargar, R.id.container_x_ranking);

        }
        return view;
    }


    //EUGENIO: Cargo Fragment CATEGORIA
    private void cargarDatosFragmenNuevo(CategoriaRecycleViewFragment categoriaACargar,int idContainer) {
        PeliculasFragment peliculasFragment = new PeliculasFragment();
        listaPeliculasFragment.add(peliculasFragment);
        Bundle unBundle = new Bundle();
        unBundle.putSerializable(PeliculasFragment.CLAVE_CATEGORIA, categoriaACargar);
        peliculasFragment.setArguments(unBundle);
        cargarFragmentNuevo(idContainer, peliculasFragment);
    }

    private void cargarFragmentNuevo(int idContainer,PeliculasFragment peliculasFragment){

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(idContainer, peliculasFragment);
        fragmentTransaction.commit();
    }




    void actualizarPelicula(Pelicula pelicula) {
        for (PeliculasFragment peliculasFragmentActual : listaPeliculasFragment) {
            if (peliculasFragmentActual.getListadoPeliculas().contains(pelicula)) {
                peliculasFragmentActual.actualizarPelicula(pelicula);
            }
        }
    }

    public void solicitarPagina(String nombreCategoria, final ResultListener<List<Pelicula>> escuchadorMain) {
        int posicion=encontrarCategoria(nombreCategoria);
        listaPeliculasFragment.get(posicion).solicitarPagina(
                new ResultListener<List<Pelicula>> (){
                    @Override
                    public void finish(List<Pelicula> resultado) {
                        escuchadorMain.finish(resultado);
                    }});

    }

    public ArrayList<PeliculasFragment> getListaPeliculasFragment() {
        return listaPeliculasFragment;
    }

    //devuelve -1 si no encuentra la categoría
    public int encontrarCategoria(String nombreCategoria) {
        int posicion = 0;
        for (PeliculasFragment peliculasFragmentActual : listaPeliculasFragment) {
            if (peliculasFragmentActual.getCategoriaACargar().getNombreCategoria().equals(nombreCategoria)) {
                return posicion;
            }
            posicion++;
        }
        return -1;
    }



    public Categoria getCategoriaXTitulo(String nombreCategoria) {
        int posicion = encontrarCategoria(nombreCategoria);
        return listaPeliculasFragment.get(posicion).getCategoriaACargar();


    }
}



