package myapp.newsapp_architecturecomponents.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;
import myapp.newsapp_architecturecomponents.repository.BusinessNews;
import myapp.newsapp_architecturecomponents.repository.web_repository.API_repository;

import java.util.List;

/**
 * Class for the UI that the user views their news in real time
 */
public class NewsFeed_ViewModel extends ViewModel{
    //private LiveData<List<BusinessNews>> news;
    private API_repository api_repository;
	private MutableLiveData<List<BusinessNews>> articles;

    public NewsFeed_ViewModel() {
        api_repository = new API_repository();
        //news = api_repository.getArticleContent();
    }
	
	public void makeNetworkCall() {
		this.articles = api_repository.getArticleContent();
	}

    public LiveData<List<BusinessNews>> getBusinessNewsItems() {
        //return news;
        return this.articles;
    }
}
