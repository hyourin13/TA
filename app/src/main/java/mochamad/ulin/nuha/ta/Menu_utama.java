package mochamad.ulin.nuha.ta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.iid.FirebaseInstanceId;


/**
 * Created by king on 27/04/2017.
 */

public class Menu_utama extends AppCompatActivity implements View.OnClickListener {
    private ViewFlipper mViewFlipper;

    LinearLayout btn_akun, btn_maps,btn_nj1,btn_alumni,btn_dunia;
    String no_hp,lat,lngg;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_utama);
        initViews();
        futar();
        bacaPreferensi();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Toast.makeText(this, refreshedToken, Toast.LENGTH_LONG).show();
        if (no_hp.toString().equals("0")){
            Intent i = new Intent(Menu_utama.this, Signin.class);
            startActivity(i);
            finish();
        }else {
            Toast.makeText(this, no_hp, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, lat, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, lngg, Toast.LENGTH_SHORT).show();
        }

    }

    public void futar(){
        //puter atas
        mViewFlipper = (ViewFlipper) this.findViewById(R.id.image_view_flipper);
        mViewFlipper.setAutoStart(false);
        mViewFlipper.setFlipInterval(5000);
        mViewFlipper.startFlipping();
        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
        mViewFlipper.showNext();

    }

    private void initViews() {
        btn_akun = (LinearLayout) findViewById(R.id.btn_akun);
        btn_akun.setOnClickListener(this);

        btn_maps = (LinearLayout) findViewById(R.id.btn_maps);
        btn_maps.setOnClickListener(this);

        btn_alumni = (LinearLayout) findViewById(R.id.btn_alumni);
        btn_alumni.setOnClickListener(this);

        btn_dunia = (LinearLayout) findViewById(R.id.btn_dunia);
        btn_dunia.setOnClickListener(this);

        btn_nj1 = (LinearLayout) findViewById(R.id.btn_nj);
        btn_nj1.setOnClickListener(this);
    }

    private void skipActivity(Class<?> classOf) {
        Intent intent = new Intent(getApplicationContext(), classOf);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_akun:
                skipActivity(Pilihan.class);
                break;
            case R.id.btn_maps:
                skipActivity(Menu_Lokasi.class);
                break;
            case R.id.btn_dunia:
                 skipActivity(Menu_Lokasi_All.class);
                break;
            case R.id.btn_alumni:
                skipActivity(Pilihan_Alumni.class);
                break;
            case R.id.btn_nj:
                skipActivity(Activity_NJ.class);
                break;

        }
    }


    private void bacaPreferensi() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        no_hp = pref.getString("no_hp", "0");
        lat = pref.getString("lat", "0");
        lngg = pref.getString("lngg", "0");

    }
}
