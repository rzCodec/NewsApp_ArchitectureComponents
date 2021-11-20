package myapp.newsapp_architecturecomponents.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import myapp.newsapp_architecturecomponents.repository.BusinessNews;
import myapp.newsapp_architecturecomponents.repository.web_repository.Api_repository;

import java.util.List;

public class NewsFeed_ViewModel extends ViewModel {
    private Api_repository api_repo = new Api_repository();
    private LiveData<List<BusinessNews>> news;
   
    public void getNews(){
	api_repo.getArticleContent();
    }

    public LiveData<List<BusinessNews>> getBusinessNewsItems() {
        return api_repo.getArticles();
    }
}
