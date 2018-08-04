package exemple.com.myapplication.LoginRegister;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jackandphantom.customtogglebutton.CustomToggle;



import cn.pedant.SweetAlert.SweetAlertDialog;
import exemple.com.myapplication.R;

public class Register extends AppCompatActivity  {
    EditText name , email , password , confirmPass;
    Button next ;
    CustomToggle gender ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);
        confirmPass = findViewById(R.id.confir);
        name = findViewById(R.id.confir);
        gender  = (CustomToggle)findViewById(R.id.gender);
        next = findViewById(R.id.log);


        /////////////////////*********************button action *******************///////////////////////
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals(""))
                {
                    new SweetAlertDialog(Register.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Please Check Your credet.....")
                            .setContentText("Check Your name")
                            .setConfirmText("ok")
                            .show();
                }

                else if(!email.getText().toString().contains("@")){
                    new SweetAlertDialog(Register.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Please Check Your credet.....")
                            .setContentText("Enter valid mail")
                            .setConfirmText("ok")
                            .show();
                }

                else if(password.getText().toString().equals("")&&password.getText().toString().equals("") ){
                        new SweetAlertDialog(Register.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Please Check Your credet.....")
                            .setContentText("Check Your Password")
                            .setConfirmText("ok")
                            .show();
                }

                else if (!confirmPass.getText().toString().equals(password.getText().toString())){
                    new SweetAlertDialog(Register.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Please Check Your credet.....")
                            .setContentText("Confirm Your Password")
                            .setConfirmText("ok")
                            .show();

                }

                else{
                    Intent intent = new Intent(Register.this,Register2.class);
                    startActivity(intent);}



            }


        });



    }
}
