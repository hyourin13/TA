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
    private ViewFlipper mViewFlipper,mViewFlipper1,mViewFlipper2,mViewFlipper3,mViewFlipper4,mViewFlipper5,mViewFlipper6;

    LinearLayout btn_akun, btn_maps, btn_radio,btn_website,btn_alumni,btn_dunia;
    String no_hp;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_utama);
        initViews();
        futar();
        bacaPreferensi();


    }

    public void futar(){
        //puter atas
        mViewFlipper = (ViewFlipper) this.findViewById(R.id.image_view_flipper);
        mViewFlipper.setAutoStart(false);
        mViewFlipper.setFlipInterval(4000);
        mViewFlipper.startFlipping();
        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
        mViewFlipper.showNext();

        //puter kanan atas
        mViewFlipper1 = (ViewFlipper) this.findViewById(R.id.image_view_flipper2);
        mViewFlipper1.setAutoStart(false);
        mViewFlipper1.setFlipInterval(2000);
        mViewFlipper1.startFlipping();
        mViewFlipper1.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        mViewFlipper1.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
        mViewFlipper1.showNext();

        //puter kiri atas
        mViewFlipper2 = (ViewFlipper) this.findViewById(R.id.image_view_flipper3);
        mViewFlipper2.setAutoStart(false);
        mViewFlipper2.setFlipInterval(2250);
        mViewFlipper2.startFlipping();
        mViewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        mViewFlipper2.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
        mViewFlipper2.showNext();

        //puter kanan tengah
        mViewFlipper3 = (ViewFlipper) this.findViewById(R.id.image_view_flipper4);
        mViewFlipper3.setAutoStart(false);
        mViewFlipper3.setFlipInterval(2250);
        mViewFlipper3.startFlipping();
        mViewFlipper3.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        mViewFlipper3.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
        mViewFlipper3.showNext();

        //puter kiri tengah
        mViewFlipper4 = (ViewFlipper) this.findViewById(R.id.image_view_flipper5);
        mViewFlipper4.setAutoStart(false);
        mViewFlipper4.setFlipInterval(2000);
        mViewFlipper4.startFlipping();
        mViewFlipper4.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        mViewFlipper4.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
        mViewFlipper4.showNext();

        //puter kanan bawah
        mViewFlipper5 = (ViewFlipper) this.findViewById(R.id.image_view_flipper6);
        mViewFlipper5.setAutoStart(false);
        mViewFlipper5.setFlipInterval(2000);
        mViewFlipper5.startFlipping();
        mViewFlipper5.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        mViewFlipper5.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
        mViewFlipper5.showNext();

        //puter kiri bawah
        mViewFlipper6 = (ViewFlipper) this.findViewById(R.id.image_view_flipper7);
        mViewFlipper6.setAutoStart(false);
        mViewFlipper6.setFlipInterval(2250);
        mViewFlipper6.startFlipping();
        mViewFlipper6.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        mViewFlipper6.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
        mViewFlipper6.showNext();
    }

    private void initViews() {
        btn_akun = (LinearLayout) findViewById(R.id.btn_akun);
        btn_akun.setOnClickListener(this);

        btn_maps = (LinearLayout) findViewById(R.id.btn_maps);
        btn_maps.setOnClickListener(this);

        btn_radio = (LinearLayout) findViewById(R.id.btn_radio);
        btn_radio.setOnClickListener(this);

        btn_website = (LinearLayout) findViewById(R.id.btn_website);
        btn_website.setOnClickListener(this);

        btn_alumni = (LinearLayout) findViewById(R.id.btn_alumni);
        btn_alumni.setOnClickListener(this);

        btn_dunia = (LinearLayout) findViewById(R.id.btn_dunia);
        btn_dunia.setOnClickListener(this);
    }

    private void skipActivity(Class<?> classOf) {
        Intent intent = new Intent(getApplicationContext(), classOf);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_akun:

                break;
            case R.id.btn_maps:
                skipActivity(Menu_Lokasi.class);
                break;
            case R.id.btn_radio:
                skipActivity(Activity_Radio.class);
                break;
            case R.id.btn_website:
                 skipActivity(Activity_Web.class);
                break;
            case R.id.btn_alumni:

                break;
            case R.id.btn_dunia:
                skipActivity(Menu_Lokasi_All.class);
              /*  final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Menu_utama.this);
                alertDialog.setTitle("Gagal");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Selesaikan Dulu yang Lokasi");
                alertDialog.show();*/
                break;

        }
    }


    private void bacaPreferensi() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        no_hp = pref.getString("no_hp", "0");
    }
}
