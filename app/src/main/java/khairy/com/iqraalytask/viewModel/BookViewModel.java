package khairy.com.iqraalytask.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import khairy.com.iqraalytask.model.Book;
import khairy.com.iqraalytask.model.Episode;
import khairy.com.iqraalytask.model.Example;
import khairy.com.iqraalytask.view.PlayerActivity;
import khairy.com.iqraalytask.view.listner.DataChangeListner;
import khairy.com.iqraalytask.view.listner.EpisodeListner;
import khairy.com.iqraalytask.viewModel.network.NetworkUtil;

public class BookViewModel extends ViewModel implements DataChangeListner {

    public MutableLiveData<String> writer = new MutableLiveData<>();
    public MutableLiveData<String> narratorName = new MutableLiveData<>();
    public MutableLiveData<String> publisher = new MutableLiveData<>();
    public MutableLiveData<String> about = new MutableLiveData<>();
    public MutableLiveData<String> cover = new MutableLiveData<>();
    public MutableLiveData<Book> bookMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Episode>> mutableEpisodes ;
    public String url ;
    static Example example;
    Context context;



    public BookViewModel(){
        NetworkUtil.getMainPageData(this);
    }

    public static void setPlayerData(final EpisodeListner dataListner){
        dataListner.getEpisodes(example.getData().getBook().getEpisodes());
    }


        public LiveData<List<Episode>> getEpisodeList() {
        if (mutableEpisodes == null) {
            mutableEpisodes = new MutableLiveData<>();
            loadEpisodes();
        }
        return mutableEpisodes;
    }

    public void loadEpisodes(){
        Handler myHandler = new Handler();
        myHandler.postDelayed(() -> {
            List<Episode> episodesList = new ArrayList<>();
            episodesList = bookMutableLiveData.getValue().getEpisodes();
            mutableEpisodes.setValue(episodesList);
        }, 5000);

    }


    public void OnCoverClicked(Context con){
        Intent intent = new Intent(con , PlayerActivity.class);
        intent.putExtra(PlayerActivity.EXTRA_Book , 0);
        con.startActivity(intent);
    }

    @Override
    public void onChange(Object change) {
        this.example = (Example) change;
        bookMutableLiveData.setValue(example.getData().getBook());
        writer.setValue(bookMutableLiveData.getValue().getAuthor());
        narratorName.setValue(bookMutableLiveData.getValue().getNarratorName());
        publisher.setValue((bookMutableLiveData.getValue().getPublisherName()));
        about.setValue((bookMutableLiveData.getValue().getAbout()));
        cover.setValue(bookMutableLiveData.getValue().getCover());

    }


    public String getBookTitle(int index){
        url = mutableEpisodes.getValue().get(index).getImage();
        return mutableEpisodes.getValue().get(index).getTitle();
    }

    public String getImageUrl() {
        return url;
    }



    public String getCouverImageUrl() {
        String coverUrl = cover.getValue();
        return coverUrl;
    }


}
