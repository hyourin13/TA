package mochamad.ulin.nuha.ta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * Created by Belal on 12/22/2015.
 */
public class GridViewAdapter extends BaseAdapter {

    //Imageloader to load images
    private ImageLoader imageLoader;
    private LayoutInflater inflater;
    //Context
    private Context context;

    //Array List that would contain the urls and the titles for the images
    private ArrayList<String> images;
    private ArrayList<String> names;
    private ArrayList<String> alamat;
    private ArrayList<String> nis;

    public GridViewAdapter(Context context, ArrayList<String> images, ArrayList<String> names, ArrayList<String> alamat, ArrayList<String> nis){
        //Getting all the values
        this.context = context;
        this.images = images;
        this.names = names;
        this.alamat = alamat;
        this.nis = nis;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Creating a Frame layout
        FrameLayout FrameLay = new FrameLayout(context);

        //Creating a linear layout
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout linearLayout1 = new LinearLayout(context);
        linearLayout1.setOrientation(LinearLayout.VERTICAL);

        //NetworkImageView
        NetworkImageView networkImageView = new NetworkImageView(context);

        //Initializing ImageLoader
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(images.get(position), ImageLoader.getImageListener(networkImageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        //Setting the image url to load
        networkImageView.setImageUrl(images.get(position),imageLoader);

        //Creating a textview to show the title
        TextView textView = new TextView(context);
        textView.setText(names.get(position));

        TextView textView1 = new TextView(context);
        textView1.setText(alamat.get(position));

        TextView textView2 = new TextView(context);
        textView2.setText(nis.get(position));

        //Scaling the imageview
        networkImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        networkImageView.setLayoutParams(new GridView.LayoutParams(150,150));

        //Adding views to the layout

        linearLayout.addView(FrameLay);
        FrameLay.addView(networkImageView);
        linearLayout.addView(linearLayout1);
        linearLayout1.addView(textView);

        //Returnint the layout
        return linearLayout;

    }
}
