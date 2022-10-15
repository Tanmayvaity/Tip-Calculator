package com.example.tipcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;

public class MapPage extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapPage";
    private GoogleMap mMap;
    private Marker marker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_page);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.i(TAG, "onMapReady: ");
        mMap = googleMap;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_options_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Enter place");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(TAG, "onQueryTextSubmit: ");
                locationSearch(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
//                Log.i(TAG, "onQueryTextChange: ");
                return false;
            }
        });


        return true;
    }

    private void locationSearch(String query) {
        List<Address> addressList = null;

        if (query != null && !query.trim().equals("")) {
            Geocoder geocoder = new Geocoder(MapPage.this);

            try {
                addressList = geocoder.getFromLocationName(query, 1);
            } catch (IOException e) {
                e.printStackTrace();

            }

            if ((addressList != null ? addressList.size() : 0) > 0) {
                Address address = addressList.get(0);

                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                if (marker != null) {
                    marker.remove();
                }

                marker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(query)
                );

                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            } else {
                Toast.makeText(MapPage.this, "invalid place", Toast.LENGTH_SHORT).show();
            }

        }
    }

}