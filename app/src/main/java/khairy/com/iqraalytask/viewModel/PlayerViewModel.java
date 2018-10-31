package khairy.com.iqraalytask.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import khairy.com.iqraalytask.model.Episode;
import khairy.com.iqraalytask.view.MainActivity;
import khairy.com.iqraalytask.view.listner.EpisodeListner;

public class PlayerViewModel extends ViewModel implements EpisodeListner {

    public MutableLiveData<List<Episode>> mutableEpisodes ;
    public MutableLiveData<String> writer = new MutableLiveData<>();

    public MutableLiveData<Long> pausedPosition;

    public LiveData<Long> getpausedPosition(Long position) {
        if (pausedPosition == null) {
            pausedPosition = new MutableLiveData<>();
            pausedPosition.setValue(position);
        }

        return pausedPosition;
    }
    int index;
    String coverUrl;

    public PlayerViewModel() {
        BookViewModel.setPlayerData(this);
        MainActivity.getImageLink(this);
    }

    public void OnbackClicked(Context context){
        Intent intent = new Intent(context , MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public String getBackgrounImageUrl() {
        return coverUrl;
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadCoverImage(ImageView view, String imageUrl) {
        Context context = view.getContext();
        Glide.with(context).load(imageUrl).into(view);
    }

    @Override
    public void getEpisodes(List<Episode> episodesData) {
        mutableEpisodes = new MutableLiveData<>();
        mutableEpisodes.setValue(episodesData);
    }

    @Override
    public void ImageLink(String Link) {
        this.coverUrl = Link;
    }

    public String getTitle(int index){
        this.index = index;
       return mutableEpisodes.getValue().get(index).getTitle();
    }

}
