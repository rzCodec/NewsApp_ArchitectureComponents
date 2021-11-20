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
	setTitle("Financial & Business News");
      
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        FragmentPager fragmentPager = new FragmentPager(getSupportFragmentManager(), "Newsfeed", "History");
     
        viewPager.setAdapter(fragmentPager);
     
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
