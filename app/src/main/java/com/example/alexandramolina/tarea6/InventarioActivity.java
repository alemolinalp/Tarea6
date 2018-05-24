package com.example.alexandramolina.tarea6;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;


public class InventarioActivity extends AppCompatActivity {

    private EditText nombre,precio,descripcion;
    private Button agregar, verlista;
    private ImageView foto;
    private TextView agregarImg;

    FirebaseDatabase database;
    DatabaseReference myRef;
    StorageReference mStorage;

    String id;

    private static final int PICK_IMAGE = 1;

    Uri imageUri;

    //ArrayList<Item> itemsInventario = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("items");
        mStorage = FirebaseStorage.getInstance().getReference();

        nombre = findViewById(R.id.editText);
        precio = findViewById(R.id.editText2);
        descripcion = findViewById(R.id.editText3);
        agregar = findViewById(R.id.button2);
        foto = findViewById(R.id.imageView);

        agregarImg = findViewById(R.id.edit_picture);
        verlista = findViewById(R.id.button4);


        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
                StorageReference filePath = mStorage.child("fotos").child(id);

                if(imageUri != null) {

                    filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(InventarioActivity.this, "Foto guardada", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });

        agregarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        verlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirListActivity();
            }
        });

    }

    private void addItem(){
        String name = nombre.getText().toString();
        String price = precio.getText().toString();
        String description = descripcion.getText().toString();

        //itemsInventario.add(new Item(name,price,description));

        if(!TextUtils.isEmpty(name)){
            id = myRef.push().getKey();

            Item item = new Item(name,price,description);

            myRef.child(id).setValue(item);

            Toast.makeText(this, "Item agregado", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            foto.setImageURI(imageUri);
       }
    }
    public void abrirListActivity(){
        Intent myIntent = new Intent(InventarioActivity.this, ListActivity.class);
        InventarioActivity.this.startActivity(myIntent);
    }
}
