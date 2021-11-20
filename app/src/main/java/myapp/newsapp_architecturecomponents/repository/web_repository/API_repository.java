package myapp.newsapp_architecturecomponents.repository.web_repository;

import androidx.lifecycle.MutableLiveData;
import myapp.newsapp_architecturecomponents.repository.BusinessNews;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class Api_repository {
    private MutableLiveData<List<BusinessNews>> articles;
    private api_service mRestful_API;
    private Call<List<BusinessNews>> mRequestCall;

    public Api_repository() {
        mRestful_API = RetrofitClient.getRetrofitInstance().create(api_service.class);
        mRequestCall = mRestful_API.get_article_content();
        articles = new MutableLiveData<>();
    }

    public MutableLiveData<List<BusinessNews>> getArticleContent() {
	//The clone method is used to make multiple network calls instead of just a one-time network request.    
        mRequestCall.clone().enqueue(new Callback<List<BusinessNews>>() {
            @Override
            public void onResponse(Call<List<BusinessNews>> call, Response<List<BusinessNews>> response) {
                if(response.isSuccessful()) {
		    articles.setValue(null);
                    articles.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<BusinessNews>> call, Throwable t) {
                articles.setValue(null);
            }
        });
        return articles;
    }

    public MutableLiveData<List<BusinessNews>> getArticles() {
        return articles;
    }
}
