package com.example.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int intentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int number = (int)(Math.random()*99+1);
        intentos = 0;

        final EditText user_number = (EditText) findViewById(R.id.user_number);
        final Button button = findViewById(R.id.check_button);
        final TextView historial = (TextView) findViewById(R.id.historial);
        final TextView atempts_number = (TextView) findViewById(R.id.Atempts_number);
        historial.setMovementMethod(new ScrollingMovementMethod());
        atempts_number.setText(String.valueOf(intentos));

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                try{
                    int number_user = Integer.parseInt(user_number.getText().toString());
                    user_number.getText().clear();

                    if (number==number_user){
                        historial.append("Numero Correcto!");

                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                        dialog.setTitle("Player Name");
                        dialog.setMessage("Write the nickname to be showed in the rancking:");

                        final EditText dialog_input = new EditText(MainActivity.this);
                        dialog_input.setWidth(10);
                        dialog.setView(dialog_input);

                        dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Editable user_name = dialog_input.getText();

                            }
                        });

                        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        });

                        dialog.show();
                        intentos = 0;

                    }else{
                        intentos++;
                        Log.d("Numero: ", String.valueOf(number));
                        atempts_number.setText(String.valueOf(intentos));
                        if (number_user>number){
                            historial.append("El numero es mas pequiño que "+ number_user + "\n");

                        }else{
                            historial.append("El numero es mas grande que "+ number_user + "\n");
                        }

                    }
                }catch(Exception E){
                    Log.i("siiiiiii","");
                    Context context = getApplicationContext();
                    Toast.makeText(context,"No has introducido el numero",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}