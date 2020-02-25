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

import java.util.ArrayList;
import java.util.HashMap;

public class Train_Route extends AppCompatActivity {
    // Remove the below line after defining your own ad unit ID.
    // train route URL - http://api.railwayapi.com/v2/route/train/12920/apikey/l3g6kb436k/
    // id- chetram07@gmail.com
// lkstorageonline01 - 23mhv522od
    // id railwayapiid5@gmail.com - 8gynq0f6uu
private String Url1= "http://api.railwayapi.com/v2/route/train/";
private  String Url2="";
private String Url3 ="/apikey/23mhv522od/";
    private String fullURlTroutes ;
    private InterstitialAd mInterstitialAd;
    EditText editText ;
    Button button ;
    ListView  listView1;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String,String>>();
    ArrayAdapter adapter;
   TextView textView ,textView1,textView2;
private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train__route);

        editText = findViewById(R.id.trainNoforRoutes);
        button = findViewById(R.id.buttongetInfo);
      //  listView=findViewById(R.id.listroutes);
        listView1=findViewById(R.id.listroutes2);
  textView = findViewById(R.id.tnodis);
  textView1 = findViewById(R.id.tnadis);
  textView2 = findViewById(R.id.noOfstn);

        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
    //    Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();

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
                   fullURlTroutes = Url1+Url2+Url3 ;
                   progressDialog = new ProgressDialog(Train_Route.this);

                   progressDialog.setMessage("Fetching Train Route");
                   progressDialog.setTitle("Wait a second....");

                   progressDialog.show();
                   JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                           fullURlTroutes, null,
                           new Response.Listener<JSONObject>() {
                               @Override
                               public void onResponse(JSONObject response) {
                                   progressDialog.dismiss();
                       // fetching coach classes of train
                                   try {
                                       JSONObject ob = response.getJSONObject("train");
                                       String Tname = ob.getString("name");
                                       String Tno = ob.getString("number");
                                           textView.setText(Tno);
                                           textView1.setText(Tname);
                                   } catch (JSONException e) {

                                       e.printStackTrace();
                                   }
                                // fetching routes
                                   try {
                                       JSONArray A1 = response.getJSONArray("route");

                                       int i = A1.length();
                                        String s= Integer.toString(i);
                                       textView2.setText("Total Halts : "+s);


                                       for (int j = 0; j < A1.length(); j++)
                                       {
                                           HashMap<String, String> map = new HashMap<String, String>();
                                           JSONObject object = A1.getJSONObject(j);
                                           //  JSONObject object1 = object.getJSONObject("source");
                                           JSONObject obj1 = object.getJSONObject("station");
                                           String stnname = obj1.getString("name");
                                           String day = object.getString("day");
                                           String dis = object.getString("distance");
                                           String StnNo = object.getString("no");
                                           String Arrival = object.getString("scharr");
                                           String depp = object.getString("schdep");

                                           // textView.setText(name);
                                           map.put("STN_No ",StnNo);
                                           map.put("STN_Name ",stnname);
                                           map.put("Arr. ",Arrival);
                                           map.put("Dep. ",depp);
                                           map.put("Distance_Completed ",dis);
                                           map.put("Travel_Day ",day);

                                           arrayList.add(map);





                                   } }catch (JSONException e) {
                                       e.printStackTrace();
                                   }







                               }
                           },
                           new Response.ErrorListener() {

                               @Override
                               public void onErrorResponse(VolleyError error) {
                                   progressDialog.dismiss();
                                   Toast.makeText(Train_Route.this ,"Failed to get, Check Your Network !",Toast.LENGTH_SHORT).show();
                               }
                           });
                   MySingelton.getInstance(Train_Route.this).addToRequestQue(jsonObjectRequest);
                  adapter = new ArrayAdapter(Train_Route.this, android.R.layout.simple_list_item_1, arrayList);
                   listView1.setAdapter(adapter);
               }








               }




       });













    }
    public void onBackPressed() {
        super.onBackPressed();
        showInterstitial();
    }

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id1));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
           //     startActivity(new Intent(Train_Route.this,FRONT.class));
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                startActivity(new Intent(Train_Route.this,FRONT.class));
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_train__route, menu);
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
