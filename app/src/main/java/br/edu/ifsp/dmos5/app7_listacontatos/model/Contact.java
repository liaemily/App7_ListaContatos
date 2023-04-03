package br.edu.ifsp.dmos5.app7_listacontatos.model;

public class Contact {
    private String surname;
    private String name;
    private String phone;

    public Contact(String surname, String name, String phone) {
        this.surname = surname;
        this.name = name;
        this.phone = phone;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

}

