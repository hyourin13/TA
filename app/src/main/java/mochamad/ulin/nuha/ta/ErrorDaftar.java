package mochamad.ulin.nuha.ta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ryu on 23/05/2017.
 */

public class ErrorDaftar extends AppCompatActivity implements View.OnClickListener {
    TextView daftar_e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.errordaftar);
        daftar_e = (TextView) findViewById(R.id.signinin);
        daftar_e.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signinin:
            Intent pindah1 = new Intent(this, Login.class);
            startActivity(pindah1);
            break;
        }
    }
}
