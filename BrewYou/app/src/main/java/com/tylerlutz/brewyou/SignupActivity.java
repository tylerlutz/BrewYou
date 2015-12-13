package com.tylerlutz.brewyou;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by Tyler on 12/6/15.
 */
public class SignupActivity extends AppCompatActivity {
    private EditText userName;
    private EditText password;
    private EditText passwordMatch;
    Button btnReg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userName = (EditText) findViewById(R.id.txtRegisterUserName);
        password = (EditText) findViewById(R.id.txtRegisterPassword);
        passwordMatch = (EditText) findViewById(R.id.txtRegisterPasswordMatch);
        btnReg = (Button) findViewById(R.id.btnRegister);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validationError = false;
                StringBuilder validationErrorMessage = new StringBuilder("Please ");
                if(isEmpty(userName)){
                    validationError = true;
                    validationErrorMessage.append("enter a email address");
                }
                if(isEmpty(password)){
                    if(validationError){
                        validationErrorMessage.append(", and");
                    }
                    validationError = true;
                    validationErrorMessage.append(" enter a password");
                }
                if(isMatching(password,passwordMatch)){
                    if(validationError){
                        validationErrorMessage.append(", and");
                    }
                    validationError = true;
                    validationErrorMessage.append(" enter the same password");
                }
                validationErrorMessage.append(".");

                if(validationError){
                    Toast.makeText(SignupActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                final ProgressDialog dlg = new ProgressDialog(SignupActivity.this);
                dlg.setTitle("Please wait");
                dlg.setMessage("Signing up. Please wait.");
                dlg.show();

                ParseUser user = new ParseUser();
                user.setUsername(userName.getText().toString());
                user.setEmail(userName.getText().toString());
                user.setPassword(password.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        dlg.dismiss();
                        if(e != null){
                            Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }else{
                            Intent intent = new Intent(SignupActivity.this, RestaurantListActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    private boolean isEmpty(EditText text){
        if(text.getText().toString().trim().length()> 0){
            return false;
        }else{
            return true;
        }
    }

    private boolean isMatching(EditText text1, EditText text2){
        if(text1.getText().toString().equals(text2.getText().toString())){
            return false;
        }else{
            return true;
        }
    }
}
