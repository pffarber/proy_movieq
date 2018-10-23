package com.example.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.controller.PeliculaController;
import com.example.model.pojo.Categoria;
import com.example.model.pojo.CategoriaRecycleViewFragment;
import com.example.model.pojo.Pelicula;
import com.example.model.pojo.Trailer;
import com.example.recyclerviewbase.R;
import com.example.utils.ResultListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnaCaegoriaViewPageFragment extends Fragment {


    public static final String CLAVE_CATEGORIAS = "nombre categoria";
    public static final String CLAVE_POSICION = "posicion pelicula seleccionada";
    Categoria categoria;
    int posicion;
    private SolicitudUnaPagina solicitadorPagina;
    private FragmentActivity myContext;
    private ArrayList<DetallePeliculaFragment> listaDetallePeliculaFragment;

    private AdapterDetalleCategoriaPageView adapterDetalleCategoriaPageView;
    private List<Pelicula> nuevaPagina;
    private ViewPager viewPagerD;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_una_caegoria_view_page, container, false);
        recibirParametros();
        listaDetallePeliculaFragment = armarCadenaFragments(categoria);
        nuevaPagina = new ArrayList<>();


//Encuentro el viewPager
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager_unacat);
        viewPagerD=viewPager;

        cargarViewPager(viewPagerD);


        //listener para solicitar más peliculas al recycler
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) {
                if(categoria.getNombreCategoria()!="Favoritos") {
                    if (position > categoria.getPeliculas().size() - 5) {
                        solicitadorPagina.solicitarPagina(categoria.getNombreCategoria(),
                                new ResultListener<List<Pelicula>>() {
                                    @Override
                                    public void finish(List<Pelicula> resultado) {
                                        agregarPaginaFragments(resultado);
                                    }
                                });
                    }
                    String titulo = viewPager.getAdapter().getPageTitle(position).toString();
                    Toast.makeText(getContext(), titulo, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                int i = 0;

            }
        });

        return view;

    }

    private void cargarViewPager(ViewPager viewPager) {

        listaDetallePeliculaFragment = armarCadenaFragments(categoria);
        nuevaPagina = new ArrayList<>();


        //Creo el Adapter y le paso un fragment manager y la lista de fragments que mostrará
        adapterDetalleCategoriaPageView =
                new AdapterDetalleCategoriaPageView(getChildFragmentManager(), listaDetallePeliculaFragment);
        //Al viewPager le "presento" el adapter
        viewPager.setAdapter(adapterDetalleCategoriaPageView);
        // que el view pager comenzará en la posición deseada de la pelicula seleccionada
        viewPager.setCurrentItem(posicion);
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(12);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        cargarViewPager(viewPagerD);

    }


    ArrayList<DetallePeliculaFragment> armarCadenaFragments(Categoria categoria) {
        ArrayList<DetallePeliculaFragment> listaDetallePeliculaFragment = new ArrayList<>();
        PeliculaController peliculaController = new PeliculaController();

        for (final Pelicula peliculaActual : categoria.getPeliculas()) {
      /*     // if (peliculaActual.getTrailerList() == null) { Ó
                if(peliculaActual.getTrailerList().size()==0){
                peliculaController.getTrailersPelicula(peliculaActual.getIdPelicula(),new ResultListener<List<Trailer>>() {
                    @Override
                    public void finish(List<Trailer> resultado) {
                      // peliculaActual.setTrailerList(resultado);

                   }
                });
            }

*/
            listaDetallePeliculaFragment.add(DetallePeliculaFragment.DameUnFragmentDetallePelicula
                    (peliculaActual, categoria.getNombreCategoria()));
       }
            return listaDetallePeliculaFragment;
        }


    private void agregarPaginaFragments(List<Pelicula> listaPeliculas){
        ArrayList<DetallePeliculaFragment> listaDetallePeliculaFragment = new ArrayList<>();
        for (Pelicula peliculaActual : listaPeliculas) {
            listaDetallePeliculaFragment.add(DetallePeliculaFragment.DameUnFragmentDetallePelicula
                    (peliculaActual, categoria.getNombreCategoria()));

        }
        adapterDetalleCategoriaPageView.addPaginaPelicula(listaDetallePeliculaFragment);

    }

    private void recibirParametros() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            categoria = (Categoria) bundle.getSerializable(CLAVE_CATEGORIAS);
            posicion = bundle.getInt(CLAVE_POSICION);
        }
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }


    public interface SolicitudUnaPagina {


        void solicitarPagina(String nombreCategoria, ResultListener<List<Pelicula>> nuevaPelicula);


    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            solicitadorPagina = (UnaCaegoriaViewPageFragment.SolicitudUnaPagina) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() + " implementar SolicitadorPagina");
        }
    }



}