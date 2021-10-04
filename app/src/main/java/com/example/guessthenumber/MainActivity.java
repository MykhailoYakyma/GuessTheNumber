package com.example.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int result;
        int attempts = 0;
        int random = (int)(Math.random() * 99 + 1);
        EditText user_data = findViewById(R.id.user_number);



        final Button button = findViewById(R.id.check_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int user_number = Integer.parseInt(user_data.getText().toString());

                Context context = getApplicationContext();
                CharSequence text = "EXPLOOOOOOOOSION!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }
}