package com.example.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.example.model.pojo.Pelicula;

import java.util.ArrayList;
import java.util.List;

public class AdapterDetalleCategoriaPageView extends FragmentStatePagerAdapter{

    //TODO: Paso 3 ViewPager. crear el adapter, que extiende de FragmentStatePagerAdapter

    //TODO: almaceno la lista de Fragments que el ViewPager mostrará
    private ArrayList<DetallePeliculaFragment> listadetallePeliculas;

    //TODO: este constructor necesita un FragmentManager, que le pasamos desde quien lo invoca con un getSupportFragmentManager(),
    // y recibe también la lista de Fragments

    public AdapterDetalleCategoriaPageView(FragmentManager fm, ArrayList<DetallePeliculaFragment> listadetallePeliculas) {
        super(fm);
        this.listadetallePeliculas = listadetallePeliculas;
    }

    @Override
    public Fragment getItem(int position) {
        return listadetallePeliculas.get(position);
    }

    @Override
    public int getCount() {
        return listadetallePeliculas.size();
    }

    //TODO: Paso 3 de TabLayout agrego este método para que el adapter pueda obtener los títulos de cada Fragment
    @Override
    public CharSequence getPageTitle(int position) {
        DetallePeliculaFragment detallePeliculaFragment = listadetallePeliculas.get(position);
        return detallePeliculaFragment.toString();

    }

    public void addPaginaPelicula(ArrayList<DetallePeliculaFragment> paginadetallelePeliculas) {
        this.listadetallePeliculas.addAll(paginadetallelePeliculas);
        notifyDataSetChanged();
    }
}

