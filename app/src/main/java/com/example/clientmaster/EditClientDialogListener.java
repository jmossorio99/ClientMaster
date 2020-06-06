package com.example.clientmaster;

import android.support.v4.app.DialogFragment;

public interface EditClientDialogListener {
    void onEditClientDialogPositiveClick(DialogFragment dialog, Client c);
    Client getClient();
}
