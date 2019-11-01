package com.example.prog_002;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.content.ContentValues.TAG;

public class ActivityRedact extends AppCompatActivity implements View.OnClickListener {

    private Button btnSave;

    private EditText editName;
    private EditText editSurname;
    private EditText editFilial;
    private EditText editOtdel;
    private EditText editPost;
    private EditText editPhone;
    private EditText editMail;

    private String strName;
    private String strSurname;
    private String strFilial;
    private String strOtdel;
    private String strPost;
    private String strPhone;
    private String strMail;
    private String strImage;

    public final int REQUEST_CODE_FOR_PERMISSIONS = 654;
    protected static boolean imageFromBd ;
    DBHelper dbHelper;
    private static Intent photoPickerIntent;
    private static boolean redact;

    private static Contacts selectItem;

    private static String selectedName;
    private static String selectedSurname;
    private static String selectedFilial;
    private static String selectedOtdel;
    private static String selectedPost;
    private static String selectedPhone;
    private static String selectedMail;
    private static String selectedImage;

    private ImageButton buttonImage;
    private Bitmap selectedImageFromBd;
    private Uri imageUri;
    static final int GALLERY_REQUEST = 1;

    public static Contacts getSelectItem() {
        Log.i(TAG, "getter selectItem  " + selectItem);
        return selectItem;
    }

    public static void setSelectItem(final Contacts selectItem) {
        Log.i(TAG, "setter selectItem  " + selectItem);
        ActivityRedact.selectItem = selectItem;
    }

    public boolean getRedact() {
        return redact;
    }

    public void setRedact(boolean redact) {
        this.redact = redact;
    }

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

    protected void setSelectedMail(String selectedMail) {
        this.selectedMail = selectedMail;
    }

    private String getSelectedImage() {
        return selectedImage;
    }

    protected void setSelectedImage(String selectedImage) {
        this.selectedImage = selectedImage;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redact);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener((View.OnClickListener) this);


        dbHelper = new DBHelper(this);

        buttonImage = (ImageButton) findViewById(R.id.btnImageRedact);
        buttonImage.setOnClickListener((View.OnClickListener) this);

 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
     if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
             ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
         ActivityCompat.requestPermissions(
                 this,
                 new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                         Manifest.permission.READ_EXTERNAL_STORAGE},
                 REQUEST_CODE_FOR_PERMISSIONS);
     }
 }
        redactOrAdd();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_FOR_PERMISSIONS) {
        }
    }

    void redactOrAdd() {
        if (redact == true) {
            seeImageIfRedact();
            textFromRedactActivity();
            editName.setText(getSelectedName());
            editSurname.setText(getSelectedSurname());
            editFilial.setText(getSelectedFilial());
            editOtdel.setText(getSelectedOtdel());
            editPost.setText(getSelectedPost());
            editPhone.setText(getSelectedPhone());
            editMail.setText(getSelectedMail());
        }
    }

    protected void textFromRedactActivity() {
        editName = (EditText) findViewById(R.id.textName);
        editSurname = (EditText) findViewById(R.id.textSurname);
        editFilial = (EditText) findViewById(R.id.textFilial);
        editOtdel = (EditText) findViewById(R.id.textOtdel);
        editPost = (EditText) findViewById(R.id.textPost);
        editPhone = (EditText) findViewById(R.id.textPhone);
        editMail = (EditText) findViewById(R.id.textMail);
        strName = editName.getText().toString();
        strSurname = editSurname.getText().toString();
        strFilial = editFilial.getText().toString();
        strOtdel = editOtdel.getText().toString();
        strPost = editPost.getText().toString();
        strPhone = editPhone.getText().toString();
        strMail = editMail.getText().toString();
    }

    void back() {
        Intent intentForBack = new Intent(this, ActivityContacs.class);
        startActivity(intentForBack);
    }

    void updateFromDbHELPER(Contacts contacts, String name, String surname, String filial, String otdel, String post, String phone, String mail, String image) {

        DBHelper db = new DBHelper(this);
        dbHelper.updateContact(getSelectItem(), strName, strSurname, strFilial, strOtdel, strPost, strPhone, strMail, strImage);
    }

    void seeImageIfRedact() {
        try {
        if (getSelectedImage() == null) {
            return;
        }
        if (redact == true && imageFromBd == true) {
            buttonImage.setImageURI(Uri.parse(getSelectedImage()));

        } else {
            return;
        } } catch(Exception ex){
            ex.printStackTrace();
            return;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    imageUri = imageReturnedIntent.getData();
                    this.strImage = imageUri.toString();
                    buttonImage.setImageURI(imageUri);
                }
                imageFromBd=true;
        }
          }

    @Override
    public void onClick(View v) {

        textFromRedactActivity();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();


        switch (v.getId()) {

            case R.id.btnImageRedact:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,  android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                break;
            case R.id.btnSave:
                if (editName.getText().toString().equals("") || editPhone.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Вы не заполнили обязательные поля",
                            Toast.LENGTH_SHORT).show();

                } else if (redact == false) {
                    contentValues.put(DBHelper.KEY_NAME, strName);
                    contentValues.put(DBHelper.KEY_SURNAME, strSurname);
                    contentValues.put(DBHelper.KEY_FILIAl, strFilial);
                    contentValues.put(DBHelper.KEY_OTDEL, strOtdel);
                    contentValues.put(DBHelper.KEY_POST, strPost);
                    contentValues.put(DBHelper.KEY_PHONE, strPhone);
                    contentValues.put(DBHelper.KEY_MAIL, strMail);
                    contentValues.put(DBHelper.KEY_IMAGE, strImage);


                    database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);

                    Toast.makeText(getApplicationContext(), "Сохранено",
                            Toast.LENGTH_SHORT).show();
                    back();


        } else if (redact == true) {

            this.strName = editName.getText().toString();
            this.strSurname = editSurname.getText().toString();
            this.strFilial = editFilial.getText().toString();
            this.strOtdel = editOtdel.getText().toString();
            this.strPost = editPost.getText().toString();
            this.strPhone = editPhone.getText().toString();
            this.strMail = editMail.getText().toString();



            updateFromDbHELPER(getSelectItem(), strName, strSurname, strFilial, strOtdel, strPost, strPhone, strMail, strImage);

            Toast.makeText(getApplicationContext(), "Изменения сохранены",
                    Toast.LENGTH_SHORT).show();
            back();

        }
                break;
            default:
                break;
        }
    }
}
