package lohitprojects.railwayapptestproject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

public class Rescheduled_Trains extends AppCompatActivity implements View.OnClickListener {
    // Remove the below line after defining your own ad unit ID.
    // http://api.railwayapi.com/v2/rescheduled/date/18-10-2017/apikey/l3g6kb436k/
    // id -bhishamsharma1996@gmail.com
// id - railwayapiid1@gmail.com - zg9glav7mr


    EditText editText;
    Button button;
    TextView textView ;
    ListView listView;
    private String Url1 = "http://api.railwayapi.com/v2/rescheduled/date/";
    private String Url2 = "";
    private String Url3 = "/apikey/23mhv522od/";
    private String fullresdUrl;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
    ArrayAdapter adapter;
   private ProgressDialog progressDialog;
    String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescheduled__trains);
        editText = findViewById(R.id.todaysdate);
        button = findViewById(R.id.buttongetInfo);
        listView = findViewById(R.id.listscheduledTrains);
         textView = findViewById(R.id.txt);
        editText.setOnClickListener(this);
        button.setOnClickListener(this);
        editText.setText(date);
        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
 .build();
        adView.loadAd(adRequest);

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
 //       Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();
        editText.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                prevL = editText.getText().toString().length();
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


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rescheduled__trains, menu);
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
        if (view == editText) {
            Toast.makeText(this, "Enter today's date", Toast.LENGTH_SHORT).show();

        }
        if (view == button) {
            Url2 = editText.getText().toString().trim();
            if (TextUtils.isEmpty(Url2)) {
                editText.setError("Enter today's date");
                return;
            } else {

                fullresdUrl=Url1+Url2+Url3;
                progressDialog = new ProgressDialog(Rescheduled_Trains.this);

                progressDialog.setMessage("Fetching data ");
                progressDialog.setTitle("Wait a second....");

                progressDialog.show();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        fullresdUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                               progressDialog.dismiss();
                            // fetching rescheduled trains list
                                try {
                                      JSONArray A1 = response.getJSONArray("trains");
                                      int nooftrains = A1.length();
                                      String s = Integer.toString(nooftrains);
                                         textView.setText("Total trains rescheduled today : "+s);
                                    for (int i = 0; i < A1.length(); i++)
                                    {
                                        HashMap<String, String> map = new HashMap<String, String>();
                                        JSONObject object = A1.getJSONObject(i);
                                        //  JSONObject object1 = object.getJSONObject("source");
                                        String Tno = object.getString("number");
                                        String Tname = object.getString("name");
                                        String Tdiff = object.getString("time_diff");
                                       String Res_Time = object.getString("rescheduled_time");
                                       String Res_date = object.getString("rescheduled_date");
                                        // textView.setText(name);
                                        JSONObject from = object.getJSONObject("from_station");
                                        JSONObject to = object.getJSONObject("to_station");
                                        String Stncodefrom =from.getString("name");
                                        String Stncodeto =to.getString("name");

                                        map.put("Train_No. ",Tno);
                                        map.put("Train_Name ",Tname);
                                        map.put("Rescheduled_Time ",Res_Time);
                                        map.put("Rescheduled_Date ",Res_date);
                                        map.put("Time_Difference ",Tdiff);
                                        map.put("From ",Stncodefrom);
                                        map.put("To ",Stncodeto);

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
                                Toast.makeText(Rescheduled_Trains.this ,"Failed to get. Check Your Network !! ",Toast.LENGTH_SHORT).show();
                            }
                        });
                MySingelton.getInstance(Rescheduled_Trains.this).addToRequestQue(jsonObjectRequest);
                adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

                listView.setAdapter(adapter);
            }

                   }
        }
    }
