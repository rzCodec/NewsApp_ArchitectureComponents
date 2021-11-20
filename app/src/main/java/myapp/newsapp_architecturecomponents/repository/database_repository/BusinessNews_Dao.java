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

    @Insert
    void insert(BusinessNews newsItem);
    
    @Delete
    void delete(BusinessNews newsItem);

    @Query("DELETE FROM business_news_table")
    void deleteAllNews();
}
