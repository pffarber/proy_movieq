package com.example.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controller.PeliculaController;
import com.example.model.pojo.Categoria;
import com.example.model.pojo.CategoriaRecycleViewFragment;
import com.example.model.pojo.Pelicula;
import com.example.recyclerviewbase.R;
import com.example.utils.ResultListener;
import com.example.view.AdapterPeliculaRecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PeliculasFragment extends Fragment implements AdapterPeliculaRecyclerView.NotificadorPeliculaCelda {

    public static final String CLAVE_CATEGORIA = "categoria";
    private static final int CANTIDAD_ELEMENTOS_PARA_NUEVO_PEDIDO = 3;

    private RecyclerView recyclerView;
    private NotificadorPelicula notificadorPelicula;
    private AdapterPeliculaRecyclerView adapter;
    private TextView textTituloCategoria;
    private CategoriaRecycleViewFragment categoriaACargar;
    private ProgressBar progressBar;
    private PeliculaController peliculaController;
    private Boolean isLoading;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private List<Pelicula> nuevaPeliculas;
    private Boolean fragmentPaginable;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_peliculas, container, false);
        fragmentPaginable=true;
        //llamamos a la función que implementa la interfaz
        //  onAttachToParentFragment(getParentFragment());

        /*{
            Toast.makeText(getContext(),"dfds",Toast.LENGTH_LONG ).show();
        }
*/

        isLoading = false;
        recibirCategoriaACargar();
        if (peliculaController == null) {
            peliculaController = new PeliculaController(categoriaACargar.getNombreCategoria());
        }

        recyclerView = view.findViewById(R.id.recycler_id);
        CargarRecycle();
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        textTituloCategoria = (TextView) view.findViewById(R.id.nombreCategoria);
        textTituloCategoria.setText(categoriaACargar.getNombreCategoria());


        if (categoriaACargar.getPeliculas().size() == 0) {
            cargarPelicula();
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int posicionFinal;
                int posicionActual;
                if (isLoading) {
                    return;
                }
                if (!categoriaACargar.getGrillaActiva()) {
                    posicionFinal = linearLayoutManager.getItemCount();
                    posicionActual = linearLayoutManager.findLastVisibleItemPosition();
                } else {
                    posicionFinal = gridLayoutManager.getItemCount();
                    posicionActual = gridLayoutManager.findLastVisibleItemPosition();
                }
                if (fragmentPaginable && (posicionFinal - posicionActual <= CANTIDAD_ELEMENTOS_PARA_NUEVO_PEDIDO)) {
                    cargarPelicula();
                }
            }
        });
        //Listener
        textTituloCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GUARDO EN UNA VARIABLE Drawable la imagen
                //ENVIARLE EL MENSAJE AL ACTIVITY
                notificadorPelicula.abrirGrilla(categoriaACargar);
            }
        });
        return view;
    }


    public void setPeliculaController(PeliculaController peliculaController){
        this.peliculaController=peliculaController;
    }

    public PeliculaController getPeliculaController() {
        return peliculaController;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            int posicionFinal,posicionActual;
            if(!categoriaACargar.getGrillaActiva()) {
                posicionFinal = linearLayoutManager.getItemCount();
                posicionActual = linearLayoutManager.findLastVisibleItemPosition();
            }else {
                posicionFinal = gridLayoutManager.getItemCount();
                posicionActual = gridLayoutManager.findLastVisibleItemPosition();
            }
            adapter.notifyItemRangeChanged(0,6);
            //adapter.notifyItemRangeChanged(posicionActual,posicionFinal-posicionActual);
         //   adapter.notify();
        } else {
            int posicionFinal,posicionActual;
            if(!categoriaACargar.getGrillaActiva()) {
                posicionFinal = linearLayoutManager.getItemCount();
                posicionActual = linearLayoutManager.findLastVisibleItemPosition();
            }else {
                posicionFinal = gridLayoutManager.getItemCount();
                posicionActual = gridLayoutManager.findLastVisibleItemPosition();
            }

                adapter.notifyItemRangeChanged(0,6);
            //ItemRangeChanged(posicionActual,posicionFinal-posicionActual);
        }
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            notificadorPelicula = (NotificadorPelicula) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(
                    context.toString() + " implementar NotificarPelicula");
        }

    }

    public CategoriaRecycleViewFragment getCategoriaACargar() {
        return categoriaACargar;
    }

    private void CargarRecycle() {
        adapter = new AdapterPeliculaRecyclerView(categoriaACargar.getPeliculas(), this);
        //el layout manager es la disposicion visual del recycler (lineal o grilla, con orientacion vertical u horizontal)
        if (!categoriaACargar.getGrillaActiva()) {
            linearLayoutManager = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        } else {
           gridLayoutManager =  new GridLayoutManager(getActivity(), 3);
            recyclerView.setLayoutManager(gridLayoutManager);
        }
        //si le puse match parent al alto y ancho del recycler, el setHasFixedSize mejora la performance
        recyclerView.setHasFixedSize(true);
        //le seteo el adapter creado al recycler view
        recyclerView.setAdapter(adapter);
    }

    private void recibirCategoriaACargar() {//Eugenio Recibo bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.categoriaACargar= (CategoriaRecycleViewFragment) bundle.getSerializable(CLAVE_CATEGORIA);

        }
    }

    public void cargarPelicula() {
        if (peliculaController.isHayPaginas()) {
            isLoading = true;
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
            peliculaController.getPeliculaLista(new ResultListener<List<Pelicula>>() {
                @Override
                public void finish(List<Pelicula> resultado) {
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    isLoading = false;
                    adapter.addListaPelicula(resultado);
                }
            });
        }
    }
    public void actualizarPelicula(Pelicula pelicula){
        int posicion = categoriaACargar.getPeliculas().indexOf(pelicula);
        if(posicion!=-1){
            categoriaACargar.getPeliculas().get(posicion).setEstaFavorito(pelicula.getEstaFavorito());
            adapter.actualizarPelicula(pelicula);
        }
    }
