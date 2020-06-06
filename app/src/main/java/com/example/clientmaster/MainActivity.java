package com.example.clientmaster;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddClientDialogListener, EditClientDialogListener {

    final static private String TAG = "MainActivity";
    private String currentClient = "";
    private DataBase db;
    private AutoCompleteTextView editText;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = loadStore();
        if (db == null) {
            String line1 = "";
            try {
                ArrayList<List<String>> dataList = new ArrayList<>();
                InputStream is = getResources().openRawResource(R.raw.dataa);
                BufferedReader buff = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

                while ((line1 = buff.readLine()) != null) {

                    String[] data = line1.split(",");
                    if (data.length > 0) {
                        db.addNewClient(data);
                    } else {
                        break;
                    }

                }

            } catch (IOException e) {
                Log.wtf(TAG, "Error reading in line " + line1, e);
                e.printStackTrace();
            }
        }
        editText = findViewById(R.id.actv);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, db.getClientNames());
        editText.setAdapter(adapter);
        Log.d(TAG, "onCreate: finished");

    }

    public void searchClient(View view) {

        Log.d(TAG, "searchClient: called");

        currentClient = editText.getText().toString();
        try {
            Client client = db.getClient(currentClient);
            ClientInfoDialog dialog = new ClientInfoDialog();
            dialog.setText("Nombre: " + client.getName() + "\n"
                    + "Nit: " + client.getNit() + " " + client.getSecurityNit() + "\n"
                    + "Dirección: " + client.getAddress() + "\n"
                    + "Teléfono: " + client.getPhone() + "\n"
                    + "Ciudad: " + client.getCity() + "\n"
                    + "Departamento: " + client.getDepartment() + "\n"
                    + "Contacto: " + client.getContact() + "\n"
                    + "Instrucción especial: " + client.getComment());
            dialog.show(getSupportFragmentManager(), "infoCliente");
        } catch (ClientNotFoundException e) {
            Context context = getApplicationContext();
            CharSequence text = "No existe un cliente con ese nombre";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
        }
        Log.d(TAG, "searchClient: finished");

    }

    public void addClientBtnClicked(View view) {

        Log.d(TAG, "addClientBtnClicked: called");
        DialogFragment dialog = new AddClientDialog();
        dialog.show(getSupportFragmentManager(), "AddClientDialogFragment");
    }

    public void editClientBtnClicked(View view) {

        Log.d(TAG, "editClientBtnClicked: called");
        DialogFragment dialog = new EditClientDialog();
        dialog.show(getSupportFragmentManager(), "EditClientDialogFragment");

    }

    public void deleteClientBtnClicked(View view) {

        Log.d(TAG, "deleteClientBtnClicked: called");
        currentClient = editText.getText().toString();
        try{
            db.getClient(currentClient);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Eliminar cliente").setMessage("¿Estás seguro de que quieres eliminar a "
                    + currentClient + "?");
            builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d(TAG, "deleteClientBtnClicked: positiveBtnClicked");
                    db.deleteClient(currentClient);
                    saveStore();
                }
            });
            builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    CharSequence text = "Operación cancelada";
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(getApplicationContext(), text, duration).show();
                    Log.e(TAG, "deleteClientBtnClicked: canceled");
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } catch (ClientNotFoundException e) {
            Log.e(TAG, "editClientBtnClicked: client with such name not found");
        }

    }

    @Override
    public void onAddClientDialogPositiveClick(DialogFragment dialog, Client c) {

        db.addNewClient(c);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, db.getClientNames());
        editText.setAdapter(adapter);
        saveStore();
        Log.d(TAG, "onAddClientDialogPositiveClick: client Added");
    }

    @Override
    public void onEditClientDialogPositiveClick(DialogFragment dialog, Client c) {

        Log.d(TAG, "onEditClientDialogPositiveClick: called");
        try {
            Client prevClient = db.getClient(currentClient);
            db.deleteClient(prevClient.getName());
            db.addNewClient(c);
            saveStore();
        } catch (ClientNotFoundException e) {
            Log.e(TAG, "editClientBtnClicked: client with such name not found");
        }

    }

    private void saveStore(){
        try{
            FileOutputStream fos = getApplicationContext().openFileOutput("store.data", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(db);
            oos.close();
            fos.close();
            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, db.getClientNames());
            editText.setAdapter(adapter);
            Log.d(TAG, "saveStore: saved db file");
        } catch (IOException e) {
            Log.e(TAG, "saveStore: error saving db");
        }
    }

    private DataBase loadStore(){
        Log.d(TAG, "loadStore: called");
        try {

            FileInputStream fis = getApplicationContext().openFileInput("store.data");
            ObjectInputStream ois = new ObjectInputStream(fis);
            DataBase db = (DataBase) ois.readObject();
            ois.close();
            fis.close();
            return db;
        } catch (Exception e) {
            Log.e(TAG, "loadStore: error loading, maybe no previous db created");
            return null;
        }
    }

    @Override
    public Client getClient() {
        Log.d(TAG, "getClient: called");
        currentClient = editText.getText().toString();
        try{
            return db.getClient(currentClient);
        } catch (ClientNotFoundException e){
            CharSequence text = "No existe un cliente con ese nombre";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(getApplicationContext(), text, duration).show();
            Log.e(TAG, "getClient: client with such name not found");
            return null;
        }

    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: called");
        super.onDestroy();
    }
}
