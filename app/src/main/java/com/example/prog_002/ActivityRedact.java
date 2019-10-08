package com.example.prog_002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityRedact extends AppCompatActivity implements View.OnClickListener{

    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redact);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                Intent intentForBack = new Intent(this, ActivityContacs.class);
                startActivity(intentForBack);
                break;

            default:
                break;
        }
    }
}
