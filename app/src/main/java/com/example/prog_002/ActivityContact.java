package com.example.prog_002;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityContact extends AppCompatActivity implements View.OnClickListener {


    private ImageButton btnGalery;

    private final int Pick_image = 1;

    private EditText editName;
    private EditText editSurname;
    private EditText editFilial;
    private EditText editOtdel;
    private EditText editPost;
    private EditText editPhone;
    private EditText editMail;

    private static String selectedName;
    private static String selectedSurname;
    private static String selectedFilial;
    private static String selectedOtdel;
    private static String selectedPost;
    private static String selectedPhone;
    private static String selectedMail;
    private static String selectedImage;


    private String getSelectedName() {
        return selectedName;
    }

    protected void setSelectedName(final String selectedName) {
        this.selectedName = selectedName;
    }

    private String getSelectedSurname() {
        return selectedSurname;
    }

    protected void setSelectedSurname(final String selectedSurname) {
        this.selectedSurname = selectedSurname;
    }

    private String getSelectedFilial() {
        return selectedFilial;
    }

    protected void setSelectedFilial(String selectedFilial) {
        this.selectedFilial = selectedFilial;
    }

    private String getSelectedOtdel() {
        return selectedOtdel;
    }

    protected void setSelectedOtdel(String selectedOtdel) {
        this.selectedOtdel = selectedOtdel;
    }

    private String getSelectedPost() {
        return selectedPost;
    }

    protected void setSelectedPost(String selectedPost) {
        this.selectedPost = selectedPost;
    }

    private String getSelectedPhone() {
        return selectedPhone;
    }

    protected void setSelectedPhone(String selectedPhone) {
        this.selectedPhone = selectedPhone;
    }


    private String getSelectedMail() {
        return selectedMail;
    }

    protected void setSelectedMail(String selectedMail) { this.selectedMail = selectedMail; }

    private String getSelectedImage() {
        return selectedImage;
    }

    protected void setSelectedImage(String selectedImage) { this.selectedImage = selectedImage; }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        data();

        btnGalery = (ImageButton) findViewById(R.id.btnImageContact);
        btnGalery.setOnClickListener((View.OnClickListener) this);

    }

    void data(){
      //  seeImageIfRedact();
        editName = (EditText) findViewById(R.id.textName);
        editSurname = (EditText) findViewById(R.id.textSurname);
        editFilial = (EditText) findViewById(R.id.textFilial);
        editOtdel = (EditText) findViewById(R.id.textOtdel);
        editPost = (EditText) findViewById(R.id.textPost);
        editPhone = (EditText) findViewById(R.id.textPhone);
        editMail = (EditText) findViewById(R.id.textMail);
        editName.setText(getSelectedName());
        editSurname.setText(getSelectedSurname());
        editFilial.setText(getSelectedFilial());
        editOtdel.setText(getSelectedOtdel());
        editPost.setText(getSelectedPost());
        editPhone.setText(getSelectedPhone());
        editMail.setText(getSelectedMail());
    }

    void seeImageIfRedact() {
        if (getSelectedImage() == null) {
        return;
    } else
            btnGalery.setImageURI(Uri.parse(getSelectedImage()));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case Pick_image:
                if(resultCode == RESULT_OK){
                    try {
                        btnGalery.setImageURI(Uri.parse(getSelectedImage()));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnImageContact:

                break;
            default:
                break;
        }
    }
}
