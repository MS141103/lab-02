package com.example.cityviewer;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String>cityAdapter;
    ArrayList<String>dataList;
    Button btnAdd, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //intialise views
        cityList = findViewById(R.id. city_list);
        btnAdd = findViewById(R.id. btnAddCity);
        btnDelete = findViewById(R.id. btnDeleteCity);


        String[] cities = new String[] {"Edmonton", "Vancouver", "Calgary", "Berlin","Amsterdam", "Cairo", "London", "Tokyo", "Jakarta"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>( this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);
        //add city buttons
        btnAdd.setOnClickListener(vc ->{
            final EditText input = new EditText(this);
            new AlertDialog.Builder(this)
                    .setTitle("Add City")
                    .setMessage("Enter city name:")
                    .setView(input)
                    .setPositiveButton("Add", (dialog, which) ->{
                        String city = input.getText().toString().trim();
                        if (!city.isEmpty()){
                            dataList.add(city);
                            cityAdapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
        //Delete City Button
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if (dataList.isEmpty()){

                    Toast.makeText(MainActivity.this, "No cities to delete", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String[] items = dataList.toArray(new String[0]);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete City")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dataList.remove(which);
                                cityAdapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "Deleted: " + items[which], Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}