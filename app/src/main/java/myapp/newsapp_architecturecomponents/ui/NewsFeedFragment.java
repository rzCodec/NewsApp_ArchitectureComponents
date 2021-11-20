package myapp.newsapp_architecturecomponents.ui;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import myapp.newsapp_architecturecomponents.R;
import myapp.newsapp_architecturecomponents.repository.BusinessNews;
import myapp.newsapp_architecturecomponents.view_model.NewsFeed_ViewModel;

import java.util.List;

public class NewsFeedFragment extends Fragment {
    private NewsFeed_ViewModel newsFeed_viewModel;
    private View view;

    public static NewsFeedFragment newInstance() {
        return new NewsFeedFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
	    
        view = inflater.inflate(R.layout.fragment_news_feed, container, false);	
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
	    
        final CustomAdapter customAdapter = new CustomAdapter(new CustomAdapter.iCallback() {
            @Override
            public void returnBusinessNewsItem(BusinessNews businessNews, int position) {
                Intent intent = new Intent(getActivity(), SaveOrDelete_ViewNewsActivity.class);
                intent.putExtra("TO_SAVE", businessNews);
                startActivity(intent);
            }
        }, view.getContext());
	    
        recyclerView.setAdapter(customAdapter);
	newsFeed_viewModel = ViewModelProviders.of(this).get(NewsFeed_ViewModel.class);
	FloatingActionButton fab = view.findViewById(R.id.fab_Makenetworkcall);
	
	fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
	            newsFeed_viewModel.getNews();
	    }
	});
		
	newsFeed_viewModel.getNews();
	final LiveData<List<BusinessNews>> liveNewsList = newsFeed_viewModel.getBusinessNewsItems();
	liveNewsList.observe(this, new Observer<List<BusinessNews>>() {
            @Override
            public void onChanged(List<BusinessNews> businessNews) {
                customAdapter.setItems(businessNews);			
            }
        });	
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
