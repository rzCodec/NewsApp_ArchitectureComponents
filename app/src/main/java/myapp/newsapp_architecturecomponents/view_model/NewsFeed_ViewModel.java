package myapp.newsapp_architecturecomponents.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import myapp.newsapp_architecturecomponents.repository.BusinessNews;
import myapp.newsapp_architecturecomponents.repository.web_repository.Api_repository;

import java.util.List;

/**
 * Class for the UI that the user views their news in real time
 */
public class NewsFeed_ViewModel extends ViewModel {
    private Api_repository api_repo = new Api_repository();
	private LiveData<List<BusinessNews>> news;
   
	//private MutableLiveData<List<BusinessNews>> articles;

	
    public NewsFeed_ViewModel() { 
		//articles = new MutableLiveData<>();
        //news = api_repository.getArticleContent();
    }

	/*
    private MutableLiveData<List<BusinessNews>> articles;
    private api_service mRestful_API;
    private Call<List<BusinessNews>> mRequestCall;

	
    public NewsFeed_ViewModel() {
        mRestful_API = RetrofitClient.getRetrofitInstance().create(api_service.class);
        mRequestCall = mRestful_API.get_article_content();
        articles = new MutableLiveData<>();
    }*/

    /**
     * @return We use MutableLiveData because we want to access the live data by assigning it the response we get
     * from the RESTful API
     * Mutable means "to change", LiveData by default has no modifiers that allow it to be changed but using the
     * keyword Mutable will do so.
     
    public void getArticleContent() {
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
        //return articles;
    }
	*/
	public void getNews(){
		api_repo.getArticleContent();
	}

    public LiveData<List<BusinessNews>> getBusinessNewsItems() {
        //return news;
        return api_repo.getArticles();
    }
}
