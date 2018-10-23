package com.example.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.model.pojo.Categoria;
import com.example.model.pojo.CategoriaRecycleViewFragment;
import com.example.model.pojo.Pelicula;
import com.example.recyclerviewbase.R;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewCategoriasBuscar extends Fragment {


    public ListViewCategoriasBuscar() {
        // Required empty public constructor
    }

    private NotificadorSearch notificadorSearch;
    private ListView lv;
    private ArrayAdapter<String> adapter;







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_view_categorias_buscar, container, false);;


         lv = (ListView) view.findViewById(R.id.listViewCategorias);
        ArrayList<String> listadoCategorias =  new ArrayList<>();
        listadoCategorias.addAll(Arrays.asList(getResources().getStringArray(R.array.stringArrayCategorias)));

        adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                listadoCategorias);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View itemView, int itemPosition, long itemId)
            {

                String categoriaSeleccionada= adapter.getItem(itemPosition).toString();
                notificadorSearch.abrirCategoria(categoriaSeleccionada);

            }
        });


        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            notificadorSearch = (NotificadorSearch) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() + " implementar NotificarPelicula");
        }
    }
    //INTERFAZ QUE COMUNICA FRAGMENT CON ACTIVITY. EL ACTIVITY ES QUIEN IMPLEMENTA ESTA INTERFAZ
    public interface NotificadorSearch {
        public void abrirCategoria(String nombreCategoria);

    }
}
