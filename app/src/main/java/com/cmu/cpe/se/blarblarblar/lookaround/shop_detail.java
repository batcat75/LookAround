package com.cmu.cpe.se.blarblarblar.lookaround;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class shop_detail extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    TextView textView1;
    GoogleApiClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);

        ImageButton back_button = (ImageButton)findViewById(R.id.imageBackButton3);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(getApplicationContext(), select_Shop.class);
                finish();
            }
        });

        textView1 = (TextView) findViewById(R.id.textViewLocation);

        boolean result = isServicesAvailable();

        result = isServicesAvailable();
        if (result) {
            mLocationClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        } else {
            finish();
        }

//        GoogleStaticMaps gsm = new GoogleStaticMaps();
//        gsm.setSize(800, 800);
//        gsm.addMarker(13.729526, 102.580533);
//        gsm.addMarker(23.749536, 104.590543);
//        gsm.addMarker(10.829546, 106.600553);
//        ImageView imageView1 = (ImageView)findViewById(R.id.imageViewStaticMap);
//        imageView1.setImageBitmap(gsm.getMapByMarker());
    }

    protected void onStart() {
        super.onStart();
        mLocationClient.connect();
    }

    protected void onStop() {
        super.onStop();
        mLocationClient.disconnect();
    }

    public boolean isServicesAvailable() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        return (resultCode == ConnectionResult.SUCCESS);
    }



    private LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            textView1.setText("Provider : " + location.getProvider() + "\n"
                    + "Latitude : " + location.getLatitude() + "\n"
                    + "Longitude : " + location.getLongitude() + "\n");
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shop_detail, menu);
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
    public void onConnected(Bundle bundle) {
//        Toast.makeText(shop_detail.this, "Services connected", Toast.LENGTH_SHORT).show();

        LocationRequest mRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000).setFastestInterval(1000);

        LocationServices.FusedLocationApi.getLastLocation(mLocationClient);
        LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient,mRequest,locationListener);
    }

    public void onDisconnected() {
        Toast.makeText(shop_detail.this, "Services disconnected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(shop_detail.this, "Services connection failed", Toast.LENGTH_SHORT).show();
    }
}
