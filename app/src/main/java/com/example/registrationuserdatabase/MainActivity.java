package com.example.registrationuserdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.room.Room;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.registrationuserdatabase.data.local.AppDatabase;
import com.example.registrationuserdatabase.data.local.DataBaseInstance;
import com.example.registrationuserdatabase.data.local.User;
import com.example.registrationuserdatabase.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private StringBuilder builder;
    private List<User> all;
    private DataBaseInstance dbi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        builder = new StringBuilder();
        dbi = DataBaseInstance.getInstance(MainActivity.this);

        dbi.getAll(new DataBaseInstance.DataBaseListener<List<User>>() {
            @Override
            public void onDataReceived(List<User> data) {
                binding.textView.setText(writeText(data));
            }
        });
        binding.buttonDELETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<User> refreshed = new ArrayList<>();
                dbi.getAll(new DataBaseInstance.DataBaseListener<List<User>>() {
                    @Override
                    public void onDataReceived(List<User> data) {
                        refreshed.addAll(data);
                    }
                });
                dbi.deleteAll(refreshed);
            }
        });
        binding.buttonREFRESH.setOnClickListener(new View.OnClickListener() {
            List<User> refreshed = new ArrayList<>();
            @Override
            public void onClick(View v) {
                  dbi.getAll(new DataBaseInstance.DataBaseListener<List<User>>() {
                      @Override
                      public void onDataReceived(List<User> data) {
                          refreshed = data;
                      }
                  });
//                binding.textView.setText("");
                binding.textView.setText(writeText(refreshed));
            }
        });
   }

    private String writeText(List<User> data) {
        builder.setLength(0);
        for (User user : data) {
         builder.append(user.toString());
         builder.append(System.lineSeparator());
        }
        return builder.toString();
    }
}
