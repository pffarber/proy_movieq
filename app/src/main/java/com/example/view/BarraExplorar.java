package com.example.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.recyclerviewbase.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BarraExplorar extends Fragment {


private NotificadorBarraExplorar notificadorBarraExplorar;
private ImageView iconoFavorito,iconoHome,iconoBuscar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_barra_explorar, container, false);

        iconoFavorito = (ImageView) view.findViewById(R.id.boton_barra_favorito_id);
        iconoHome = (ImageView) view.findViewById(R.id.boton_home);

        iconoFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GUARDO EN UNA VARIABLE Drawable la imagen
                //ENVIARLE EL MENSAJE AL ACTIVITY
                notificadorBarraExplorar.abrirFavoritos();
            }
        });

        iconoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GUARDO EN UNA VARIABLE Drawable la imagen
                //ENVIARLE EL MENSAJE AL ACTIVITY
                notificadorBarraExplorar.abrirHome();
            }
        });


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        notificadorBarraExplorar = (BarraExplorar.NotificadorBarraExplorar) context;

    }


    //INTERFAZ QUE COMUNICA FRAGMENT CON ACTIVITY. EL ACTIVITY ES QUIEN IMPLEMENTA ESTA INTERFAZ
    public interface NotificadorBarraExplorar {

        public void abrirFavoritos();

        public void abrirHome();
    }
}
