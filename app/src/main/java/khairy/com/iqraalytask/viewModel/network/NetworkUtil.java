package khairy.com.iqraalytask.viewModel.network;

import android.util.Log;

import khairy.com.iqraalytask.model.Example;
import khairy.com.iqraalytask.view.listner.DataChangeListner;
import khairy.com.iqraalytask.viewModel.BookViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class NetworkUtil {

    private static final String URL = "https://staging.app.iqraaly.com/";
    private static Retrofit retrofit;
//    private Book selectedBook = new Example().getData().getBook();
    public static BookViewModel bookViewModel;

    public static void getMainPageData(final DataChangeListner<Example> dataListner){
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dataFetcher apiFetcher = retrofit.create(dataFetcher.class);
        Call<Example> call = apiFetcher.getCategories();
        call.enqueue(new Callback<Example>() {

            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
//                Example ex =  response.body();
//                Log.d("xxxx" , ex.getData().getBook().getName());
//
//                bookViewModel = new BookViewModel(ex);
                if (response != null && response.body() != null) {
                    dataListner.onChange(response.body());
                }

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.d("MAIN" , t.getMessage() + "\n" + t.getLocalizedMessage());
                t.getStackTrace();
            }
        });
    }

//    public static void setData(){
//        bookViewModel.writer.setValue(selectedBook.getAuthor());
//        bookViewModel.about.setValue(selectedBook.getAbout());
//        bookViewModel.narratorName.setValue(selectedBook.getNarratorName());
//        bookViewModel.publisher.setValue(selectedBook.getPublisherName());
//
//    }
}

interface dataFetcher {

    @GET("api/show/1203" )
    Call<Example> getCategories();


}