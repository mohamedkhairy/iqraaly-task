package khairy.com.iqraalytask.view.listner;

import java.util.List;

import khairy.com.iqraalytask.model.Episode;

public interface EpisodeListner {
    void getEpisodes(List<Episode> episodesData);

    void ImageLink(String Link);
}
