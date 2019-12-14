package myapp.newsapp_architecturecomponents.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import myapp.newsapp_architecturecomponents.R;
import myapp.newsapp_architecturecomponents.repository.BusinessNews;
import myapp.newsapp_architecturecomponents.view_model.NewsHistory_ViewModel;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class NewsHistoryFragment extends Fragment {

    private NewsHistory_ViewModel mViewModel;
    private final static int REQUEST_CODE = 6500;
    private CustomAdapter customAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_history, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        customAdapter = new CustomAdapter(new CustomAdapter.iCallback() {
            @Override
            public void returnBusinessNewsItem(BusinessNews businessNews, int position) {
                Intent intent = new Intent(getActivity(), SaveOrDelete_ViewNewsActivity.class);
                //The user is viewing news that was already saved
                //Therefore the only option is to delete it
				//We will then pass data back from the viewing activity, then delete the article in this fragment
                intent.putExtra("TO_DELETE", businessNews);
                intent.putExtra("POSITION", position);
                //Position is an integer that determines which card was selected in the recycler view
                startActivityForResult(intent, REQUEST_CODE);
            }
        }, view.getContext());

        recyclerView.setAdapter(customAdapter);

        // TODO: Use the ViewModel
        mViewModel = ViewModelProviders.of(this).get(NewsHistory_ViewModel.class);
        mViewModel.getDatabaseBusinessNewsItems().observe(this, new Observer<List<BusinessNews>>() {
            @Override
            public void onChanged(List<BusinessNews> businessNews) {
                //Show a progress dialog
                customAdapter.setItems(businessNews);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mViewModel.delete(customAdapter.getBusinessNewsItem(viewHolder.getAdapterPosition()));
                Toast.makeText(view.getContext(), "News deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * After the user has deleted a saved business article, the activity the user was in will close.
     * Then the news will be deleted from the list of news that is shown in the recycler view.
     * By getting the position
     * The news cannot be deleted in the activity in which the user views it in.
     * It must be deleted in this fragment which shows all the saved news in a recycler view
     * It makes no sense to pass this view model to the viewing activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("DeleteNews")) {
                int position = data.getExtras().getInt("DeleteNews");
                mViewModel.delete(customAdapter.getBusinessNewsItem(position));
            }
        }
    }
}
