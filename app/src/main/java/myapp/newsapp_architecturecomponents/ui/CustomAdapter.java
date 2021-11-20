package myapp.newsapp_architecturecomponents.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import myapp.newsapp_architecturecomponents.R;
import myapp.newsapp_architecturecomponents.repository.BusinessNews;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context = null;
    private List<BusinessNews> items;
    private iCallback mCallback;

    //Interface that passes the news object and the position of the current news item in the recycler view
    public interface iCallback {
        void returnBusinessNewsItem(BusinessNews businessNews, int position);
    }

    public CustomAdapter(iCallback mCallback, Context context) {
        this.mCallback = mCallback;
        this.context = context;
		this.items = new ArrayList<BusinessNews>(); 
    }

    public CustomAdapter(List<BusinessNews> items, Context context) {
        this.items = items;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BusinessNews item = items.get(position);
        holder.mTextView_ArticleTitle.setText(item.getArticle_title().trim());
        holder.mTextView_ArticleDate.setText(item.getArticle_date().trim());
		int iContentLength = item.getArticle_content().length();
		if(iContentLength >= 100) {
			 holder.mTextView_ArticleContent.setText(item.getArticle_content().substring(0, 100).trim() + "...");
		} 
		else {
			 holder.mTextView_ArticleContent.setText(item.getArticle_content().substring(0, iContentLength).trim() + "...");
		}
       
        if(item.getSaved() == true) {
            holder.mImageView.setImageBitmap(new ImageHandler().loadImageFromStorage(item.getFile_path(), item.getArticle_imagefilename()));
        } else Picasso.get().load("http:" + item.getArticle_image_link()).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public void setItems(List<BusinessNews> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public BusinessNews getBusinessNewsItem(int position) {
        return items.get(position);
    }
	
	public void refreshItems(List<BusinessNews> items) {
		this.items.clear();
		setItems(items);
	}

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView_ArticleTitle;
        private TextView mTextView_ArticleDate;
        private TextView mTextView_ArticleContent;
        private ImageView mImageView;
        private Button mButton_ViewNews;

        public MyViewHolder(View v) {
            super(v);
            mTextView_ArticleTitle = v.findViewById(R.id.textview_article_title);
            mTextView_ArticleDate = v.findViewById(R.id.textview_article_date);
            mTextView_ArticleContent = v.findViewById(R.id.textview_article_content);
            mImageView = v.findViewById(R.id.imageView_article_image);
            mButton_ViewNews = v.findViewById(R.id.material_button_OpenArticle);
            mButton_ViewNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BusinessNews item = items.get(getAdapterPosition());
                    //mCallback is defined during object creation
                    mCallback.returnBusinessNewsItem(item, getAdapterPosition());
                }
            });
        }
    }
}
