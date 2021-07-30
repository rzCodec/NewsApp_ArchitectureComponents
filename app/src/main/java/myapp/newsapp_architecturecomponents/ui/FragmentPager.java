package myapp.newsapp_architecturecomponents.ui;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by User on 2017/09/16.
 */
public class FragmentPager extends FragmentPagerAdapter {

    private String[] arrTitles;
    private int numFragments;
    private Fragment fragment = null;

    public FragmentPager(FragmentManager fm, String... paramTitles) {
        super(fm);
        if ((paramTitles != null && paramTitles.length > 0)) {
            arrTitles = new String[paramTitles.length];
            this.numFragments = paramTitles.length;
            for (int i = 0; i < paramTitles.length; i++) {
                arrTitles[i] = paramTitles[i];
            }
        } else {
            this.arrTitles = new String[1];
            this.arrTitles[0] = "Default One";
            this.arrTitles[1] = "Default Two";
        }
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NewsFeedFragment();
        } else if (position == 1) {
            return new NewsHistoryFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return this.numFragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return arrTitles[position];
    }
}
