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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class pnr_screen2 extends AppCompatActivity implements View.OnClickListener {
    // Remove the below line after defining your own ad unit ID.
    // id - railwayapiid6@gmail.com - t3rp4d1ug4
// 57bb3d20b2c4f37678dd18a2ff94bd05 developer.lk1994
// 92243deb92af26be2fbeac69d7f67565 lohit9098
    private TextView pnrnumber, TNo, Tname, BoardingStn, Destinationstn, DOJ, Class, NoOfPes;
    TextView ChartInfo;
    private Button button;

    private ListView listView;
//    private String pnrURl1 = "http://api.railwayapi.com/v2/pnr-status/pnr/";
//    private String pnrURL2 = "";
//    private String pnrURL3 = "/apikey/23mhv522od/";
    private String pnrURl1 = "https://indianrailapi.com/api/v2/PNRCheck/apikey/57bb3d20b2c4f37678dd18a2ff94bd05/PNRNumber/";
    private String pnrURL2 = "";
    private String pnrURL3 = "/Route/1/";
    private String pnrfullURL;
    private InterstitialAd mInterstitialAd;
    String msg;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
    ArrayAdapter adapter;
  private  ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnr_screen2);
        pnrnumber = findViewById(R.id.pnroutput);
        TNo = findViewById(R.id.trainno);
        Tname = findViewById(R.id.trainname);
        ChartInfo = findViewById(R.id.chartC);
        BoardingStn = findViewById(R.id.boardingplace);
        Destinationstn = findViewById(R.id.destinationplace);
        DOJ = findViewById(R.id.Dojtextbox);
        Class = findViewById(R.id.classtextview);
        NoOfPes = findViewById(R.id.NOP);
        button = findViewById(R.id.buttonfetchdata);

        Bundle bundle = getIntent().getExtras();

        listView = findViewById(R.id.listofpassengeer);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        msg = bundle.getString("key");
        pnrURL2 = msg;


        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
    //    Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();
        button.setOnClickListener(this);
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pnr_screen2, menu);
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
            pnrfullURL = pnrURl1 + pnrURL2 + pnrURL3;
            //Toast.makeText(resultScreen.this,"fine"+pnrfullURL,Toast.LENGTH_LONG).show();
            progressDialog = new ProgressDialog(pnr_screen2.this);

            progressDialog.setMessage("Fetching Your PNR Status");
            progressDialog.setTitle("Wait a second....");

            progressDialog.show();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    pnrfullURL, null,
                    new Response.Listener<JSONObject>() {

                        public void onResponse(JSONObject response) {
                               progressDialog.dismiss();
                            // fetching pnr no
                            try {
                                String s1 = response.getString("PnrNumber");
                                pnrnumber.setText(s1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            // fetching no passenger
//                            try {
//                                String s1 = response.getString("total_passengers");
//                                NoOfPes.setText("No. of passengers : " + s1);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }


                            // fetching train no and name
                            try {
                                String no = response.getString("TrainNumber");
                                String name = response.getString("TrainName");
                                // NoOfPes.setText("No. of passengers : "+s1);
                                TNo.setText(no);
                                Tname.setText(name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//                            try {
//                                JSONObject object = response.getJSONObject("train");
//                                String no = object.getString("number");
//                                String name = object.getString("name");
//                                TNo.setText(no);
//                                Tname.setText(name);
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
                            // fetching chart details
                            try {
                                String value = response.getString("ChatPrepared");
                                ChartInfo.setText("Chart Prepared: "+value);
//                                String value = response.getString("chart_prepared");
//                                Boolean param = Boolean.parseBoolean(value);
//                                if (param == true)
//                                    ChartInfo.setText("Chart Prepared");
//                                else
//                                    ChartInfo.setText("Chart Not Prepared");


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // fetching class
//                            try {
//                                String s1 = response.getString("class");
//                                Class.setText("Class : " + s1);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            // fetching date of journey
//
//                            try {
//                                String s1 = response.getString("doj");
//                                DOJ.setText("DOJ :" + s1);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
                            // fetching boarding station

                            try {
                                String s1 = response.getString("JourneyClass");
                                Class.setText("Class : "+s1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//                            try {
//                                JSONObject object = response.getJSONObject("boarding_point");
//                                String stncode = object.getString("code");
//                                BoardingStn.setText(stncode);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }

                            try {
                                String s1 = response.getString("JourneyDate");
                                DOJ.setText("DOJ :"+s1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                String stnbrd  = response.getString("From");
                                BoardingStn.setText(stnbrd);


                                String stndest = response.getString("To");
                                Destinationstn.setText(stndest);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // fetching destination station
//                            try {
//                                JSONObject object = response.getJSONObject("reservation_upto");
//                                String stncode = object.getString("code");
//                                Destinationstn.setText(stncode);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
                            // fetching current reservation status
                            try {
                                String s1 = response.getString("Passangers");
                                JSONArray A1 = new JSONArray(s1);
                                NoOfPes.setText("No. of passengers : "+A1.length());
                                for (int i = 0; i < A1.length(); i++) {
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    JSONObject object = A1.getJSONObject(i);
                                    String NO = object.getString("Passenger");
                                    String booking_Status = object.getString("BookingStatus");
                                    String Current_Status = object.getString("CurrentStatus");

                                    map.put("Previous Status : ", booking_Status);

                                    map.put("Current Status : ", Current_Status);

                                    arrayList.add(map);
                                }


                                listView.setAdapter(adapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }


                    }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                                      progressDialog.dismiss();
                    Toast.makeText(pnr_screen2.this, "Failed to get, Check Your Network!!", Toast.LENGTH_LONG).show();
                }
            }

            );

            MySingelton.getInstance(pnr_screen2.this).addToRequestQue(jsonObjectRequest);


        }
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
              //  startActivity(new Intent(pnr_screen2.this,FRONT.class));
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                startActivity(new Intent(pnr_screen2.this,FRONT.class));
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

}