package khairy.com.iqraalytask.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import khairy.com.iqraalytask.R;
import khairy.com.iqraalytask.databinding.ActivityMainBinding;
import khairy.com.iqraalytask.databinding.MainContentBinding;
import khairy.com.iqraalytask.view.adapter.EpisodeRecyclerView;
import khairy.com.iqraalytask.view.listner.EpisodeListner;
import khairy.com.iqraalytask.viewModel.BookViewModel;

public class MainActivity extends AppCompatActivity implements EpisodeRecyclerView.card_onclickHandler {


    RecyclerView recyclerView;
    EpisodeRecyclerView episodeAdapter;
    BookViewModel bookViewModel;
    ActivityMainBinding binding;
    public static String ImageLink ="https://mobile.iqraaly.com/users/1/shows/1203_1537895377.jpg" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        binding.setBookViewModel(bookViewModel);
        binding.setLifecycleOwner(this);
        initInstancesDrawer();


        bookViewModel.getEpisodeList().observe(this, mutableEpisodes -> {
            ExpandableTextView expTv1 = (ExpandableTextView) findViewById(R.id.expand_text_view);
            expTv1.setText(bookViewModel.about.getValue());
            set_episodeView();
        });

        binding.setMainActivity(this);

    }


    public void set_episodeView() {


        recyclerView = binding.content.recyclerView;
        LinearLayoutManager linearLayout = new LinearLayoutManager(getBaseContext(), LinearLayout.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayout);
        episodeAdapter = new EpisodeRecyclerView(this, bookViewModel, 3);
        recyclerView.setAdapter(episodeAdapter);
    }

    @Override
    public void card_handler(int position) {
        ImageLink = bookViewModel.mutableEpisodes.getValue().get(position).getImage();
        Intent intent = new Intent(MainActivity.this , PlayerActivity.class);
        intent.putExtra(PlayerActivity.EXTRA_Book , position);
        startActivity(intent);
    }
    public static void getImageLink(final EpisodeListner dataListner){
        dataListner.ImageLink(ImageLink);
    }


    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {

        Handler myHandler = new Handler();
        myHandler.postDelayed(() -> {
            Context context = view.getContext();
            Glide.with(context).load(imageUrl).into(view);
        }, 2000);
    }


    private void initInstancesDrawer() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }

}
