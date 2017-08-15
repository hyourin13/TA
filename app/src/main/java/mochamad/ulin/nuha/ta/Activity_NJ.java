package mochamad.ulin.nuha.ta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Activity_NJ extends AppCompatActivity implements View.OnClickListener {
    LinearLayout btn_radio,btn_website,btn_pesan1,btn_masukan1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__nj);
        initViews();
    }


    private void initViews() {

        btn_radio = (LinearLayout) findViewById(R.id.btn_radio);
        btn_radio.setOnClickListener(this);

        btn_website = (LinearLayout) findViewById(R.id.btn_website);
        btn_website.setOnClickListener(this);

        btn_pesan1 = (LinearLayout) findViewById(R.id.btn_pesan);
        btn_pesan1.setOnClickListener(this);

        btn_masukan1 = (LinearLayout) findViewById(R.id.btn_masukan);
        btn_masukan1.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_website:
                skipActivity(Activity_Web.class);
                break;
            case R.id.btn_radio:
                skipActivity(Activity_Radio.class);
                break;
            case R.id.btn_pesan:
                skipActivity(tampilnotif.class);
                break;
            case R.id.btn_masukan:
                skipActivity(Activity_Masukan.class);
                break;

        }
    }

    private void skipActivity(Class<?> classOf) {
        Intent intent = new Intent(getApplicationContext(), classOf);
        startActivity(intent);
    }
}
