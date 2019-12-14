package myapp.newsapp_architecturecomponents.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import myapp.newsapp_architecturecomponents.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get the viewpager and instantiate the pager adapter
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        FragmentPager fragmentPager =
                new FragmentPager(getSupportFragmentManager(), "Business News", "History");
        //Set the viewpager to the pager adapter
        viewPager.setAdapter(fragmentPager);
        //Get the tab layout and then set it with the viewpager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
