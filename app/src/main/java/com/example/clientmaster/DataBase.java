package com.example.clientmaster;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBase implements Serializable {

    private ArrayList<String> clientNames = new ArrayList<String>();
    private HashMap<String, Client> map = new HashMap<String, Client>();
    private ArrayList<List<String>> dataList;

    public void addNewClient(String[] clientData) {

        String name = clientData[0];
        String nit = clientData[1];
        String securityNit = clientData[2];
        String address = clientData[3];
        String phone = clientData[4];
        String city = clientData[5];
        String department = clientData[6];
        String contact = clientData[7];
        String comment = "";
        clientNames.add(name);
        map.put(name, new Client(name, nit, securityNit, address, phone, city, department, contact, comment));

    }

    public void addNewClient(Client c) {

        String name = c.getName();
        clientNames.add(name);
        map.put(name, c);

    }

    public void deleteClient(String name){
        clientNames.remove(name);
        map.remove(name);
    }

    public ArrayList<String> getClientNames() {

        return clientNames;

    }

    public Client getClient(String clientName) throws ClientNotFoundException{
        if (map.get(clientName) != null) return map.get(clientName);
        else throw new ClientNotFoundException();
    }

}
