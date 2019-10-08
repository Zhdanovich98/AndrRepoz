package com.example.prog_002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityContact extends AppCompatActivity implements View.OnClickListener {

    Button btnRedact;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        btnRedact = (Button) findViewById(R.id.btnRedact);
        btnRedact.setOnClickListener((View.OnClickListener) this);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRedact:
                Intent intent1 = new Intent(this, ActivityRedact.class);
                startActivity(intent1);
                break;
            case R.id.btnBack:
                Intent intentForBack = new Intent(this, ActivityContacs.class);
                startActivity(intentForBack);
                break;
            default:
                break;
        }
    }
}
