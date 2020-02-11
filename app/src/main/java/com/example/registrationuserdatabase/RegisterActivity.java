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
import com.example.registrationuserdatabase.databinding.ActivityRegistrationBinding;

public class RegisterActivity extends AppCompatActivity {
    private DataBaseInstance dbi;
    private ActivityRegistrationBinding bindingRegister;
    private String userEmailTransferred;
    private String passwordForRegistration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingRegister = DataBindingUtil.setContentView(RegisterActivity.this, R.layout.activity_registration);
        dbi = DataBaseInstance.getInstance(RegisterActivity.this);
        passwordForRegistration = null;

        userEmailTransferred = getIntent().getStringExtra("userEmail");
        bindingRegister.textEmailRegister.setText(userEmailTransferred);

        bindingRegister.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bindingRegister.passRegister.getText().toString()
                        .equals(bindingRegister.passConfirm.getText().toString())){
                    passwordForRegistration = bindingRegister.passConfirm.getText().toString();
                    User user = new User();
                    user.setUserEmail(userEmailTransferred);
                    user.setUserPassword(passwordForRegistration);
                    dbi.insertSingleAsync(user);
                    Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(mainIntent);
                }else{
                    bindingRegister.passConfirm.setBackgroundColor(Color.RED);
                    Toast.makeText(RegisterActivity.this,
                            "Password and Confirm Password must be the same", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
