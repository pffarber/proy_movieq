package com.example.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.controller.PeliculaController;
import com.example.model.pojo.Pelicula;
import com.example.model.pojo.Trailer;
import com.example.recyclerviewbase.R;
import com.example.utils.ResultListener;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

import static com.example.view.YouTubeFragment.KEY_TRAILER;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetallePeliculaFragment extends Fragment  {

    public static final String PELICULA_KEY = "Pelicula_key";
    public static final String NOMBRE_CATEGORIA_KEY = "Categoria";
    private PeliculaController peliculaController;
    public static int IDPeli;
    private String keyYouTube;
    private String movieIDString;
    private Integer movieIDInt;
    private NotificadorVerTrailer notificadorVerTrailer;

    public DetallePeliculaFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            notificadorVerTrailer = (DetallePeliculaFragment.NotificadorVerTrailer) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() + " implementar NotificarVerTrailer");
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_pelicula, container, false);
        ImageView IVimagenPelicula =  (ImageView) view.findViewById(R.id.imagen_pelicula);
        ImageView IVFondoPelicula = (ImageView) view.findViewById(R.id.fondo_imagen_pelicula);
        TextView TVtituloPelicula = (TextView) view.findViewById(R.id.titulo_pelicula);
        TextView TVtituloOriginalPelicula = (TextView) view.findViewById(R.id.titulo_original_pelicula);
        TextView TVanioLanzamiento = (TextView) view.findViewById(R.id.anio_lanzamiento_id);
        TextView TVGeneroPelicula = (TextView) view.findViewById(R.id.genero_pelicula);
        TextView TVIdPelicula = (TextView) view.findViewById(R.id.id_pelicula);
        TextView TVVotosPromedio = (TextView) view.findViewById(R.id.votosPromedio_pelicula);
        RatingBar RatingBarPelicula = (RatingBar)view.findViewById(R.id.rating_bar);
        TextView TVdescripcionPelicula = (TextView) view.findViewById(R.id.descripcion_pelicula);
        TextView TVnombreCategoria = (TextView) view.findViewById(R.id.titulo_categoria);
        Button BtTrailer = (Button)view.findViewById(R.id.button_trailer);


        //LinearLayout linearLayout = (LinearLayout) view.findViewById() ;

        final Bundle bundle = getArguments();
        final Pelicula pelicula = (Pelicula) bundle.getSerializable(PELICULA_KEY);
        String nombreCategoria = (String) bundle.getString(NOMBRE_CATEGORIA_KEY);
        TVtituloPelicula.setText(pelicula.getTitulo());
        TVtituloOriginalPelicula.setText(pelicula.getOriginal_title());
        //TVIdPelicula.setText(String.valueOf(pelicula.getIdPelicula()));

        TVanioLanzamiento.setText(pelicula.getReleaseDate().split("-")[0]);
        TVVotosPromedio.setText(String.valueOf(pelicula.getVotosPromedio()));
        RatingBarPelicula.setVisibility(view.VISIBLE);
        RatingBarPelicula.setRating(pelicula.getVotosPromedio()/2);
        TVdescripcionPelicula.setText(pelicula.getOverview());
        TVnombreCategoria.setText(nombreCategoria);
        Glide.with(getContext()).load(pelicula.getPoster()).into(IVimagenPelicula);
        Glide.with(getContext()).load(pelicula.getFondo()).into(IVFondoPelicula);

        //
        movieIDString = String.valueOf(pelicula.getIdPelicula());

        movieIDInt = pelicula.getIdPelicula();

        peliculaController = new PeliculaController();
        peliculaController.getTrailersPelicula(movieIDInt, new ResultListener<List<Trailer>>(){

                @Override
                public void finish(List<Trailer> resultado) {
                    if (resultado != null){
                        keyYouTube = resultado.get(0).getKey();
                    pelicula.setTrailer(resultado.get(0));
                    //YouTubeFragment youTubeFragment = new YouTubeFragment();
                    //FragmentManager fragmentManager = getChildFragmentManager();
                    //fragmentManager.beginTransaction().replace(R.id.container_fragment_yt, youTubeFragment).commit();
                    //Bundle bundleyt = new Bundle();
                    //bundleyt.putString(KEY_TRAILER,keyYouTube);
                    //youTubeFragment.setArguments(bundleyt);
                }
                    }

        });
        //peliculaController.get

        BtTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificadorVerTrailer.notificarVerTrailer(keyYouTube);

            }
        });
/*
        YouTubeFragment youTubeFragment = new YouTubeFragment();
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container_fragment_yt, youTubeFragment).commit();
       // keyYouTube = pelicula.getTrailerList().get(0).getKey();
        Bundle bundleyt = new Bundle();
        bundleyt.putString(KEY_TRAILER,"SUXWAEX2jlg");
     //   bundleyt.putString(KEY_TRAILER,keyYouTube);
*/
        return view;
    }





    public static DetallePeliculaFragment DameUnFragmentDetallePelicula (Pelicula pelicula,String nombreCategoria){
        //Esta es la fábrica de fragments. Se invocará sin instanciar la clase por ser static

        //Genero un nuevo fragment concreto. Que este método retornará.
        DetallePeliculaFragment detallePeliculaFragment = new DetallePeliculaFragment();

        //Genero un bundle para poblar el fragment concreto con sus datos
        Bundle bundle = new Bundle();
        bundle.putSerializable(PELICULA_KEY, pelicula);
        bundle.putString(NOMBRE_CATEGORIA_KEY,nombreCategoria);

        detallePeliculaFragment.setArguments(bundle);
        //Devuelvo el fragment ya con título y con su bundle
        return detallePeliculaFragment;
    }

    public interface NotificadorVerTrailer {
        void notificarVerTrailer(String key);

    }





}
