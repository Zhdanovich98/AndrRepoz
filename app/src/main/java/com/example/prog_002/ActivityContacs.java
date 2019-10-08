package com.example.prog_002;

import android.app.ListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ActivityContacs extends ListActivity implements AdapterView.OnItemLongClickListener, View.OnClickListener {

    Button btnAddContact;

    final String[] catNamesArray = new String[]{"Рыжик", "Барсик", "Мурзик",
            "Мурка", "Васька", "Томасина", "Бобик", "Кристина", "Пушок",
            "Дымка", "Кузя", "Китти", "Барбос", "Масяня", "Симба"};

    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> catNamesList = new ArrayList<>(Arrays.asList(catNamesArray));


    void seeContact(){
        Intent intentForAddContact = new Intent(this, ActivityContact.class);
        startActivity(intentForAddContact);
    }

    void redactContact(){
        Intent intentForAddContact = new Intent(this, ActivityRedact.class);
        startActivity(intentForAddContact);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacs);

        btnAddContact = (Button) findViewById(R.id.btnAddContact);
        btnAddContact.setOnClickListener((View.OnClickListener) this);

        mAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, catNamesList);
        setListAdapter(mAdapter);
        getListView().setOnItemLongClickListener(this);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
         seeContact();
}

    @Override
    public boolean onItemLongClick(final AdapterView<?> parent, View v, final int position, long id) {


        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popupmenu);
        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                seeContact();
                                return true;
                            case R.id.menu2:
                                redactContact();
                                return true;
                            case R.id.menu3:
                                String selectedItem = parent.getItemAtPosition(position).toString();
                                mAdapter.remove(selectedItem);
                                mAdapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(),
                                selectedItem + " удалён.",
                                Toast.LENGTH_SHORT).show();
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
                redactContact();
                break;
            default:
                break;
        }
    }
}