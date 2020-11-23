package myapp.newsapp_architecturecomponents.repository;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "business_news_table")
public class BusinessNews implements Parcelable {


    @PrimaryKey(autoGenerate = true)
    private int id;

    //SerializedName annotation is used for GSON and Retrofit object conversion
	//There is no need to use setter methods since Retrofit will automatically map the corresponding named JSON objects
	//To the variables named below
    @SerializedName("article_title")
    private String article_title;

    @SerializedName("article_date")
    private String article_date;

    @SerializedName("article_content")
    private String article_content;

    @SerializedName("article_image_link")
    private String article_image_link;

    private String article_imagefilename;
	
	private String file_path;

    private boolean isSaved;

    public BusinessNews(String article_title,
                        String article_date,
                        String article_content,
                        String article_image_link)
    {
        this.article_title = article_title;
        this.article_date = article_date;
        this.article_content = article_content;
        this.article_image_link = article_image_link;
        this.isSaved = false;
    }

    //Set method for the id so Room can set the id automatically
    public void setId(int id) {
        this.id = id;
    }
	
	public void setFile_path(String file_path){
		this.file_path = file_path;
	}

    public void setArticle_imagefilename(String path){
        this.article_imagefilename = path;
    }

    public void setIsSaved(Boolean bool) {
        this.isSaved = bool;
    }

    public Boolean getSaved() {
        return isSaved;
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getArticle_title() {
        return article_title;
    }

    public String getArticle_date() {
        return article_date;
    }

    public String getArticle_content() {
        return article_content;
    }

    public String getArticle_image_link() {
        return article_image_link;
    }
	
	public String getFile_path() {
        return file_path;
	}

    public String getArticle_imagefilename() {
        return article_imagefilename;
    }

    //Parcelable methods --------------------------------------------------------------
    private BusinessNews(Parcel parcel) {
        this.article_title = parcel.readString();
        this.article_date = parcel.readString();
        this.article_content = parcel.readString();
        this.article_image_link = parcel.readString();
		this.article_imagefilename = parcel.readString();
		this.file_path = parcel.readString();
    }

    public static final Parcelable.Creator<BusinessNews> CREATOR = new Parcelable.Creator<BusinessNews>() {
        public BusinessNews createFromParcel(Parcel in) {
            return new BusinessNews(in); //Refer to the private Subject Constructor
        }

        public BusinessNews[] newArray(int size) {
            return new BusinessNews[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.article_title);
        parcel.writeString(this.article_date);
        parcel.writeString(this.article_content);
        parcel.writeString(this.article_image_link);
        parcel.writeString(this.article_imagefilename);
		parcel.writeString(this.file_path);
    }
}
