package com.example.view;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.model.pojo.Trailer;
import com.example.recyclerviewbase.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * A simple {@link Fragment} subclass.
 */



public class YouTubeFragment extends Fragment implements YouTubePlayer.OnInitializedListener {
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private NotificadorTrailer notificadorTrailer;

    public static final String KEY_TRAILER = "keyYouTube";


    public YouTubeFragment() {
        // Required empty public constructor
    }

    FragmentActivity mContext;
    private YouTubePlayer YPlayer;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            notificadorTrailer = (NotificadorTrailer) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(
                    context.toString() + " implementar NotificarTrailer");
        }

    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //notificadorTrailer = (NotificadorTrailer) activity;

        if (activity instanceof FragmentActivity) {
            mContext = (FragmentActivity) activity;
        }*/
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_you_tube, container, false);
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        Bundle bundle = getArguments();
        final String keyTrailer = bundle.getString(KEY_TRAILER);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_player, youTubePlayerFragment).commit();




        youTubePlayerFragment.initialize(Config.YOUTUBE_API_KEY,   new YouTubePlayer.OnInitializedListener() {


            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    YPlayer = youTubePlayer;
                    //YPlayer.cueVideo("vn9mMeWcgoM");
                    YPlayer.cueVideo(keyTrailer);

                    YPlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
                        @Override
                        public void onPlaying() {
                           Toast.makeText(getContext(), "onPlaying", Toast.LENGTH_SHORT).show();
                            //notificadorTrailer.notificarTrailer(keyTrailer);
                        }

                        @Override
                        public void onPaused() {
                            //Toast.makeText(getContext(), "onPaused", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onStopped() {
                           Toast.makeText(getContext(), "onStopped", Toast.LENGTH_SHORT).show();
                            //llega a los tres seg del play

                        }

                        @Override
                        public void onBuffering(boolean b) {
                           // Toast.makeText(getContext(), "onbuff", Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onSeekTo(int i) {
                            Toast.makeText(getContext(), "onSeekto", Toast.LENGTH_SHORT).show();


                        }
                    });
                    YPlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                        @Override
                        public void onLoading() {
                            Toast.makeText(getContext(), "onloading", Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onLoaded(String s) {
                            Toast.makeText(getContext(), "onLoaded", Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onAdStarted() {

                        }

                        @Override
                        public void onVideoStarted() {
                            //Toast.makeText(getContext(), "onVideoStarted", Toast.LENGTH_SHORT).show();
//no llega

                           // YPlayer.setFullscreen(true);

                        }

                        @Override
                        public void onVideoEnded() {

                        }

                        @Override
                        public void onError(YouTubePlayer.ErrorReason errorReason) {
                            Toast.makeText(getContext(), "onError", Toast.LENGTH_SHORT).show();


                        }
                    });
                   // YPlayer.setFullscreen(true);

/*
                 YPlayer.loadVideo("ZJDMWVZta3M");
*/


                    /*YPlayer.play();*/
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            String errorMessage =  youTubeInitializationResult.toString();
            Toast.makeText(getActivity(),errorMessage, Toast.LENGTH_LONG).show();
            Log.d("errorMessage:",errorMessage);
            }


        });






        return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();

    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    public interface NotificadorTrailer {
        void notificarTrailer(String key);

    }




}


