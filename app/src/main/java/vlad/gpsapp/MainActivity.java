package vlad.gpsapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import android.location.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;


public class MainActivity extends ActionBarActivity implements LocationListener {


    private Button refresh, send;
    private TextView lat, lon;



    LocationManager service;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refresh = (Button)findViewById( R.id.button2 );

        lat = (TextView)findViewById( R.id.textView3 );
        lon = (TextView)findViewById(R.id.textView4);

        service = (LocationManager)getSystemService(Context.LOCATION_SERVICE);


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);



                //service.requestSingleUpdate( provider, null );//.requestSingleUpdate( criteria, null );




                if( service.isProviderEnabled( LocationManager.NETWORK_PROVIDER ) ) {
                    Location location = service.getLastKnownLocation( LocationManager.NETWORK_PROVIDER );
                    if (location != null) {
                        lon.setText(Double.toString(location.getLatitude()));
                        lat.setText(Double.toString(location.getLongitude()));
                    } else {
                        lat.setText("Not ");
                        lon.setText("Not Available");
                    }
                }
                else{
                    lat.setText("No GPS ");
                    lon.setText("Not Available");
                }

            }




            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://localhost:8080/sep/track_ride/communication_module.php");


            try{
                post.setEntity( new UrlEncodedFormEntity(  ) );
                HttpResponse response = client.execute(post);
            }
            catch( Exception e1 ) {
                e1.printStackTrace();
            }





        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
