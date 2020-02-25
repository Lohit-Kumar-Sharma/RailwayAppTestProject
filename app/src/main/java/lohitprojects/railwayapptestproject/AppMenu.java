package lohitprojects.railwayapptestproject;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppMenu extends AppCompatActivity implements View.OnClickListener {
    // Remove the below line after defining your own ad unit ID.
    @Override
    protected void onStart() {
        super.onStart();
        ConnectivityManager ConnectionManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()==true )
        {
           // Toast.makeText(AppMenu.this, "Internet Available ", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(AppMenu.this, "Network Not Available!! Turn on your Internet!!", Toast.LENGTH_LONG).show();

        }
    }public void onBackPressed() {

        AlertDialog.Builder alert = new AlertDialog.Builder(AppMenu.this);
        alert.setTitle("Rate Us To Improve:");
       // ImageView img =  findViewById(R.id.ImageView01);
        ///img.setImageResource(R.drawable.rateme);
      //  LayoutInflater factory = LayoutInflater.from(MainActivity.this);
        //  final View view = factory.inflate(R.layout.activity_main, null);
        //alert.setView(view);

       alert.setIcon(R.drawable.rateme);
        //alert.setView(img);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=lohitprojects.railwayapptestproject");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=lohitprojects.railwayapptestproject"
                                    + getPackageName())));
                }

                dialog.dismiss();
                finish();
            }
        });


        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        alert.create();
        alert.show();
    }








     Button liveTStatus , trainInfo , trainRoute , pnrStatus , cancelled_trains , rescheduled_trains , RateApp,book,shareIt;

TextView textViewT,textViewD ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_menu);
        String currentDate = DateFormat.getDateInstance().format(new Date());
        liveTStatus = (Button) findViewById(R.id.livetrainstatus);
        trainInfo = (Button) findViewById(R.id.trainInfo);
        trainRoute = (Button) findViewById(R.id.trainRoute);
        pnrStatus = (Button) findViewById(R.id.PnrStatusCheck);
        cancelled_trains = (Button) findViewById(R.id.CancelledtrainInfo);
        rescheduled_trains = (Button) findViewById(R.id.rescheduledtrains);
        RateApp = (Button) findViewById(R.id.rateapp);
        book = (Button) findViewById(R.id.booktrainticket);
        shareIt=findViewById(R.id.shareapp);
          textViewT = findViewById(R.id.time);
          textViewD = findViewById(R.id.date);
        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
               .build();
        adView.loadAd(adRequest);

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
//        Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();
         textViewD.setText(currentDate);
         liveTStatus.setOnClickListener(this);
         trainInfo.setOnClickListener(this);
         trainRoute.setOnClickListener(this);
         pnrStatus.setOnClickListener(this);
         cancelled_trains.setOnClickListener(this);
         rescheduled_trains.setOnClickListener(this);
         RateApp.setOnClickListener(this);
         book.setOnClickListener(this);
         shareIt.setOnClickListener(this);
        final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textViewT.setText(new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(new Date()));
                someHandler.postDelayed(this, 1000);
            }
        }, 10);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app_menu, menu);
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

     if(view==liveTStatus)
     {
         startActivity(new Intent(AppMenu.this,Live_Train_Status.class));

     }

     if(view==trainInfo)
     {
         startActivity(new Intent(AppMenu.this,TrainInfo.class));


     }
     if(view==pnrStatus)
     {
        // Intent intent = new Intent(Intent.ACTION_VIEW);
         //intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=lohitprojects.pnrstatus"));
         //startActivity(intent);
      startActivity(new Intent(AppMenu.this,pnrScreen1.class));

     }
     if(view==cancelled_trains)
     {
         startActivity(new Intent(AppMenu.this,cancelledtrainlist.class));
     }

     if(view==rescheduled_trains)
     {

         startActivity(new Intent(AppMenu.this,Rescheduled_Trains.class));
     }

       if(view==trainRoute)
       {

           startActivity(new Intent(AppMenu.this , Train_Route.class
           ));
       }
if(view==book) {

            startActivity(new Intent(AppMenu.this , BookTickets.class));
    }

    if(view ==RateApp)
    {
           startActivity(new Intent(AppMenu.this,FRONT.class));
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=lohitprojects.railwayapptestproject"));
//        startActivity(intent);




    }
    if(view==shareIt)
    {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "https://play.google.com/store/apps/details?id=lohitprojects.railwayapptestproject";
        String shareContent1="\n✦ Amaze Your Co-Passenger by telling them LIVE STATUS of your Train!\n";
        String shareContent2="✦ Get Live Train tracking Info \n✦ Your PNR Status \n✦ Train Routes \n✦ Book your Ticket \n✦ List of Cancelled Trains \n✦ List of Rescheduled Trains \n✦ Train Running Days \n✦ App Size(<4 MB !!)\n✦ Free App !";
        String shareContent3 = "\n✦ So, What you waiting for?    Download Now!!\n✦Indian Rail Train Info✦\n";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareContent1+shareContent2+shareContent3+shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }
    }
}
