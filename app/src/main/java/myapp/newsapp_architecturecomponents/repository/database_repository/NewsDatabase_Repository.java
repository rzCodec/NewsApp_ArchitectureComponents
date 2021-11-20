package myapp.newsapp_architecturecomponents.repository.database_repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import myapp.newsapp_architecturecomponents.repository.BusinessNews;
import needle.Needle;

import java.util.List;

public class NewsDatabase_Repository {
    private LiveData<List<BusinessNews>> database_newsItems;
    private BusinessNews_Dao dao;

    public NewsDatabase_Repository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        dao = appDatabase.businessNews_dao();
        database_newsItems = dao.getAll_BusinewssNewsItems();
    }

    public LiveData<List<BusinessNews>> getDatabase_newsItems() {
        return database_newsItems;
    }

    public void insert(final BusinessNews newsItem) {
        Needle.onBackgroundThread().execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(newsItem);
            }
        });
    }

    public void delete(final BusinessNews newsItem) {
        Needle.onBackgroundThread().execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(newsItem);
            }
        });
    }

    public void deleteAll() {
        Needle.onBackgroundThread().execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteAllNews();
            }
        });
    }
}
