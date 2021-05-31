package com.jayesh.tasks;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.jayesh.tasks.models.UserModel;
import com.jayesh.tasks.utils.TaskDbHelper;

public class MainActivity extends AppCompatActivity {
    private UserModel user;
    private TaskDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new TaskDbHelper(this);
        user = new UserModel(dbHelper);
        login();
    }

    private void login(){
        final EditText loginEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Enter User Name")
                .setView(loginEditText)
                .setPositiveButton("Login", (dialog1, which) -> {
                    String userName = String.valueOf(loginEditText.getText());
                    user.loginUser(userName);
                })
                .setCancelable(false)
                .create();
        dialog.show();
    }
}