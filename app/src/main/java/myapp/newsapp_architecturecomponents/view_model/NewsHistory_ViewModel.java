package myapp.newsapp_architecturecomponents.view_model;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import myapp.newsapp_architecturecomponents.repository.BusinessNews;
import myapp.newsapp_architecturecomponents.repository.database_repository.NewsDatabase_Repository;

import java.util.List;

/**
 * Class for the UI that the user can see their saved news
 */
public class NewsHistory_ViewModel extends AndroidViewModel {

    private LiveData<List<BusinessNews>> database_newsItems; //LiveData is used so whatever changes are made are reflected
    private NewsDatabase_Repository newsDatabase_repository;

    public NewsHistory_ViewModel(@NonNull Application application) {
        super(application);
        newsDatabase_repository = new NewsDatabase_Repository(application);
	//Retrieve the news from the db repo and get it ready to be returned and displayed in a RecyclerView and CardView
        this.database_newsItems = newsDatabase_repository.getDatabase_newsItems();
    }

    public void insert(BusinessNews newsItem) {
        newsDatabase_repository.insert(newsItem);
    }

    public void delete(BusinessNews newsItem) {
        newsDatabase_repository.delete(newsItem);
    }

    public void deleteAll() {
        newsDatabase_repository.deleteAll();
    }

    public LiveData<List<BusinessNews>> getDatabaseBusinessNewsItems() {
        return database_newsItems;
    }
}
