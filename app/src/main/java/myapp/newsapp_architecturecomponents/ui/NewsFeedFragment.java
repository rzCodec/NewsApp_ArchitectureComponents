package myapp.newsapp_architecturecomponents.ui;


import android.content.Intent;
import android.os.Bundle;
import androidx.lifecycle.LiveData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
        //TODO Create and set the adapter
        final CustomAdapter customAdapter = new CustomAdapter(new CustomAdapter.iCallback() {
            @Override
            public void returnBusinessNewsItem(BusinessNews businessNews, int position) {
                Intent intent = new Intent(getActivity(), SaveOrDelete_ViewNewsActivity.class);
                //The user is for the first time viewing the news
                //Therefore the only option is to save it.
                intent.putExtra("TO_SAVE", businessNews);
                startActivity(intent);
            }
        }, view.getContext());
        recyclerView.setAdapter(customAdapter);
		newsFeed_viewModel = ViewModelProviders.of(this).get(NewsFeed_ViewModel.class);
		
	    FloatingActionButton fab = view.findViewById(R.id.fab_getNews);
		fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           		newsFeed_viewModel.makeNetworkCall();
					final LiveData<List<BusinessNews>> liveNewsList = newsFeed_viewModel.getBusinessNewsItems();
		liveNewsList.observe(NewsFeedFragment.this, new Observer<List<BusinessNews>>() {
            @Override
            public void onChanged(List<BusinessNews> businessNews) {
                //Enable progress bar
              
                //Update the UI by passing in the business news list which is obtained from the RESTful API
                //And makes use of a Retrofit client
                //Inside the custom adapter class, there is a method to set the notes and then update /refresh the UI
                customAdapter.refreshItems(businessNews);
                liveNewsList.removeObserver(this);
            }
        });
            }
        });
		
		
        //TODO Don't forget to add internet permissions in the manifest file since this app will make API calls
		//LiveData has the method "observe()" meaning the live data now observes any changes after the API call is made
        
		newsFeed_viewModel.makeNetworkCall();
		final LiveData<List<BusinessNews>> liveNewsList = newsFeed_viewModel.getBusinessNewsItems();
		liveNewsList.observe(this, new Observer<List<BusinessNews>>() {
            @Override
            public void onChanged(List<BusinessNews> businessNews) {
                //Enable progress bar
              
                //Update the UI by passing in the business news list which is obtained from the RESTful API
                //And makes use of a Retrofit client
                //Inside the custom adapter class, there is a method to set the notes and then update /refresh the UI
                customAdapter.setItems(businessNews);
                liveNewsList.removeObserver(this);
            }
        });
		
        return view;
    }//end of conCreateView
	
	private void retrieveNews(CustomAdapter customAdapter) {
       	
	}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }



}
