package com.example.prog_002;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBHelper  extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "phonebook";
    public static final String TABLE_CONTACTS = "contacts";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_FILIAl = "filial";
    public static final String KEY_OTDEL = "otdel";
    public static final String KEY_POST = "post";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_MAIL = "mail";
    public static final String KEY_IMAGE = "uri_image";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID
                + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT,"  + KEY_SURNAME + " TEXT," + KEY_FILIAl + " TEXT,"
                + KEY_OTDEL + " TEXT," + KEY_POST + " TEXT," + KEY_PHONE + " TEXT,"  + KEY_MAIL + " TEXT,"  + KEY_IMAGE + " TEXT"  + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        onCreate(db);
    }


    public void getContact(Contacts contacts) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_SURNAME,KEY_FILIAl, KEY_OTDEL, KEY_POST, KEY_PHONE, KEY_MAIL, KEY_IMAGE }, KEY_ID + "=?",
                new String[] { String.valueOf(contacts.getId()) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Contacts contactsResult = new Contacts(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6),
                cursor.getString(7), cursor.getString(8));
        ActivityContact activityContact = new ActivityContact();
        activityContact.setSelectedName(cursor.getString(1));
        activityContact.setSelectedSurname(cursor.getString(2));
        activityContact.setSelectedFilial(cursor.getString(3));
        activityContact.setSelectedOtdel(cursor.getString(4));
        activityContact.setSelectedPost(cursor.getString(5));
        activityContact.setSelectedPhone(cursor.getString(6));
        activityContact.setSelectedMail(cursor.getString(7));
        activityContact.setSelectedImage(cursor.getString(8));

        ActivityRedact activityRedact = new ActivityRedact();
        activityRedact.setSelectedName(cursor.getString(1));
        activityRedact.setSelectedSurname(cursor.getString(2));
        activityRedact.setSelectedFilial(cursor.getString(3));
        activityRedact.setSelectedOtdel(cursor.getString(4));
        activityRedact.setSelectedPost(cursor.getString(5));
        activityRedact.setSelectedPhone(cursor.getString(6));
        activityRedact.setSelectedMail(cursor.getString(7));
        activityRedact.setSelectedImage(cursor.getString(8));
    }

    public List<Contacts> getAllContacts() {
        List<Contacts> contactsList = new ArrayList<Contacts>();

        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Contacts contacts = new Contacts();
                contacts.setId(Integer.parseInt(cursor.getString(0)));
                contacts.setName(cursor.getString(1));
                contacts.setSurname(cursor.getString(2));
                contacts.setFilial(cursor.getString(3));
                contacts.setOtdel(cursor.getString(4));
                contacts.setPost(cursor.getString(5));
                contacts.setPhone(cursor.getString(6));
                contacts.setMail(cursor.getString(7));
                contacts.setImage(cursor.getString(8));
                contactsList.add(contacts);

            } while (cursor.moveToNext());
        }
        Collections.sort(contactsList, Contacts.nameComparator);
        return contactsList;
    }

    public List<Contacts> getAllContactsForResultSearch(String column1, String dataForSeach) {
        List<Contacts> contactsList = new ArrayList<Contacts>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS + " WHERE " + column1 +  " LIKE " + "'%" + dataForSeach +"%' ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Contacts contacts = new Contacts();
                contacts.setId(Integer.parseInt(cursor.getString(0)));
                contacts.setName(cursor.getString(1));
                contacts.setSurname(cursor.getString(2));
                contacts.setFilial(cursor.getString(3));
                contacts.setOtdel(cursor.getString(4));
                contacts.setPost(cursor.getString(5));
                contacts.setPhone(cursor.getString(6));
                contacts.setMail(cursor.getString(7));
                contacts.setImage(cursor.getString(8));
                contactsList.add(contacts);

            } while (cursor.moveToNext());
        }
        Collections.sort(contactsList, Contacts.nameComparator);
        return contactsList;
    }

    public int updateContact(Contacts contacts, String name, String surname,String filial,String otdel,String post,
                             String phone,String mail, String image) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME ,name);
        values.put(KEY_SURNAME, surname);
        values.put(KEY_FILIAl,filial);
        values.put(KEY_OTDEL, otdel);
        values.put(KEY_POST, post);
        values.put(KEY_PHONE, phone);
        values.put(KEY_MAIL , mail);
        values.put(KEY_IMAGE , image);
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(contacts.getId())});
    }
    public void deleteContact(Contacts contacts) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contacts.getId()) });

        db.close();
    }

}
