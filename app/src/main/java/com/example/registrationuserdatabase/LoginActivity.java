package com.example.registrationuserdatabase;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.registrationuserdatabase.data.local.DataBaseInstance;
import com.example.registrationuserdatabase.data.local.User;
import com.example.registrationuserdatabase.databinding.ActivityFrontBinding;

import java.util.List;

public class LoginActivity extends AppCompatActivity {


    private ActivityFrontBinding bindingLogin;
    private DataBaseInstance dbi;
    private boolean isExisting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingLogin = DataBindingUtil.setContentView(this, R.layout.activity_front);

        dbi = DataBaseInstance.getInstance(this);
        User defaultUser = new User();
        defaultUser.setUserEmail("dukenukem@gmail.com");
        defaultUser.setUserPassword("12345");
        dbi.insertSingleAsync(defaultUser);
        bindingLogin.textEmailInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindingLogin.textEmailInput.setBackgroundResource(R.drawable.gradient_front);
            }
        });
        bindingLogin.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailFieldText = bindingLogin.textEmailInput.getText().toString();
                final String passwordFieldText = bindingLogin.passInput.getText().toString();
                dbi.getAll(new DataBaseInstance.DataBaseListener<List<User>>() {
                    @Override
                    public void onDataReceived(List<User> data) {
                        isExisting = false;
                        User user = new User();
                        for (User userTemp : data) {
                            if (userTemp.getUserEmail().equals(emailFieldText)){
                                isExisting = true;
                                user = userTemp;
                                break;
                            }
                        }
                        if (isExisting){
                            if (user.getUserPassword().equals(passwordFieldText)){
                                Intent loginIntent = new Intent(LoginActivity.this,MainActivity.class);
                                loginIntent.putExtra("userEmail",user.getUserEmail());
                                startActivity(loginIntent);
                            }else {
               Toast.makeText(LoginActivity.this,"Wrong password!",Toast.LENGTH_LONG).show();
                                bindingLogin.passInput.setBackgroundColor(Color.RED);
                                bindingLogin.passInput.setTextColor(Color.WHITE);
               bindingLogin.createAccount.setBackgroundColor(Color.RED);
               bindingLogin.createAccount.setTextColor(Color.WHITE);
                            }
                        }else{
                Toast.makeText(LoginActivity.this,"No such username in Database",Toast.LENGTH_LONG).show();
                bindingLogin.textEmailInput.setBackgroundColor(Color.RED);
                bindingLogin.textEmailInput.setTextColor(Color.WHITE);
                        }
                    }
                });

            }
        });
        bindingLogin.createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                loginIntent.putExtra("userEmail",bindingLogin.textEmailInput.getText().toString());
                startActivity(loginIntent);
            }
        });
    }

}
