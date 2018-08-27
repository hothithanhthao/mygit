package com.example.risa.showplaces;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONObject;
import android.os.AsyncTask;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONArray;
import android.util.Log;
import android.widget.Toast;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private JSONArray highscores;
    private  StringBuffer text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        FetchDataTask task = new FetchDataTask();
        task.execute("https://api.myjson.com/bins/12u9tq");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(5);
        CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(this);
        mMap.setInfoWindowAdapter(customInfoWindow);

    }
    class FetchDataTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... urls) {
            HttpURLConnection urlConnection = null;
            JSONObject json = null;
            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                json = new JSONObject(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
            }
            return json;
        }

        protected void onPostExecute(JSONObject json) {
            text = new StringBuffer("");
            double longi;
            double lati;
            float markerColor;
            try {
                // store highscores
                highscores = json.getJSONArray("courses");
                for (int i=0;i < highscores.length();i++) {
                    JSONObject hs = highscores.getJSONObject(i);
                    //text.append(hs.getString("name")+":"+hs.getString("score")+"\n");
                    longi = Double.parseDouble(hs.getString("Lat"));
                    lati = Double.parseDouble(hs.getString("Lng"));
                    LatLng Places = new LatLng(longi,lati);

                    //color
                    if(hs.getString("Type").equals("Gold")){
                        markerColor = BitmapDescriptorFactory.HUE_YELLOW;
                    }
                    else if(hs.getString("Type").equals("Front")){
                        markerColor = BitmapDescriptorFactory.HUE_AZURE;
                    }
                    else{
                        markerColor = BitmapDescriptorFactory.HUE_GREEN;
                    }

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(Places)
                            .title(hs.getString("course"))
                            .snippet(hs.getString("Web"))
                            .icon(BitmapDescriptorFactory.defaultMarker(markerColor));

                    InfoWindowData info = new InfoWindowData();
                    info.setHotel(hs.getString("address"));
                    info.setFood(hs.getString("phone"));
                    info.setTransport(hs.getString("Email"));

                    final Marker m = mMap.addMarker(markerOptions);
                    m.setTag(info);

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(Places));

                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(final Marker marker) {
                            if (marker.equals(m)) {
                                Toast.makeText(getApplicationContext(), "Marker = " + marker.getTitle(), Toast.LENGTH_SHORT).show();
                                return true;
                            }
                            return false;
                        }
                    });
                }
            } catch (JSONException e) {
                Log.e("JSON", "Error getting data.");
            }

        }
    }
}
