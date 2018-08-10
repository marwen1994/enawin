package exemple.com.myapplication.LoginRegister;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codemybrainsout.placesearch.PlaceSearchDialog;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.rilixtech.Country;
import com.rilixtech.CountryCodePicker;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;
import exemple.com.myapplication.R;

public class Register2 extends AppCompatActivity {

    Button button ,log;
    String name , email ,password , gender ;
    String countryCodeAndroid = "+";
    CountryCodePicker ccp;
    EditText phone ;
    String number ,type ;
    String url = "http://192.168.137.1/enawin/public/storeUser" ;
    android.app.AlertDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();
        button = findViewById(R.id.button);
        log = findViewById(R.id.log);
        ccp = findViewById(R.id.ccp);
        phone = findViewById(R.id.phone);
        dialog = new SpotsDialog(this);



        String[] listItems = getResources().getStringArray(R.array.shopping_item);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        name= bundle.getString("name");
        email= bundle.getString("email");
        password= bundle.getString("password");
        gender= bundle.getString("gender");
          ////////////////////******************************country code *////////////////
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country country) {
                countryCodeAndroid = countryCodeAndroid+ccp.getSelectedCountryCode();

                Toast.makeText(Register2.this, countryCodeAndroid, Toast.LENGTH_SHORT).show();
            }
        });



 ////////////////////******** select type with popup
        button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
         AlertDialog.Builder mBuilder = new AlertDialog.Builder(Register2.this);
        mBuilder.setTitle("Choose whitch one you are ");
        mBuilder.setIcon(R.drawable.user);
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                button.setText(listItems[i]);
                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();


    }
    });

        //////******************** end select type************//////////


        ///////////////////////////***************** button addd **************////////////////
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (phone.getText().equals("")){

                    new SweetAlertDialog(Register2.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Please Check Your credet.....")
                            .setContentText("Check Your phone")
                            .setConfirmText("ok")
                            .show();                }
             else   if (button.getText().equals("Choose")){
                    new SweetAlertDialog(Register2.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Please Check Your credet.....")
                            .setContentText("Check Your Usre Type " )
                            .setConfirmText("ok")
                            .show();

                }


                else {
                       number=  countryCodeAndroid+phone.getText().toString();
                       type = button.getText().toString();

                    dialog.show();

                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Boolean res = jsonObject.getBoolean("result");

                                if (res) {

                                    Intent intent = new Intent(Register2.this,LoginActivity.class);
                                    startActivity(intent);
                                    dialog.dismiss();

                                } else {
                                    new SweetAlertDialog(Register2.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Please Check Your credet.....")
                                            .setContentText("User email already exists " )
                                            .setConfirmText("ok")
                                            .show();
                                    dialog.dismiss();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                dialog.dismiss();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            new SweetAlertDialog(Register2.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Please Check Your Internet.....")
                                    .setContentText("Check Your Internet Connexion " )
                                    .setConfirmText("ok")
                                    .show();

                            dialog.dismiss();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<String, String>();


                            map.put("name",name.toString() );
                            map.put("email",email.toString());
                            map.put("password",password.toString() );
                            map.put("num_tel", number.toString());
                            map.put("type", type.toString());
                            map.put("sexe", gender.toString());
                            return map;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                    queue.add(request);












                }





            }
        });








 }


}
