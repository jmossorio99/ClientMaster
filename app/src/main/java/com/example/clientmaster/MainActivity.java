package com.example.clientmaster;

import android.content.Context;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddClientDialog.AddClientDialogListener {

    private String currentClient = "";
    private DataBase db;
    private AutoCompleteTextView editText;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DataBase();
        String line = "";
        try {
            ArrayList<List<String>> dataList = new ArrayList<>();
            InputStream is = getResources().openRawResource(R.raw.dataa);
            BufferedReader buff = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

            while ((line = buff.readLine()) != null) {

                String[] data = line.split(",");
                if (data.length > 0) {
                    db.addNewClient(data);
                } else {
                    break;
                }

            }

        } catch (IOException e) {
            Log.wtf("MainActivity", "Error reading in line " + line, e);
            e.printStackTrace();
        }
        editText = findViewById(R.id.actv);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, db.getClientNames());
        editText.setAdapter(adapter);

    }

    public void searchClient(View view) {

        currentClient = editText.getText().toString();
        try {
            String[] clientData = db.getClientData(currentClient);
            ClientInfoDialog dialog = new ClientInfoDialog();
            dialog.setText("Nombre: " + clientData[0] + "\n"
                    + "Nit: " + clientData[1] + " " + clientData[2] + "\n"
                    + "Dirección: " + clientData[3] + "\n"
                    + "Teléfono: " + clientData[4] + "\n"
                    + "Ciudad: " + clientData[5] + "\n"
                    + "Departamento: " + clientData[6] + "\n"
                    + "Contacto: " + clientData[7] + "\n"
                    + "Instrucción especial: " + clientData[8]);
            dialog.show(getSupportFragmentManager(), "infoCliente");
        } catch (ClientNotFoundException e) {
            Context context = getApplicationContext();
            CharSequence text = "No existe un cliente con ese nombre";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
        }


    }

    public void addClientBtnClicked(View view) {

        DialogFragment dialog = new AddClientDialog();
        dialog.show(getSupportFragmentManager(), "AddClientDialogFragment");

    }

    public void addClient() {


    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, Client c) {

        db.addNewClient(c);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, db.getClientNames());
        editText.setAdapter(adapter);
        InputStream is = getResources().openRawResource(R.raw.dataa);
        try {
            byte[] buffer = new byte[is.available()];
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
