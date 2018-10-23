package com.example.view;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recyclerviewbase.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment  {
private static final int RECOVERY_DIALOG_REQUEST = 1;
public static final String KEY_TRAILER = "keyTrailer";
private String trailer;


public BlankFragment() {
        // Required empty public constructor
        }

        FragmentActivity mContext;
private YouTubePlayer YPlayer;

        @Override
        public void onAttach(Activity activity) {
                super.onAttach(activity);

                if (activity instanceof FragmentActivity) {
                        mContext = (FragmentActivity) activity;
                }
        }



        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.fragment_blank, container, false);
                Bundle bundle = getArguments();
                final String keyTrailer = bundle.getString(KEY_TRAILER);

                YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
                FragmentTransaction transaction =getChildFragmentManager().beginTransaction();
                transaction.add(R.id.youtube_player, youTubePlayerFragment).commit();
                youTubePlayerFragment.initialize(Config.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                                if (!b) {
                                        YPlayer = youTubePlayer;
                                        YPlayer.setFullscreen(false);


                                        YPlayer.loadVideo( keyTrailer );

                                       // YPlayer.cueVideo("SUXWAEX2jlg");
                                        YPlayer.play();
                                }
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                        }
                });
                return view;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
                super.onViewCreated(view, savedInstanceState);
                mContext = getActivity();

        }






}