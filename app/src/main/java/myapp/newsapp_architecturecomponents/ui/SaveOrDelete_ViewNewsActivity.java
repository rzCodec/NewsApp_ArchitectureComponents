package myapp.newsapp_architecturecomponents.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.webkit.WebView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import myapp.newsapp_architecturecomponents.R;
import myapp.newsapp_architecturecomponents.repository.BusinessNews;
import myapp.newsapp_architecturecomponents.view_model.NewsHistory_ViewModel;

public class SaveOrDelete_ViewNewsActivity extends AppCompatActivity {
    private boolean toSave = false;
    private BusinessNews businessNewsItem;
    private int position; //Position of the business news item in the recycler view
    //Variables used to store the image to internal storage
    private ImageHandler imageHandler;
    private Bitmap bitmap = null;
    private String fileName = "";

    /**
     * We will send data back to the fragment that started this activity
     * The fragment contains the view model which will delete the item in the fragment
     * It makes no sense to pass the view model to this class
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_or_delete_viewnews);
        FloatingActionButton fab = findViewById(R.id.fab_save_or_delete);
        TextView textView_ViewArticleContent = findViewById(R.id.textview_view_article_content);
        final ImageView imageView = findViewById(R.id.SaveOrDelete_imageView);
        imageHandler = new ImageHandler();


        Intent intent = getIntent();
        position = intent.getIntExtra("POSITION", 0);

        if(intent.hasExtra("TO_SAVE")) {
            toSave = true;
            //This means the button's functionality will change such that it will save
            //the piece of news to the database
            businessNewsItem = intent.getParcelableExtra("TO_SAVE");
            Toast.makeText(this, "Saving Works", Toast.LENGTH_SHORT).show();
            Picasso.get().load("http:" + businessNewsItem.getArticle_image_link()).into(imageView);

        }
        else if(intent.hasExtra("TO_DELETE")) {
            toSave = false;
            //Otherwise the button will delete the news article
            businessNewsItem = intent.getParcelableExtra("TO_DELETE");
            Toast.makeText(this, "Deleting Works", Toast.LENGTH_SHORT).show();
            imageView.setImageBitmap(imageHandler.loadImageFromStorage(businessNewsItem.getFile_path(), businessNewsItem.getArticle_imagefilename()));
        }

        textView_ViewArticleContent.setText(businessNewsItem.getArticle_content().replaceAll("\\s+$", "") + "\n");
		//String text;
		//text = "<html><body><p align=\"justify\">";
		//text += businessNewsItem.getArticle_content();
		//text += "</p></body></html>";
		//textView_ViewArticleContent.loadData("<p style=\"text-align: justify\">"+ businessNewsItem.getArticle_content() + "</p>", "text/html", "UTF-8");
        
		getSupportActionBar().setTitle(businessNewsItem.getArticle_title());

        final NewsHistory_ViewModel newsHistory_viewModel =
                ViewModelProviders.of(this).get(NewsHistory_ViewModel.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toSave == true) {            
                    Toast.makeText(SaveOrDelete_ViewNewsActivity.this,
                            "Saved news article", Toast.LENGTH_SHORT).show();
                    businessNewsItem.setIsSaved(true);
                    // When saving the news article, ensure it knows it resides in the Room Database
                    bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                    fileName = businessNewsItem.getArticle_title() + ".png";
                    // Set the file path and file name of the business news article item...
					businessNewsItem.setArticle_imagefilename(fileName); 
                    String filepath = imageHandler.saveToInternalStorage(bitmap, SaveOrDelete_ViewNewsActivity.this, fileName);
                    businessNewsItem.setFile_path(filepath);
					// THEN, save it to the database
					newsHistory_viewModel.insert(businessNewsItem);
				} else {
                    Toast.makeText(SaveOrDelete_ViewNewsActivity.this,
                            "Deleted news article", Toast.LENGTH_SHORT).show();

                    // Only delete the news when the user presses the delete button
                    // Then close the activity
                    // Prepare data intent
                    Intent data = new Intent();
                    //DeleteNews is the key while the position is the value
                    //The position is the position of the business news item in the recycler view
                    //We need the position to know where to look in the array list to delete from
                    data.putExtra("DeleteNews", position);
                    // Activity finished ok, return the data
                    setResult(RESULT_OK, data);
                    finish(); //Call finish to close the activity

                }
            }
        });
    }

    /**
     * Close the activity
     */
    @Override
    public void finish() {
        super.finish();
    }
}