//SETERS AND GETTERS

    public void setFragmentPaginable(Boolean esPaginableBoolean){
        fragmentPaginable=esPaginableBoolean;
    }

    ArrayList<Pelicula> getListadoPeliculas(){
        return categoriaACargar.getPeliculas();
    }
    //Imlementación de la interfaz que se comunica con la celda
    @Override
    public void notificarPeliculaClickeado(int posicionPeliculaClickpelicula) {
        //esto se va a llamar cuando se clickee una celda en el adapter
        //Esto hace de pasa mano, y tiene que notificarle al activity el pelicula que llegó.
        //aca estoy en el metodo que me obligo a implementar LA INTERFAZ DEL ADAPTER!
        notificadorPelicula.notificar(this.categoriaACargar,posicionPeliculaClickpelicula);

    }
    @Override
    public void solicitudDeActualizacionAdapters(Pelicula pelicula) {

        notificadorPelicula.solicituddeActualizarDatosFragmentsPelicula(pelicula);
        adapter.notifyDataSetChanged();

    }



    public void solicitarPagina(final ResultListener<List<Pelicula>> escuchadorTodasCategorias) {
        //if (peliculaController.isHayPaginas()) {
            isLoading = true;
            peliculaController.dameUnaPagina(new ResultListener<List<Pelicula>>() {
                @Override
                public void finish(List<Pelicula> resultado) {
                    isLoading = false;
                    adapter.addListaPelicula(resultado);
                    nuevaPeliculas=resultado;
                    escuchadorTodasCategorias.finish(resultado);
                }
            });
       // }
    }



    //INTERFAZ QUE COMUNICA FRAGMENT CON ACTIVITY. EL ACTIVITY ES QUIEN IMPLEMENTA ESTA INTERFAZ
    public interface NotificadorPelicula {
        public void notificar(Categoria categoria, int posicion);
        public void abrirGrilla(Categoria categoria);
        public void solicituddeActualizarDatosFragmentsPelicula(Pelicula pelicula);

    }

}
