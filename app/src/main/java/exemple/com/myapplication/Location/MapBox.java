package exemple.com.myapplication.Location;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.util.List;

import exemple.com.myapplication.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapBox extends AppCompatActivity implements OnMapReadyCallback, LocationEngineListener, PermissionsListener , MapboxMap.OnMapClickListener {
    MapView mapView;

    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationLayerPlugin locationLayerPlugin;
    private Location orLocation;
    private MapboxMap map;
    MarkerOptions options , options1 , options2 ;
    private Point  originPosition ;
    private Point  destinaionPosition ;
    private  Marker destinationMarker;
    private NavigationMapRoute navigationMapRoute ;
    private static  final String Tag = "MapBox";

    NavigationLauncherOptions navigationLauncherOptions ;

    Button go;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoibWFyd2VuMTk5NCIsImEiOiJjampzaGg4anUwOWJnM3ZtODFxbjhvOXRvIn0.CAZB_p5cZhnO43gmICIHBw");
        setContentView(R.layout.activity_map_box);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ///////////////////////////******************* voice controle *********************////////////

/*

        android.content.Context context = this.getApplicationContext();
        SinchClient sinchClient = Sinch.getSinchClientBuilder().context(context)
                .applicationKey("ebf33ca8-ad1d-4d10-9d3a-5599a7d48dd6")
                .applicationSecret("FN9brKQF+UyBjf1hcuuXgA==")
                .environmentHost("clientapi.sinch.com")
                .userId("108013")
                .build();

*/

        ////////////////////////******************* end voice controle **************************/////////////////////

        go = findViewById(R.id.Start);
        mapView = findViewById(R.id.box);
        mapView.setStyleUrl(Style.MAPBOX_STREETS);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
               options    = new MarkerOptions();
                options.setTitle("maw and ramram position");
                options.setPosition(new LatLng(36.862499, 10.195556));

                options1    = new MarkerOptions();
                options1.setTitle("La marssa");
                options1.setPosition(new LatLng(36.8891, 10.3223));

                options2    = new MarkerOptions();
                options2.setTitle("Capitale");
                options2.setPosition(new LatLng(36.8891, 10.3223));


                mapboxMap.addMarker(options);
                mapboxMap.addMarker(options1);
                mapboxMap.addMarker(options2);
            }
        });
        mapView.getMapAsync(this);


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                originPosition = Point.fromLngLat(orLocation.getLongitude(),orLocation.getLatitude());
                double y = orLocation.getLongitude();
                double x = orLocation.getAltitude() ;

                Toast.makeText(MapBox.this,"Longitude"+""+String.valueOf(y), Toast.LENGTH_SHORT).show();
                Toast.makeText(MapBox.this,"Latitude"+""+String.valueOf(x), Toast.LENGTH_SHORT).show();
              //////////////////////****************** call nuber **********************///////////
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+21621558740"));

                if (ActivityCompat.checkSelfPermission(MapBox.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MapBox.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            10);
                    return;
                }else {
                    try{
                        startActivity(callIntent);
                    }
                    catch (android.content.ActivityNotFoundException ex){
                        Toast.makeText(getApplicationContext(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });





    }

    @Override
    @SuppressWarnings("MissingPermation")
    public void onConnected() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationEngine.requestLocationUpdates();

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {

            orLocation = location;
            setCameraPosition(location);
        }

    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {

            enableLocation();
        }
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {

        map = mapboxMap;
        map.addOnMapClickListener(this);
        enableLocation();

    }

    private void enableLocation() {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            initializeLocationEngine();
            initializeLocationLayer();

        } else {

            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);

        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @SuppressWarnings("MissingPermation")
    private void initializeLocationEngine() {
        locationEngine = new LocationEngineProvider(this).obtainBestLocationEngineAvailable();
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = locationEngine.getLastLocation();
        if (location != null) {

            orLocation = location;
            setCameraPosition(location);
        } else {

            locationEngine.addLocationEngineListener(this);
        }


    }

    @SuppressWarnings("MissingPermation")
    private void initializeLocationLayer() {

        locationLayerPlugin = new LocationLayerPlugin(mapView, map, locationEngine);
        locationLayerPlugin.setLocationLayerEnabled(true);
        locationLayerPlugin.setCameraMode(CameraMode.TRACKING);
        locationLayerPlugin.setRenderMode(RenderMode.NORMAL);


    }

    private void setCameraPosition(Location location) {

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13.0));

    }

    @Override
    public void onMapClick(@NonNull LatLng point) {
        if (destinationMarker!=null){


            map.removeMarker(destinationMarker);
        }


        destinationMarker = map.addMarker(new MarkerOptions().position(point));
        destinaionPosition = Point.fromLngLat(point.getLongitude(),point.getLatitude());
        originPosition = Point.fromLngLat(orLocation.getLongitude(),orLocation.getLatitude());

        getRoute(originPosition,destinaionPosition);
        go.setEnabled(true);
        go.setBackgroundResource(R.color.mapbox_blue);

    }
    /////////////////**********  navigation route **************/////////////////
    private void getRoute (Point origion , Point destination ) {

        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(origion)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        if (response.body() == null) {

                            Toast.makeText(MapBox.this, "No route found ", Toast.LENGTH_SHORT).show();
                            return;

                        }
                        else if (response.body().routes().size()==0){

                            Toast.makeText(MapBox.this, "No route found ", Toast.LENGTH_SHORT).show();
                            return;

                        }
                        DirectionsRoute currentRoute = response.body().routes().get(0);

                        if (navigationMapRoute != null){
                            navigationMapRoute.removeRoute();
                        }
                        else {
                            navigationMapRoute = new NavigationMapRoute(null,mapView,map);

                        }

                        navigationMapRoute.addRoute(currentRoute);


                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                        Log.e(Tag ,"errooor") ;
                    }
                });



    }

    /////////////////*** end navigation *****/////////////////////





    @Override
    @SuppressWarnings("MissingPermation")
    public void onStart() {
        super.onStart();

        if (locationEngine != null) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            locationEngine.requestLocationUpdates();
        }
        if(locationLayerPlugin!=null){

            locationLayerPlugin.onStart();
        }
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(locationEngine!=null){

            locationEngine.removeLocationUpdates();
        }
        if(locationLayerPlugin!=null){

            locationLayerPlugin.onStop();
        }

        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(locationEngine!=null){

            locationEngine.deactivate();
        }
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }



}
