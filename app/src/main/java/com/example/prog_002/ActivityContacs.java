package com.example.prog_002;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;

import static android.content.ContentValues.TAG;

public class ActivityContacs extends ListActivity implements AdapterView.OnItemLongClickListener, View.OnClickListener {

    Button btnAddContact;
    private ListView listView;
    private ArrayAdapter<Contacts> listViewAdapter;
    DBHelper dbHelper;
    SQLiteDatabase database;
    EditText textForSearch;
    int doubleBackToExitPressed = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacs);

        dbHelper = new DBHelper(this);

        database = dbHelper.getWritableDatabase();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnAddContact = (Button) findViewById(R.id.btnAddContact);
        btnAddContact.setOnClickListener((View.OnClickListener) this);

        textForSearch = (EditText) findViewById(R.id.textForSearch);

        worksWithSpinner();
    }



 void seeListContacts(boolean seeAllList, String column, String dataForSearch){
        if (seeAllList == true) {
            listView = (ListView) findViewById(android.R.id.list);

            List<Contacts> contactsList = new ArrayList<Contacts>();
            List<Contacts> list = dbHelper.getAllContacts();
            this.listViewAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, contactsList);
            contactsList.addAll(list);
            setListAdapter(listViewAdapter);
            getListView().setOnItemLongClickListener(this);
        }  else {
            listView = (ListView) findViewById(android.R.id.list);
            List<Contacts> contactsList = new ArrayList<Contacts>();
            List<Contacts> list = dbHelper.getAllContactsForResultSearch(column, dataForSearch);
            this.listViewAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, contactsList);
            contactsList.addAll(list);
            setListAdapter(listViewAdapter);
            getListView().setOnItemLongClickListener(this);
        }
 }

    String listenerTextForSearch(){
        try{

    textForSearch.addTextChangedListener(new TextWatcher() {

        public void afterTextChanged(Editable s) {
            textForSearch.getText().toString();
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) { textForSearch.getText().toString();}

        public void onTextChanged(CharSequence s, int start, int before, int count) {

             textForSearch.getText().toString();
        }
    });
    return textForSearch.getText().toString(); }
    catch(Exception ex){
        ex.printStackTrace();
        return null;
     }
}

    void worksWithSpinner() {
        final Spinner spinner = (Spinner ) findViewById(R.id.spinner);
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.categoryForSearch, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                String[] choose = getResources().getStringArray(R.array.categoryForSearch);
                handlerSpinner(selectedItemPosition, listenerTextForSearch());
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

   void handlerSpinner(int position, String textForSearch){
       switch (position) {
           case 0:
               Log.i(TAG, "позиция "  +position);
               this.textForSearch.setText("");
               seeListContacts(true, null, null);
               break;
           case 1:
               Log.i(TAG, "позиция "  +position);
               Log.i(TAG, "textForSearch "  +textForSearch);
               Log.i(TAG, "listenerTextForSearch "  +listenerTextForSearch());
               dbHelper.getAllContactsForResultSearch(dbHelper.KEY_NAME, listenerTextForSearch());
               seeListContacts(false, dbHelper.KEY_NAME, listenerTextForSearch());
               break;
           case 2:
               Log.i(TAG, "textForSearch "  +textForSearch);
               Log.i(TAG, "позиция "  +position);
               Log.i(TAG, "listenerTextForSearch "  +listenerTextForSearch());
               dbHelper.getAllContactsForResultSearch(dbHelper.KEY_SURNAME, listenerTextForSearch());
               seeListContacts(false, dbHelper.KEY_SURNAME, listenerTextForSearch());
               break;
           case 3:
               Log.i(TAG, "позиция "  +position);
               Log.i(TAG, "textForSearch "  +textForSearch);
               dbHelper.getAllContactsForResultSearch(dbHelper.KEY_FILIAl, listenerTextForSearch());
               seeListContacts(false, dbHelper.KEY_FILIAl, listenerTextForSearch());
               break;
           case 4:
               Log.i(TAG, "позиция "  +position);
               dbHelper.getAllContactsForResultSearch(dbHelper.KEY_OTDEL, listenerTextForSearch());
               seeListContacts(false, dbHelper.KEY_OTDEL, listenerTextForSearch());
               break;
           case 5:
               Log.i(TAG, "позиция "  +position);
               dbHelper.getAllContactsForResultSearch(dbHelper.KEY_POST, listenerTextForSearch());
               seeListContacts(false, dbHelper.KEY_POST, listenerTextForSearch());
               break;
           case 6:
               Log.i(TAG, "позиция "  +position);
               Log.i(TAG, "textForSearch "  +textForSearch);
               dbHelper.getAllContactsForResultSearch(dbHelper.KEY_PHONE, listenerTextForSearch());
               seeListContacts(false, dbHelper.KEY_PHONE, listenerTextForSearch());
               break;
           case 7:
               Log.i(TAG, "позиция "  +position);
               Log.i(TAG, "textForSearch "  +textForSearch);
               dbHelper.getAllContactsForResultSearch(dbHelper.KEY_MAIL, listenerTextForSearch());
               seeListContacts(false, dbHelper.KEY_MAIL, listenerTextForSearch());
               break;
           default:
               break;
       }
    }

            void delete(final AdapterView<?> parent, final int position){
                Log.i(TAG, "delete ... ");
                try (Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);
                ) {
                    if (cursor.moveToFirst()) {
                        int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                        int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                        int surnameIndex = cursor.getColumnIndex(DBHelper.KEY_SURNAME);
                        int filialIndex = cursor.getColumnIndex(DBHelper.KEY_FILIAl);
                        int otdelIndex = cursor.getColumnIndex(DBHelper.KEY_OTDEL);
                        int otdelPost = cursor.getColumnIndex(DBHelper.KEY_POST);
                        int phoneIndex = cursor.getColumnIndex(DBHelper.KEY_PHONE);
                        int mailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);
                        int otdelImage = cursor.getColumnIndex(DBHelper.KEY_IMAGE);
                        do {
                            Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                    ", name = " + cursor.getString(nameIndex) +
                                    ", surname = " + cursor.getString(surnameIndex) +
                                    ", filial = " + cursor.getString(filialIndex) +
                                    ", otdel = " + cursor.getString(otdelIndex) +
                                    ", post = " + cursor.getString(otdelPost) +
                                    ", phone = " + cursor.getString(phoneIndex) +
                                    ", mail = " + cursor.getString(mailIndex) +
                                    ", image  = " + cursor.getString(otdelImage));
                        } while (cursor.moveToNext());
                    } else
                        Log.d("mLog", "0 rows");

                    AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
                    builder.setTitle("Внимание");
                    builder.setMessage("Удалить контакт?");
                    builder.setPositiveButton("ДА", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Contacts selectedItem = (Contacts) parent.getItemAtPosition(position);
                            Log.i(TAG, "selectedItem ... " + selectedItem);
                            deleteFromDbHELPER(selectedItem);
                            Toast.makeText(getApplicationContext(),
                                    selectedItem + " удалён.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("НЕТ", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }

            private void deleteFromDbHELPER(Contacts contacts){
                DBHelper db = new DBHelper(this);
                db.deleteContact(contacts);
                this.listViewAdapter.remove(contacts);
                // Refresh ListView.
                this.listViewAdapter.notifyDataSetChanged();
            }


    private void openSeeContact(){
        Intent intentForAddContact = new Intent(this, ActivityContact.class);
        startActivity(intentForAddContact);

    }

    private void openRredactContact(){
        Intent intentForAddContact = new Intent(this, ActivityRedact.class);
        startActivity(intentForAddContact);
    }

void transferDataForRedact(ListView listView, View v, int position, long id){

    ActivityRedact activityRedact = new ActivityRedact();
    activityRedact.setRedact(true);
    Contacts selectedItemForUpdate = (Contacts) listView.getItemAtPosition(position);
    activityRedact.setSelectItem(selectedItemForUpdate);
    Log.i(TAG, "selectedItemForUpdate  "  +selectedItemForUpdate);

    Object selectedItem = listView.getItemAtPosition(position);
    dbHelper.getContact((Contacts)selectedItem);

    openRredactContact();
}

    @Override
    protected void onListItemClick(ListView listView, View v, final int position, long id) {
        Object selectedItem = listView.getItemAtPosition(position);
        dbHelper.getContact((Contacts)selectedItem);
        Log.i(TAG, "клик по  "  +selectedItem.toString());
        openSeeContact();
            }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressed == 2) {
            finishAffinity();
            System.exit(0);
        }
        else {
            doubleBackToExitPressed++;
            Toast.makeText(getApplicationContext(), "Нажмите ещё раз для выхода из приложения",
                    Toast.LENGTH_SHORT).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressed=1;
            }
        }, 2000);
    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> parent,final View v, final int position, final long id) {


        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popupmenu);
        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                onListItemClick(listView,v,position, id);
                                return true;
                            case R.id.menu2:
                                transferDataForRedact(listView,v,position, id);
                                return true;
                            case R.id.menu3:
                                delete(parent, position);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
        popupMenu.show();
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddContact:
                ActivityRedact activityRedact = new ActivityRedact();
                activityRedact.setRedact(false);
                openRredactContact();
                break;
            default:
                break;
        }
    }
}
