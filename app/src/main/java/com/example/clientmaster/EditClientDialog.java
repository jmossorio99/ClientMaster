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

public class EditClientDialog extends DialogFragment {

    private final static String TAG = "EditClientDialog";
    private EditClientDialogListener listener;
    private Client client;

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach: called");
        super.onAttach(context);
        try {
            listener = (EditClientDialogListener) context;
            client = listener.getClient();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: exception caught -> " + e.getMessage());
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
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
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
                listener.onEditClientDialogPositiveClick(EditClientDialog.this, c);
                Toast.makeText(getContext(), "Cliente Agregado", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.cancelBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Operación cancelada", Toast.LENGTH_SHORT).show();
            }
        });
        ((EditText) view.findViewById(R.id.clientName)).setText(client.getName());
        ((EditText) view.findViewById(R.id.clientNit)).setText(client.getNit());
        ((EditText) view.findViewById(R.id.clientSecurityNit)).setText(client.getSecurityNit());
        ((EditText) view.findViewById(R.id.clientAddress)).setText(client.getAddress());
        ((EditText) view.findViewById(R.id.clientPhone)).setText(client.getPhone());
        ((EditText) view.findViewById(R.id.clientCity)).setText(client.getCity());
        ((EditText) view.findViewById(R.id.clientDepartment)).setText(client.getDepartment());
        ((EditText) view.findViewById(R.id.clientContact)).setText(client.getContact());
        ((EditText) view.findViewById(R.id.clientComment)).setText(client.getComment());
        Log.d(TAG,"onCreateDialog: finished");
        return builder.create();
    }
}
