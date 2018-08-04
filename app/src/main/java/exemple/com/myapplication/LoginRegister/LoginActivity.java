package exemple.com.myapplication.LoginRegister;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mapbox.mapboxsdk.Mapbox;

import exemple.com.myapplication.Location.MapBox;
import exemple.com.myapplication.R;

public class LoginActivity extends AppCompatActivity {
 TextView singUp ;
 Button login ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();

        singUp =  findViewById(R.id.sing);
        login = findViewById(R.id.log) ;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MapBox.class);
                startActivity(intent);
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
}
