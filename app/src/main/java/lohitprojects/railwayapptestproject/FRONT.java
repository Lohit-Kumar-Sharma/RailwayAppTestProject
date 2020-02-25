package lohitprojects.railwayapptestproject;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FRONT extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

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
            Toast.makeText(FRONT.this, "Network Not Available!! Turn on your Internet!!", Toast.LENGTH_LONG).show();
        }
    }public void onBackPressed() {

        AlertDialog.Builder alert = new AlertDialog.Builder(FRONT.this);
        alert.setTitle("Rate Our App To Improve:");
        alert.setMessage("It won't take more than 10seconds!!!");
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
                finishAffinity();
                finish();
                finishAndRemoveTask();

            }
        });
        alert.create();
        alert.show();
    }





    private ImageButton liveTStatus , trainInfo , trainRoute , pnrStatus , cancelled_trains , rescheduled_trains , book;

    private TextView textViewT,textViewD ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        String currentDate = DateFormat.getDateInstance().format(new Date());
        liveTStatus = (ImageButton) findViewById(R.id.livetrainstatus);
        trainInfo = (ImageButton) findViewById(R.id.trainInfo);
        trainRoute = (ImageButton) findViewById(R.id.trainRoute);
        pnrStatus = (ImageButton) findViewById(R.id.PnrStatusCheck);
        cancelled_trains = (ImageButton) findViewById(R.id.CancelledtrainInfo);
        rescheduled_trains = (ImageButton) findViewById(R.id.rescheduledtrains);
        book = (ImageButton) findViewById(R.id.booktrainticket);
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

        book.setOnClickListener(this);

        final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textViewT.setText(new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(new Date()));
                someHandler.postDelayed(this, 1000);
            }
        }, 10);





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "https://play.google.com/store/apps/details?id=lohitprojects.railwayapptestproject";
                String shareContent1="\n✦ Amaze Your Co-Passenger by telling them LIVE STATUS of your Train!\n";
                String shareContent2="✦ Get Live Train tracking Info \n✦ Your PNR Status \n✦ Train Routes \n✦ Book your Ticket \n✦ List of Cancelled Trains \n✦ List of Rescheduled Trains \n✦ Train Running Days \n✦ App Size(<4 MB !!)\n✦ Free App !";
                String shareContent3 = "\n✦ So, What are you waiting for?    Download Now!!\n✦Indian Rail Train Info✦\n";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareContent1+shareContent2+shareContent3+shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.front, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=lohitprojects.railwayapptestproject"));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "https://play.google.com/store/apps/details?id=lohitprojects.railwayapptestproject";
            String shareContent1="\n✦ Ama" +
                    "ze Your Co-Passenger by telling them LIVE STATUS of your Train!\n";
            String shareContent2="✦ Get Live Train tracking Info \n✦ Your PNR Status \n✦ Train Routes \n✦ Book your Ticket \n✦ List of Cancelled Trains \n✦ List of Rescheduled Trains \n✦ Train Running Days \n✦ App Size(<4 MB !!)\n✦ Free App !";
            String shareContent3 = "\n✦ So, What are you waiting for?    Download Now!!\n✦Indian Rail Train Info✦\n";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareContent1+shareContent2+shareContent3+shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        } else  if (id == R.id.nav_rate) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=lohitprojects.railwayapptestproject"));
            startActivity(intent);

        }else  if (id == R.id.more_apps) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Lohit+Kumar"));
            startActivity(intent);


        }
         else if(id==R.id.nav_aboutUs){
            startActivity(new Intent(FRONT.this,MainActivity.class));
        }

        else if(id==R.id.nav_CantactUs){
            startActivity(new Intent(FRONT.this,MainActivity.class));
        }
        else if(id==R.id.privacy)
        {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://sites.google.com/view/train-info-privacy-policy/home"));
            startActivity(intent);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {


        if(view==liveTStatus)
        {
            startActivity(new Intent(FRONT.this,Live_Train_Status.class));

        }

        if(view==trainInfo)
        {
            startActivity(new Intent(FRONT.this,TrainInfo.class));


        }
        if(view==pnrStatus)
        {
            // Intent intent = new Intent(Intent.ACTION_VIEW);
            //intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=lohitprojects.pnrstatus"));
            //startActivity(intent);
            startActivity(new Intent(FRONT.this,pnrScreen1.class));

        }
        if(view==cancelled_trains)
        {
            startActivity(new Intent(FRONT.this,cancelledtrainlist.class));
        }

        if(view==rescheduled_trains)
        {

            startActivity(new Intent(FRONT.this,Rescheduled_Trains.class));
        }

        if(view==trainRoute)
        {

            startActivity(new Intent(FRONT.this , Train_Route.class
            ));
        }
        if(view==book) {

            startActivity(new Intent(FRONT.this , BookTickets.class));
        }

    }
}
