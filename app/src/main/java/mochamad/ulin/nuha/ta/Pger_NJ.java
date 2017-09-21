package mochamad.ulin.nuha.ta;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by king on 29/03/2017.
 */

public class Pger_NJ extends FragmentStatePagerAdapter {
    int numTabs;

    public Pger_NJ(FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                tampildonasi tab1 = new tampildonasi();
                return tab1;
            case 1:
                tampildonasi tab2 = new tampildonasi();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
