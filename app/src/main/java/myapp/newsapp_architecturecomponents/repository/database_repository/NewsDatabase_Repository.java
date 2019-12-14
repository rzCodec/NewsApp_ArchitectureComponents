package myapp.newsapp_architecturecomponents.repository.database_repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import myapp.newsapp_architecturecomponents.repository.BusinessNews;
import needle.Needle;

import java.util.List;

public class NewsDatabase_Repository {
	//We use LiveData because whenever a change is made to this data, the UI updates automatically
	//Saves the hassle of updating it manually
    private LiveData<List<BusinessNews>> database_newsItems;
    private BusinessNews_Dao dao;

    /**
     * Whenever a new repository is created, we load our database items
     * @param application is passed from the relevant activity / fragment
     */
    public NewsDatabase_Repository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        dao = appDatabase.businessNews_dao(); //Get access to the database after it has been created
        database_newsItems = dao.getAll_BusinewssNewsItems();
        //Getting stuff from the database does not need to be on a background thread
    }

    public LiveData<List<BusinessNews>> getDatabase_newsItems() {
        return database_newsItems;
    }

    //We must perform database operations on a background thread.
    //The main thread should not be blocked
    //It is good practice to keep the Main thread perform only UI work.
	//Makes use of the Needle library, an alternative to AsyncTask 

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
