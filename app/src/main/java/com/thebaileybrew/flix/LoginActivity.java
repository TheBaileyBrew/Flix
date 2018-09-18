package com.thebaileybrew.flix;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private TextInputEditText passwordInputText;
    private TextInputEditText usernameInputText;
    private TextInputLayout passwordInputLayout;
    private TextInputLayout usernameInputLayout;
    private Boolean validUser;
    private Boolean validPass;
    private String usernameValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        passwordInputText = findViewById(R.id.password_edit_text);
        passwordInputLayout = findViewById(R.id.password_entry);
        usernameInputText = findViewById(R.id.username_edit_text);
        usernameInputLayout = findViewById(R.id.username_entry);


        MaterialButton loginButton = findViewById(R.id.login_button);
        MaterialButton registerButton = findViewById(R.id.register);
        MaterialButton skipLoginButton = findViewById(R.id.skip_login);
        //Set an error for password if less than 8 characters
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        skipLoginButton.setOnClickListener(this);

        // Clears the error after password has 8 characters.
        passwordInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(passwordInputText.getText().length() >= 8) {
                    passwordInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private boolean isUsernameValid(@Nullable Editable text) {
        //Future Improvement: Add further logic to define valid username -- Eventually this should validate against a DB of users
        return text != null && text.length() >=4;
    }

    private boolean isPasswordValid(@Nullable Editable text) {
        //Future Improvement: Add logic to validate password against DB of user/passwords
        return text != null && text.length() >= 8;
    }

    private void navigateToMovieActivity(@Nullable Boolean login) {

        Intent openMovieActivity = new Intent(LoginActivity.this, MovieActivity.class);
        if (login){
            openMovieActivity.putExtra("user",usernameValue);
        }
        startActivity(openMovieActivity);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_button:
                if(!isPasswordValid(passwordInputText.getText())) {
                    passwordInputLayout.setError(getString(R.string.password_too_short));
                    validPass = false;
                } else {
                    passwordInputLayout.setError(null); //Clear the error
                    validPass = true;
                }
                if (!isUsernameValid(usernameInputText.getText())) {
                    usernameInputLayout.setError(getString(R.string.username_too_short));
                    validUser = false;
                } else {
                    usernameInputLayout.setError(null);
                    validUser = true;
                }
                if (validUser && validPass) {
                    Toast.makeText(LoginActivity.this, "Logging In...", Toast.LENGTH_SHORT).show();
                    usernameValue = usernameInputText.getText().toString().trim();

                    navigateToMovieActivity(true);
                }
                break;
            case R.id.skip_login:
                navigateToMovieActivity(false);
                break;
            case R.id.register:
                //Next Step Add additional field parameters for new user creation
                //First Name, Last Name, Username/Email, Password, Confirm Password
                break;
        }

    }
}
