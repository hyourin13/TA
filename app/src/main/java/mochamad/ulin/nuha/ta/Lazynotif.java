package mochamad.ulin.nuha.ta;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by GOBLIN on 6/21/2016.
 */
public class Lazynotif extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    HashMap<String, String> data_tampil = new HashMap<String, String>();
    private static LayoutInflater inflater = null;

    TextView txturl;
    private MapView mMapView;
    private GoogleMap mMap;

    //StreamFragmentManager
    public Lazynotif(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.custom_notif, null);
        //GoogleMap map1 = ((SupportMapFragment)vi.getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        // GoogleMap map = ((MapFragment) Context.getFragmentManager().findFragmentById(R.id.map)).getMap();
        TextView txttgl = (TextView) vi.findViewById(R.id.textView20);
        TextView txtnotif = (TextView) vi.findViewById(R.id.textView19);
        //txturl = (TextView)vi.findViewById(R.id.textView14);
        //ImageView thumb_image = (ImageView)vi.findViewById(R.id.gpemesanan);

        data_tampil = data.get(position);

        txttgl.setText(data_tampil.get(tampilnotif.TAGtgl));
        txtnotif.setText(data_tampil.get(tampilnotif.TAGnotif));

        return vi;
    }
}
