package com.example.prog_002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  implements OnClickListener {

    Button btnActTwo;
    Button btnActThree;
    Button btnActContact;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnActTwo = (Button) findViewById(R.id.btnActTwo);
        btnActTwo.setOnClickListener((OnClickListener) this);

        btnActThree = (Button) findViewById(R.id.btnActThree);
        btnActThree.setOnClickListener((OnClickListener) this);

        btnActContact = (Button) findViewById(R.id.btnActContact);
        btnActContact.setOnClickListener((OnClickListener) this);
    }

@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnActTwo:
                Intent intent1 = new Intent(this, ActivityRedact.class);
                startActivity(intent1);
                break;
            case R.id.btnActThree:
                Intent intent2 = new Intent(this, ActivityContacs.class);
                startActivity(intent2);
                break;
            case R.id.btnActContact:
                Intent intent3 = new Intent(this, ActivityContact.class);
                startActivity(intent3);
                break;
            default:
                break;
        }
    }
}
