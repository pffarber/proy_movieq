package com.example.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.controller.PeliculaController;
import com.example.model.pojo.Categoria;
import com.example.model.pojo.CategoriaRecycleViewFragment;
import com.example.model.pojo.Pelicula;
import com.example.model.pojo.Trailer;
import com.example.recyclerviewbase.R;
import com.example.utils.ResultListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSearch extends Fragment {



    public FragmentSearch() {
        // Required empty public constructor
    }


    private  ImageView imagenSearch;
    private EditText editTextSearch;
    private FrameLayout contenendorFragments;
    private ListViewCategoriasBuscar fragmentCategoriasABuscar;
    private PeliculasFragment peliculasFragment;
    private PeliculaController peliculaController;
    private List<Pelicula> resultadoBusqueda;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_search, container, false);

        peliculaController = new PeliculaController();
        resultadoBusqueda = new ArrayList<>();

        imagenSearch= (ImageView) view.findViewById(R.id.search_imagen);
        imagenSearch.setImageResource(R.drawable.ic_search_black_24dp);

        editTextSearch = (EditText) view.findViewById(R.id.edit_text_search) ;
        editTextSearch.setHint("Nombre Pelicula");


        fragmentCategoriasABuscar = new ListViewCategoriasBuscar();

        cargarFragment(fragmentCategoriasABuscar,R.id.contenedor_search);


        editTextSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String contenidoEditText = editTextSearch.getText().toString();
                if (contenidoEditText.isEmpty()) {
                    cargarFragment(fragmentCategoriasABuscar, R.id.contenedor_search);
                } else {
                    peliculaController.searchPelicula(contenidoEditText, new ResultListener<List<Pelicula>>() {
                        @Override
                        public void finish(List<Pelicula> resultado) {

                            abrirResultadoSearch((ArrayList<Pelicula>) resultado);


                        }
                    });
                }
            }
        });

        return view;
    }



    public void abrirResultadoSearch(ArrayList<Pelicula> resultadoBusqueda){
        //CARGO EL BUNDLE PARA Enviar al fragment
        CategoriaRecycleViewFragment categoriaAbrir = new CategoriaRecycleViewFragment
                (resultadoBusqueda,"Resultado",true);


        PeliculasFragment peliculasFragment = new PeliculasFragment();
        peliculasFragment.setFragmentPaginable(false);

        Bundle unBundle = new Bundle();
        unBundle.putSerializable(PeliculasFragment.CLAVE_CATEGORIA, categoriaAbrir);
        peliculasFragment.setArguments(unBundle);
        cargarFragment(peliculasFragment,R.id.contenedor_search);

    }



    void cargarFragment(Fragment fragment, int idContenedor){
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(idContenedor,fragment).commit();
    }

}
