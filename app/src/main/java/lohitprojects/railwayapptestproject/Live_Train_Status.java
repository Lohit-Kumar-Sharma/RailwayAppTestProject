package lohitprojects.railwayapptestproject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Live_Train_Status extends AppCompatActivity {
    // Remove the below line after defining your own ad unit ID.
     // Live running status of train URL - http://api.railwayapi.com/v2/live/train/<train number>/date/<dd-mm-yyyy>/apikey/ggi8cg751m/
    // key id - pumkinduke0829@gmail.com

    // id - railwayapiid4@gmail.com - feb23oouc8
    // 23mhv522od - lkonline storage01

    // new rest api url - https://indianrailapi.com/api/v2/livetrainstatus/apikey/57bb3d20b2c4f37678dd18a2ff94bd05/trainnumber/12920/date/20190202/

    //String Url1 = "http://api.railwayapi.com/v2/live/train/";
    String Url1 = "https://indianrailapi.com/api/v2/livetrainstatus/apikey/57bb3d20b2c4f37678dd18a2ff94bd05/trainnumber/";
    String Url2="";
    private InterstitialAd mInterstitialAd;
    String Url3="/date/";
    String Url4="";
   // String Url5="/apikey/23mhv522od/";
    String date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
    TextView textView,textView2,textView3, textViewStatus;
    EditText ed1 ;
    Button button;
    String completeURL ;
    private ProgressDialog progressDialog;
    private TextView textViewT,textViewD ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live__train__status);
        String currentDate = DateFormat.getDateInstance().format(new Date());

       textViewT = findViewById(R.id.time);
       textViewD = findViewById(R.id.date);

        textView = findViewById(R.id.trainStatusDisplay);
        textView2 = findViewById(R.id.trainName);
        textView3=findViewById(R.id.noresulthandler);
        textViewStatus=findViewById(R.id.delayedby);

        ed1=findViewById(R.id.trainNo);

        button=findViewById(R.id.showResult);
        textViewD.setText(currentDate);

        final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textViewT.setText(new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(new Date()));
                someHandler.postDelayed(this, 1000);
            }
        }, 10);
       /* ed2.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                prevL = ed2.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 2 || length == 5)) {
                    editable.append("-");
                }
            }
        });

*/


        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
   .build();
        adView.loadAd(adRequest);

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Url2= ed1.getText().toString();
                Url4= date;
                if(TextUtils.isEmpty(Url2))
                {
                    ed1.setError("Enter train number!");
                    return;
                }
                completeURL =Url1+Url2+Url3+Url4+"/";
                progressDialog = new ProgressDialog(Live_Train_Status.this);

                progressDialog.setMessage("Fetching Live Status");
                progressDialog.setTitle("Wait a second....");

                progressDialog.show();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET, completeURL, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                progressDialog.dismiss();
//                                try {
//                                    String status = response.getString("position");
//                                    textView.setText(status);
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
                                try {
                                    String TrainName = response.getString("TrainNumber");

                                   // JSONObject trainName= new JSONObject(TrainName);
                                  //  String nam =trainName.getString("name");
                                    textView2.setText("Your train no is "+TrainName);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try{
                                    JSONObject CurrentStation= new JSONObject(response.getString("CurrentStation"));
                                    String stnName = CurrentStation.getString("StationName");
                                    String status = CurrentStation.getString("DelayInArrival");
                                    textView.setText("Your train is at "+stnName);
                                    textViewStatus.setText("Train is delayed by "+status);
                                }
                                catch(JSONException e){e.printStackTrace();}
                            }

                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Live_Train_Status.this,"Sorry No data Found , Make Sure Your Internet is working",Toast.LENGTH_LONG).show();

                    }
                }
                );

                MySingelton.getInstance(Live_Train_Status.this).addToRequestQue(jsonObjectRequest);

            }
        });
   //     Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();
     textView3.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             startActivity(new Intent(Live_Train_Status.this,TrainInfo.class));
         }
     });
    }
    public void onBackPressed() {
        super.onBackPressed();
        showInterstitial();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_live__train__status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=lohitprojects.railwayapptestproject"));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id1));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
Toast.makeText(Live_Train_Status.this,"Ads are necessary to keep this App Free to all Users",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
              //  startActivity(new Intent(Live_Train_Status.this,FRONT.class));
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                startActivity(new Intent(Live_Train_Status.this,FRONT.class));
            }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            }
    }

    private void loadInterstitial() {
        // Disable the next level button and load the ad.
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
    }
}
