package com.example.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RankingActivity extends AppCompatActivity {
    private File image = null;
    static private ArrayList<Field> fields = new ArrayList<Field>();
    private ArrayAdapter<Field> adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        final TextView message = (TextView) findViewById(R.id.message);
        String[] newField= new String[3];
        newField = getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE).split( ", ");

        fields.add(new Field(Integer.parseInt(newField[1]), newField[0],Uri.fromFile(new File(newField[2]))));
        Collections.sort(fields, new UserNameComparator());
        adapter = new ArrayAdapter<Field>( this, R.layout.list, fields )
        {
            @Override
            public View getView(int pos, View convertView, ViewGroup container)
            {
                if( convertView==null ) {
                    convertView = getLayoutInflater().inflate(R.layout.list, container, false);
                }
                ((TextView) convertView.findViewById(R.id.name)).setText(getItem(pos).name);
                ((TextView) convertView.findViewById(R.id.intents)).setText(Integer.toString(getItem(pos).intents));
                ((ImageView) convertView.findViewById(R.id.image)).setImageURI(getItem(pos).photo);

                return convertView;
            }
        };
        ListView rankin_list = (ListView) findViewById(R.id.list_ranking);
        rankin_list.setAdapter(adapter);
    }

    protected File getFile(){
        File path = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photo = new File(path,"imatge.jpg");
        return photo;
    }

    public class UserNameComparator implements Comparator<Field>
    {
        public int compare(Field left, Field right) {
            return left.intents - right.intents;
        }
    }

    class Field {
        public int intents;
        public String name;
        public Uri photo;

        public Field(int intents, String name, Uri url) {
            this.intents = intents;
            this.name = name;
            this.photo = url;
        }
    }
}