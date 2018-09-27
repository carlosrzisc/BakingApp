package com.crodr.bakingapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.crodr.bakingapp.R;
import com.crodr.bakingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepFragment extends Fragment {
    private static final String ARG_PARAM_STEP = "param_step";
    private static final String PLAYER_POSITION = "player_position";
    private static final String PLAYER_PLAY_PAUSE = "player_pause";
    private Step paramStep;

    private long position = 0;

    @BindView(R.id.exoplayer)
    SimpleExoPlayerView playerView;

    @BindView(R.id.stepImageView)
    ImageView stepImageView;

    @BindView(R.id.stepDescriptionTextView)
    TextView stepDescriptionTextView;

    private SimpleExoPlayer exoPlayer;
    private boolean isPlayerReady = true;

    public StepFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param step Parameter Step.
     * @return A new instance of fragment RecipeFragment.
     */
    public static StepFragment newInstance(Step step) {
        StepFragment fragment = new StepFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM_STEP, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            paramStep = getArguments().getParcelable(ARG_PARAM_STEP);
        }
        if (savedInstanceState != null && savedInstanceState.containsKey(PLAYER_POSITION)) {
            position = savedInstanceState.getLong(PLAYER_POSITION);
            isPlayerReady = savedInstanceState.getBoolean(PLAYER_PLAY_PAUSE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);

        stepDescriptionTextView.setText(paramStep.getDescription());

        if (!paramStep.getThumbnailURL().isEmpty()) {
            Picasso.get().load(paramStep.getThumbnailURL()).into(stepImageView);
            stepImageView.setVisibility(View.VISIBLE);
        }
        if (!paramStep.getVideoURL().isEmpty()) {
            initVideoPlayer(Uri.parse(paramStep.getVideoURL()));
        } else {
            playerView.setVisibility(View.GONE);
        }
        return rootView;
    }

    private void initVideoPlayer(Uri videoUri) {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this.getContext(), new DefaultTrackSelector(), new DefaultLoadControl());
            playerView.setPlayer(exoPlayer);

            MediaSource mediaSource = new ExtractorMediaSource.Factory(
                    new DefaultHttpDataSourceFactory("baking-app")).createMediaSource(videoUri);
            exoPlayer.prepare(mediaSource);

            if (position != 0) exoPlayer.seekTo(position);

            exoPlayer.setPlayWhenReady(isPlayerReady);
            playerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (exoPlayer != null) {
            outState.putLong(PLAYER_POSITION, exoPlayer.getCurrentPosition());
            outState.putBoolean(PLAYER_PLAY_PAUSE, exoPlayer.getPlayWhenReady());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (exoPlayer != null) {
            releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || exoPlayer == null) {
            initVideoPlayer(Uri.parse(paramStep.getVideoURL()));
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}
