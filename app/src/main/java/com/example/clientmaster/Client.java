package com.example.clientmaster;

import java.io.Serializable;

public class Client implements Serializable {

    private String name;
    private String nit;
    private String securityNit;
    private String address;
    private String phone;
    private String city;
    private String department;
    private String contact;
    private String comment;

    public Client(String name, String nit, String securityNit, String address, String phone, String city
            , String department, String contact, String comment) {

        this.name = name;
        this.nit = nit;
        this.securityNit = securityNit;
        this.address = address;
        this.phone = phone;
        this.city = city;
        this.department = department;
        this.contact = contact;
        this.comment = comment;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getSecurityNit() {
        return securityNit;
    }

    public void setSecurityNit(String securityNit) {
        this.securityNit = securityNit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String[] getData() {

        return new String[]{getName(), getNit(), getSecurityNit(), getAddress(),
                getPhone(), getCity(), getDepartment(), getContact(), getComment()};

    }

    @Override
    public String toString() {
        return name + ";" + nit + ";" + securityNit + ";" + address + ";" + phone + ";" + city + ";" + department + ";" + contact + ";" + comment;
    }
}
