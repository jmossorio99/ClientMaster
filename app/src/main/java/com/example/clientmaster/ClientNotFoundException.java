package com.example.clientmaster;

public class ClientNotFoundException extends Exception {

    String msg = "No existe un cliente con ese nombre";
    public ClientNotFoundException(){

    }

    public String getMsg(){

        return msg;

    }

}
