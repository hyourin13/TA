package mochamad.ulin.nuha.ta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Activity_NJ extends AppCompatActivity implements View.OnClickListener {
    LinearLayout btn_radio,btn_website;
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

        }
    }

    private void skipActivity(Class<?> classOf) {
        Intent intent = new Intent(getApplicationContext(), classOf);
        startActivity(intent);
    }
}
