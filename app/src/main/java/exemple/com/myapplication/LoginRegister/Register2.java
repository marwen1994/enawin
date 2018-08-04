package exemple.com.myapplication.LoginRegister;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.codemybrainsout.placesearch.PlaceSearchDialog;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;


import exemple.com.myapplication.R;

public class Register2 extends AppCompatActivity {
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1 ;
    Button button ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();
        button = findViewById(R.id.button);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
           //     Log.i(TAG, "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
              //  Log.i(TAG, "An error occurred: " + status);
            }
        });


  button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

          PlaceSearchDialog placeSearchDialog = new PlaceSearchDialog.Builder(Register2.this)
                 // .setHeaderImage(R.drawable.dialog_header)
                  .setHintText("Enter location name")
                //  .setHintTextColor(R.color.light_gray)
                  .setNegativeText("CANCEL")
                 // .setNegativeTextColor(R.color.gray)
                  .setPositiveText("ok")
                 // .setPositiveTextColor(R.color.red)
                  //.setLatLngBounds(BOUNDS)
                  .setLocationNameListener(new PlaceSearchDialog.LocationNameListener() {
                      @Override
                      public void locationName(String locationName) {
                          //set textview or edittext
                      }
                  })
                  .build();
          placeSearchDialog.show();


      }
  });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
               // Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
              //  Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
