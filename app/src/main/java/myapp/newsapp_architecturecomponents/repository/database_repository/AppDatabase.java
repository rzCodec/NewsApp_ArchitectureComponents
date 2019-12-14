package myapp.newsapp_architecturecomponents.repository.database_repository;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import myapp.newsapp_architecturecomponents.repository.BusinessNews;
import needle.Needle;

@Database(entities = {BusinessNews.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    public abstract BusinessNews_Dao businessNews_dao();

    public static synchronized AppDatabase getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "BusinessNews_Database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback) //This adds hard coded data when the database is created
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //populateWithExistingData(instance);
        }
    };

    /**
     * Database operations must be performed on a background thread
     * This adds some data so we can see what the app looks like
     */
    private static void populateWithExistingData(AppDatabase appDatabase) {
        final BusinessNews_Dao dao = appDatabase.businessNews_dao();
        Needle.onBackgroundThread().execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(new BusinessNews("Title 1",
                        "1st August 2019",
                        "... some stuff",
                        "fake link 1"));

                dao.insert(new BusinessNews("Title 2",
                        "18th June 2019",
                        "Meh",
                        "fake link 2"));

                dao.insert(new BusinessNews("Title 3",
                        "4th August 2019",
                        "Random content #3",
                        "fake link 3"));
            }
        });
    }

}
