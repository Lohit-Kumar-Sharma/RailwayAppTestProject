package lohitprojects.railwayapptestproject;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class pnrScreen1 extends AppCompatActivity implements View.OnClickListener {
    // Remove the below line after defining your own ad unit ID.
    private EditText editText;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnr_screen1);

        editText = findViewById(R.id.enteredpnr);
        button = findViewById(R.id.checkpnrstatus);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
  //      Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();
        button.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pnr_screen1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        if (view == button) {
            String pnr = editText.getText().toString().trim();
            if ((pnr.length() != 10) || (TextUtils.isEmpty(pnr))) {
                editText.setError("Enter valid 10 digits PNR number!");
                return;

            } else {
                Intent intent = new Intent(pnrScreen1.this, pnr_screen2.class);
                intent.putExtra("key", pnr);
                startActivity(intent);
            }
        }
    }
}