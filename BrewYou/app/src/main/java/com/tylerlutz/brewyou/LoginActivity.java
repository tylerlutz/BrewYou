package com.tylerlutz.brewyou;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tyler on 12/5/15.
 */
public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnFacebook;
    Button btnRegister;

    String name = null, email = null;

    ParseUser parseUser;

    private EditText userName;
    private EditText password;

    public static final List<String> mPermissions = new ArrayList<String>() {{
        add("public_profile");
        add("email");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText) findViewById(R.id.txtUserName);
        password = (EditText) findViewById(R.id.txtPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnSignup);
        btnFacebook = (Button) findViewById(R.id.btnFacebookLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validationError = false;
                StringBuilder validationErrorMessage = new StringBuilder("Please ");

                if (isEmpty(userName)) {
                    validationError = true;
                    validationErrorMessage.append("enter your email address");
                }
                if (isEmpty(password)) {
                    if (validationError) {
                        validationErrorMessage.append(", and");
                    }
                    validationError = true;
                    validationErrorMessage.append(" enter your password");
                }
                validationErrorMessage.append(".");

                if (validationError) {
                    Toast.makeText(LoginActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                final ProgressDialog dlg = new ProgressDialog(LoginActivity.this);
                dlg.setTitle("Please Wait");
                dlg.setMessage("Logging in. Please wait.");
                dlg.show();

                ParseUser.logInInBackground(userName.getText().toString(), password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        dlg.dismiss();
                        if (e != null) {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginActivity.this, mPermissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {

                        if (user == null) {
                            Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                        } else if (user.isNew()) {
                            Log.d("MyApp", "User signed up and logged in through Facebook!");
                            getUserDetailsFromFB();
                            saveNewUser();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        } else {
                            getUserDetailsFromFB();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            Log.d("MyApp", "User logged in through Facebook!");
                        }
                    }
                });

            }
        });
    }

    private void saveNewUser() {
        parseUser = ParseUser.getCurrentUser();
        parseUser.setUsername(email);
        parseUser.setEmail(email);

        //Finally save all the user details
        parseUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Toast.makeText(LoginActivity.this, "New user:" + name + " Signed up", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUserDetailsFromFB() {

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */
                        try {
                            email = response.getJSONObject().getString("email");
                            userName.setText(email);

                            saveNewUser();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isEmpty(EditText text){
        if(text.getText().toString().trim().length()> 0){
            return false;
        }else{
            return true;
        }
    }
}

