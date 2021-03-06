package com.example.clientmaster;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddClientDialog extends DialogFragment{

    private final static String TAG = "AddClientDialog";

    private AddClientDialogListener listener;

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach: called");
        super.onAttach(context);
        try {
            listener = (AddClientDialogListener) context;
        } catch (ClassCastException e) {

        }
        Log.d(TAG, "onAttach: finished");

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Log.d(TAG, "onCreateDialog: called");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.client_form, null);
        builder.setView(view);
        builder.setPositiveButton(R.string.addClientBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = ((EditText) view.findViewById(R.id.clientName)).getText().toString();
                String nit = ((EditText) view.findViewById(R.id.clientNit)).getText().toString();
                String securityNit = ((EditText) view.findViewById(R.id.clientSecurityNit)).getText().toString();
                String address = ((EditText) view.findViewById(R.id.clientAddress)).getText().toString();
                String phone = ((EditText) view.findViewById(R.id.clientPhone)).getText().toString();
                String city = ((EditText) view.findViewById(R.id.clientCity)).getText().toString();
                String department = ((EditText) view.findViewById(R.id.clientDepartment)).getText().toString();
                String contact = ((EditText) view.findViewById(R.id.clientContact)).getText().toString();
                String comment = ((EditText) view.findViewById(R.id.clientComment)).getText().toString();
                Client c = new Client(name, nit, securityNit, address, phone, city, department, contact, comment);
                listener.onAddClientDialogPositiveClick(AddClientDialog.this, c);
                Toast.makeText(getContext(), "Cliente Agregado", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.cancelBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Operación cancelada", Toast.LENGTH_SHORT).show();
            }
        });
        Log.d(TAG,"onCreateDialog: finished");
        return builder.create();
    }
}

