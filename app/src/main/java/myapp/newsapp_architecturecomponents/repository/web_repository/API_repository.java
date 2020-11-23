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

    /**
     * @return We use MutableLiveData because we want to access the live data by assigning it the response we get
     * from the RESTful API
     * Mutable means "to change", LiveData by default has no modifiers that allow it to be changed but using the
     * keyword Mutable will do so.
     */
    public MutableLiveData<List<BusinessNews>> getArticleContent() {
		//Use the clone method to make multiple calls using the same parameters / method signature
		//Because most Retrofit calls can only be used once
		//The clone() method allows a network call to be used multiple times.
        mRequestCall.clone().enqueue(new Callback<List<BusinessNews>>() {
            @Override
            public void onResponse(Call<List<BusinessNews>> call, Response<List<BusinessNews>> response) {
                if(response.isSuccessful()) {
					articles.setValue(null); //To clean any data that might already exist from a prior network call
                    articles.setValue(response.body());
					//Think of it as passing the response body "out of this async code block" and treat it as synchronous code
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
