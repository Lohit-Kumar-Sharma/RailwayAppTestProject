package lohitprojects.railwayapptestproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class cancelledtrainlist extends AppCompatActivity implements View.OnClickListener {

    // http://api.railwayapi.com/v2/cancelled/date/<dd-mm-yyyy>/apikey/ggi8cg751m/

//  id - railwayapiid2 - t05q2l5k3n
   EditText editText ;
   Button button;
   TextView textView;
   ListView listView ;
    String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
String Url1="http://api.railwayapi.com/v2/cancelled/date/";
String Url2="";
String Url3="/apikey/23mhv522od/";
String fullUrl="";
    private static final String TrainName = "TName";
    private static final String TrainNumber = "TNumber";
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String,String>>();
    ArrayAdapter adapter;
   private ProgressDialog progressDialog;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_cancelledtrainlist);
        editText = findViewById(R.id.date);
        button = findViewById(R.id.buttongetInfo);
        listView = findViewById(R.id.listCancelledTrains);
textView = findViewById(R.id.txt);
           editText.setText(date);
           editText.setOnClickListener(this);
    button.setOnClickListener(this);

    AdView adView = (AdView) findViewById(R.id.adView);
    AdRequest adRequest = new AdRequest.Builder()
            .build();
    adView.loadAd(adRequest);
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
        getMenuInflater().inflate(R.menu.menu_cancelled_trains, menu);
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

    @Override
    public void onClick(View view) {

        if(view==editText)
        {
            Toast.makeText(this , "Enter today's date",Toast.LENGTH_SHORT).show();

        }
        if(view==button)
        {
            Url2 =editText.getText().toString().trim();
            if(TextUtils.isEmpty(Url2))
             {
                 editText.setError("Enter today's date");
                 return;
             }
             else
             {
                   fullUrl = Url1+Url2+Url3;
                 progressDialog = new ProgressDialog(cancelledtrainlist.this);

                 progressDialog.setMessage("Fetching details");
                 progressDialog.setTitle("Wait a second....");

                 progressDialog.show();

                 JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                         fullUrl, null,
                         new Response.Listener<JSONObject>() {
                             @Override
                             public void onResponse(JSONObject response) {
                                      progressDialog.dismiss();
                                 try {
                                     String s1 = response.getString("trains");
                                     JSONArray A = response.getJSONArray("trains");
                                     int n = A.length();
                                     String s= Integer.toString(n);
                                     textView.setText("Total trains cancelled today :"+s);
                                     JSONArray trainArray = new JSONArray(s1);
                                     for (int i = 0; i < trainArray.length(); i++)
                                     {
                                         HashMap<String, String> map = new HashMap<String, String>();
                                          JSONObject object = trainArray.getJSONObject(i);
                                        //  JSONObject object1 = object.getJSONObject("source");
                                          String name = object.getString("name");
                                          String number = object.getString("number");
                                         // textView.setText(name);
                                         map.put("Train name",name);

                                          map.put("Train number",number);

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
                                 Toast.makeText(cancelledtrainlist.this ,"Failed to get,Check Your Network !!",Toast.LENGTH_SHORT).show();
                             }
                         });
                 MySingelton.getInstance(cancelledtrainlist.this).addToRequestQue(jsonObjectRequest);
                 adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

                 listView.setAdapter(adapter);
             }
        }
    }
}
