package myapp.newsapp_architecturecomponents.repository.database_repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import myapp.newsapp_architecturecomponents.repository.BusinessNews;

import java.util.List;

@Dao
public interface BusinessNews_Dao {
    @Query("SELECT * FROM business_news_table")
    LiveData<List<BusinessNews>> getAll_BusinewssNewsItems();

    //Save the current business news item
    @Insert
    void insert(BusinessNews newsItem);

    //Delete only the current business news article
    @Delete
    void delete(BusinessNews newsItem);

    //Deletes all business news items
    @Query("DELETE FROM business_news_table")
    void deleteAllNews();
}
