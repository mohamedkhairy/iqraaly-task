package khairy.com.iqraalytask.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import khairy.com.iqraalytask.R;
import khairy.com.iqraalytask.databinding.ActivityPlayerBinding;
import khairy.com.iqraalytask.viewModel.PlayerViewModel;

public class PlayerActivity extends AppCompatActivity {


    public static final String EXTRA_Book = "EXTRA_PEOPLE";
    private String Url;
    private long videoState;
    private long stopstate;
    private Bundle bundlesavedState = null;
    private SimpleExoPlayer player;
    PlayerView playerView;
    PlayerViewModel playerViewModel;
    ActivityPlayerBinding binding;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_player);
        playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        binding.setPlayerViewModel(playerViewModel);
        binding.setLifecycleOwner(this);
        binding.setPlayerActivity(this);

        playerViewModel.getpausedPosition(stopstate)
                .observe(this, aLong -> { });

            getIndex();
        initial_state(Url);

    }

    public void getIndex() {
        index = getIntent().getIntExtra(EXTRA_Book, 0);
        Url = playerViewModel.mutableEpisodes.getValue().get(index).getFile();
        binding.setIndex(index);
    }


    public void initial_state(String link) {

        playerView = binding.audioView;
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory TrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(TrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        playerView.setShutterBackgroundColor(Color.TRANSPARENT);

        playerView.setPlayer(player);

        Uri mp4VideoUri = Uri.parse(link);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                this,
                Util.getUserAgent(this, getString(R.string.app_name)));
        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mp4VideoUri);
        player.prepare(mediaSource);


        if (playerViewModel.pausedPosition.getValue() != null) {
            player.seekTo(playerViewModel.pausedPosition.getValue());
            stopstate = 0;
        }
        player.setPlayWhenReady(true);

    }

    @Override
    public void onStop() {
        if (!Url.isEmpty()) {
            playerView.setPlayer(null);
            player.release();
        }
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopstate = player.getCurrentPosition();
        playerViewModel.pausedPosition.setValue(stopstate);
    }



//TODO this method should play a list

//    public void playEpisodesList() {
//
//        MediaSource[] list = new MediaSource[episodeslist.size()];
//
//        for (int i = 0; i < episodes.size(); i++) {
//            mediaSources[i] = buildMediaSource(Uri.parse(episodes.get(i).getfile()));
//        }
//
//        MediaSource mediaSource = list.length == 1 ? list[0] new ConcatenatingMediaSource(list);
//        player.seekTo(position, 0);
//    }

}
