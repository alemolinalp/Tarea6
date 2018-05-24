package com.example.alexandramolina.tarea6;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ArrayList<Item> items = new ArrayList<>();
    final ArrayList<String> keyList = new ArrayList<>();

    ListView listView;
    itemAdapter adapter;
    DatabaseReference mDatabaseRef;
    private Button atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.lista);
        atras = findViewById(R.id.button7);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("items");

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ListActivity.this, InventarioActivity.class);
                ListActivity.this.startActivity(myIntent);
            }
        });

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Item item = postSnapshot.getValue(Item.class);
                    items.add(item);

                }
                adapter = new itemAdapter(ListActivity.this, R.layout.itemlistview, items);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseRef.getRoot().child("items")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot i : dataSnapshot.getChildren()) {
                            keyList.add(i.getKey());
                            //items.add(i.getValue(Item.class));
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;
                new AlertDialog.Builder(ListActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Está seguro?")
                        .setMessage("Desea eliminar el item?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                items.remove(position);
                                items.clear();
                                Toast.makeText(ListActivity.this, "Item eliminado", Toast.LENGTH_LONG).show();
                                adapter.notifyDataSetChanged();
                                mDatabaseRef.getRoot().child("items").child(keyList.get(position)).removeValue();
                                keyList.remove(position);
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();


                return true;
            }
        });

    }

}
