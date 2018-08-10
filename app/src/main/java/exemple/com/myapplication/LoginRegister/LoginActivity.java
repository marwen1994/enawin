package exemple.com.myapplication.LoginRegister;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
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
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;
import com.mapbox.mapboxsdk.Mapbox;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.joinersa.oooalertdialog.Animation;
import br.com.joinersa.oooalertdialog.OnClickListener;
import br.com.joinersa.oooalertdialog.OoOAlertDialog;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;
import exemple.com.myapplication.Location.MapBox;
import exemple.com.myapplication.R;
import exemple.com.myapplication.testconnexion.AppStatus;

public class LoginActivity extends AppCompatActivity {
 TextView singUp ;
 EditText  email , pass ;
 Button login ;
 String url1 ="http://192.168.137.1/enawin/public/getUserByEmail";
    String url="http://192.168.137.1/enawin/public/checkUser" ;

        android.app.AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();
                dialog = new SpotsDialog(this);

        singUp =  findViewById(R.id.sing);
        login = findViewById(R.id.log) ;
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass)        ;
        activateconnexion(LoginActivity.this);
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }






        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String res = jsonObject.getString("result");

                            if (res.equals("true")) {
                                getall();
                                Intent intent = new Intent(LoginActivity.this , MapBox.class);
                                startActivity(intent);
                                dialog.dismiss();
                            } else {
                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Please Check Your credet.....")
                                        .setContentText("Check Your Login or password")
                                        .setConfirmText("ok")
                                        .show();
                                dialog.dismiss();


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Please Check Your Internet.....")
                                    .setContentText("Check Your Internet Connexion " )
                                    .setConfirmText("ok")
                                    .show();

                            dialog.dismiss();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("email", email.getText().toString().trim());
                        map.put("password", pass.getText().toString().trim());
                        return map;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                queue.add(request);










            }
        });


        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,Register.class);
                startActivity(intent);
            }
        });
    }





    public  void activateconnexion(Activity activity)
    {


        if (AppStatus.getInstance(this).isOnline()) {
            SuperActivityToast.create(LoginActivity.this, new Style(), Style.TYPE_BUTTON)
                    .setButtonText("Succes")
                    .setProgressBarColor(Color.WHITE)
                    .setText("Successfully Connected .....")
                    .setDuration(Style.DURATION_MEDIUM)
                    .setFrame(Style.FRAME_LOLLIPOP)
                    .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_GREEN))
                    .setAnimations(Style.ANIMATIONS_POP).show();

        }

        else {



        final AlertDialog.Builder builder =  new AlertDialog.Builder(activity);
        final String message = " open Connexion setting?";

        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                startActivity(new Intent(Settings.ACTION_SETTINGS));
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                Intent intent = new Intent(LoginActivity.this , LoginActivity.class);
                                startActivity(intent);

                            }
                        });
        builder.create().show();


    }}

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }



    public  void getall() {
        StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String id = jsonObject.getString("id");
                    String name = jsonObject.getString("name");
                    String email = jsonObject.getString("email");
                    String status = jsonObject.getString("status");
                    String num_tel = jsonObject.getString("num_tel");
                    String type	 = jsonObject.getString("type");
                    String sexe = jsonObject.getString("sexe");
                    new SessionManager(getApplication()).setUser(id,name,email,status,num_tel,type ,sexe);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("email", email.getText().toString().trim());
                //map.put("password", editText1.getText().toString().trim());
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        queue.add(request);
    }

}
