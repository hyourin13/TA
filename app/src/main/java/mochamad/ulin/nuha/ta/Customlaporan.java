package mochamad.ulin.nuha.ta;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


public class Customlaporan extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String,String>> data;
    HashMap<String,String> data_tampil = new HashMap<String,String>();
    private static LayoutInflater inflater = null;

    //StreamFragmentManager
    public Customlaporan(Activity a, ArrayList<HashMap<String, String>> d){
        activity=a;
        data=d;
        inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        View vi =convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.activity_list_tanggapan,null);
        TextView keluhan = (TextView)vi.findViewById(R.id.TV_keluhan_list);
        ImageView foto = (ImageView)vi.findViewById(R.id.img_card_jalan);
        TextView alamat = (TextView)vi.findViewById(R.id.TV_alamat_list);
        TextView status = (TextView)vi.findViewById(R.id.TV_status_list);
        data_tampil = data.get(position);
        keluhan.setText(data_tampil.get(Tanggapan.TAGkeluhan));
        alamat.setText(data_tampil.get(Tanggapan.TAGtalamat));
        status.setText(data_tampil.get(Tanggapan.TAGstatus));
        Picasso.with(activity)
                .load(data_tampil.get(Tanggapan.TAGfoto))
                .placeholder(R.mipmap.ic_loogo)   // optional
                .error(R.mipmap.ic_loogo)      // optional
                .resize(400,400)                        // optional
                .into(foto);

        return vi;
    }
}


