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
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class TrainInfo extends AppCompatActivity {
    // Remove the below line after defining your own ad unit ID.

// train info URL - http://api.railwayapi.com/v2/name-number/train/12920/apikey/l3g6kb436k
    // id used - lkdragon004@gmail.com

    // new id - railwayapiid3@gmail.com  - oasv0orgs3
TextView Tname;
    private InterstitialAd mInterstitialAd;
ListView listView;
private String Url1="http://api.railwayapi.com/v2/name-number/train/";
private  String Url2="";
private  String Url3="/apikey/23mhv522od";
private String fullTinfoUrl ;
    String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
EditText editText;
Button button;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String,String>>();
    ArrayAdapter adapter;
private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_info);
        Tname = findViewById(R.id.DisplayTname);
        listView=findViewById(R.id.listitems);
        button = findViewById(R.id.getInfo);
        editText = findViewById(R.id.trainNo);
        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
.build();
        adView.loadAd(adRequest);

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
//        Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();

      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
          Url2 = editText.getText().toString().trim();

          if(TextUtils.isEmpty(Url2))
          {
              editText.setError("Enter Valid Train Number !!");
              return;
          }
         else
           {
              fullTinfoUrl = Url1+Url2+Url3;
               progressDialog = new ProgressDialog(TrainInfo.this);

               progressDialog.setMessage("Fetching Train Running Days");
               progressDialog.setTitle("Wait a second....");

               progressDialog.show();
              JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                       fullTinfoUrl, null,
                       new Response.Listener<JSONObject>() {
                           @Override
                           public void onResponse(JSONObject response) {
                                    progressDialog.dismiss();
                               try {
                                   JSONObject ob = response.getJSONObject("train");
                                     String name = ob.getString("name");
                                     Tname.setText(name);
                                     JSONArray A1 = ob.getJSONArray("days");
                                     for(int i =0 ;i<A1.length();i++)
                                     {
                                         HashMap<String, String> map = new HashMap<String, String>();
                                         JSONObject object = A1.getJSONObject(i);
                                         String status = object.getString("runs");
                                         String day = object.getString("code");

                                         map.put("Status : ",status);

                                         map.put("Day : ",day);


                                         arrayList.add(map);
                                     }






                               } catch (JSONException e) {

                                   e.printStackTrace();
                               }
                           }
                       },
                       new Response.ErrorListener() {

                           @Override
                           public void onErrorResponse(VolleyError error) {
                               progressDialog.dismiss();
                               Toast.makeText(TrainInfo.this ,"Failed to get , Check your network ! ",Toast.LENGTH_SHORT).show();
                           }
                       });
               MySingelton.getInstance(TrainInfo.this).addToRequestQue(jsonObjectRequest);
               adapter = new ArrayAdapter(TrainInfo.this, android.R.layout.simple_list_item_1, arrayList);

               listView.setAdapter(adapter);
           }
          }

      });







    }

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id2));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
              //  startActivity(new Intent(TrainInfo.this,FRONT.class));
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                startActivity(new Intent(TrainInfo.this,FRONT.class));
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

    public void onBackPressed() {
        super.onBackPressed();
        showInterstitial();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
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

}
