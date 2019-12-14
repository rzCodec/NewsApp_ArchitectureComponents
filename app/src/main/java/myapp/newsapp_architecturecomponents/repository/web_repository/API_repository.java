package myapp.newsapp_architecturecomponents.repository.web_repository;

import androidx.lifecycle.MutableLiveData;
import myapp.newsapp_architecturecomponents.repository.BusinessNews;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class API_repository {
    private api_service mRestful_API;
    private Call<List<BusinessNews>> mRequestCall;

    public API_repository() {
        mRestful_API = RetrofitClient.getRetrofitInstance().create(api_service.class);
        mRequestCall = mRestful_API.get_article_content();
    }

    /**
     * @return We use MutableLiveData because we want to access the live data by assigning it the response we get
     * from the RESTful API
     * Mutable means "to change", LiveData by default has no modifiers that allow it to be changed but using the
     * keyword Mutable will do so.
     */
    public MutableLiveData<List<BusinessNews>> getArticleContent() {
        final MutableLiveData<List<BusinessNews>> articles = new MutableLiveData<>();

        mRequestCall.enqueue(new Callback<List<BusinessNews>>() {
            @Override
            public void onResponse(Call<List<BusinessNews>> call, Response<List<BusinessNews>> response) {
                if(response.isSuccessful()) {
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
}
