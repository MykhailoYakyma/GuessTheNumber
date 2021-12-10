package com.example.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "con.example.endivinanumero";
    private int intentos, number;
    private Intent intent;
    private Editable user_name;
    private File image = null;
    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         number = (int)(Math.random()*99+1);
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
                        intentos++;
                        historial.append("Numero Correcto!");
                        intent = new Intent(v.getContext(), RankingActivity.class);
                        showRanking(intent);
                        number = (int)(Math.random()*100+1);
                        historial.setText("");
                        atempts_number.setText("0");


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
                    Context context = getApplicationContext();
                    Toast.makeText(context,"No has introducido el numero",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showRanking(Intent intent) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

        dialog.setTitle("Player Name");
        dialog.setMessage("Write the nickname to be showed in the rancking:");

        final EditText dialog_input = new EditText(MainActivity.this);
        dialog_input.setWidth(10);
        dialog.setView(dialog_input);

        dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                 user_name = dialog_input.getText();
                if(user_name.toString().replace(" ", "").isEmpty()){

                    Toast.makeText(getApplicationContext(),"Nickname can not be empty",Toast.LENGTH_SHORT).show();
                    showRanking(intent);

                }else{
                    
                    try {
                        openCamera();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

        });

        dialog.show();

    }

    private void openCamera() throws IOException {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = createImageFile();
        Uri photoURI = FileProvider.getUriForFile(this, "com.mykhailo.android.fileprovider",  photoFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(takePictureIntent, 1);

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        }
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri fileUri = null;
        String message = String.valueOf(user_name.toString()+", "+intentos+", "+image.toString());
        intent.putExtra(EXTRA_MESSAGE, message);
        intentos = 0;

        startActivity(intent);
    }
}