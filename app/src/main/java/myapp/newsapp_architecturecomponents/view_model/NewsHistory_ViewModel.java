package myapp.newsapp_architecturecomponents.view_model;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import myapp.newsapp_architecturecomponents.repository.BusinessNews;
import myapp.newsapp_architecturecomponents.repository.database_repository.NewsDatabase_Repository;
import java.util.List;

public class NewsHistory_ViewModel extends AndroidViewModel {
    private LiveData<List<BusinessNews>> database_newsItems;
    private NewsDatabase_Repository newsDatabase_repository;

    public NewsHistory_ViewModel(@NonNull Application application) {
        super(application);
        newsDatabase_repository = new NewsDatabase_Repository(application);
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
