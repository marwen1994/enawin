package exemple.com.myapplication.LoginRegister;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import exemple.com.myapplication.R;

public class Splash extends AppCompatActivity {
    private static int SOLASH_SCREAN8TYME= 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i =new Intent(Splash.this , LoginActivity.class);
                startActivity(i);
                finish();
            }
        } , SOLASH_SCREAN8TYME);

    }
}
