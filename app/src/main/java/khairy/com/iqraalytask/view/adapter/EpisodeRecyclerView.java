package khairy.com.iqraalytask.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import khairy.com.iqraalytask.R;
import khairy.com.iqraalytask.databinding.EpisodeCardBinding;
import khairy.com.iqraalytask.viewModel.BookViewModel;


public class EpisodeRecyclerView extends RecyclerView.Adapter<EpisodeRecyclerView.cardHolder>{

    private int size;
//    private Context context;
    final private card_onclickHandler CardClickHandler ;
BookViewModel bookViewModel;

    public EpisodeRecyclerView(card_onclickHandler chandler , BookViewModel viewModel, int episodes) {
        this.size = episodes;
        this.CardClickHandler = chandler;
        this.bookViewModel  = viewModel;
    }




    public interface card_onclickHandler{
        void card_handler(int position);
    }

    @NonNull
    @Override
    public cardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int mainPageLayout = R.layout.episode_card;
        LayoutInflater inflater = LayoutInflater.from(context);
        EpisodeCardBinding episodeCardBinding = DataBindingUtil.inflate(inflater , mainPageLayout , parent , false);
        cardHolder holder = new cardHolder(episodeCardBinding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull cardHolder holder, int position) {
        holder.binding.setPosition(position);
        holder.binding.setEpisode(bookViewModel);
        holder.binding.bookcard.setOnClickListener(view -> {
            if (CardClickHandler != null) {
                CardClickHandler.card_handler(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.size;
    }

    public class cardHolder extends RecyclerView.ViewHolder{


        private final EpisodeCardBinding binding;

        public cardHolder(EpisodeCardBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }


//        @Override
//        public void onClick(View view) {
//            int position = getAdapterPosition();
//            CardClickHandler.card_handler(position);
//        }
    }
}