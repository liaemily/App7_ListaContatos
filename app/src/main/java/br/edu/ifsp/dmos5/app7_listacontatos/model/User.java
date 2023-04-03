package br.edu.ifsp.dmos5.app7_listacontatos.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;
    private String password;
    private List<Contact> ContactList = new ArrayList<>();

    public User(List<Contact> ContactList, String username, String password) {
        this.ContactList = ContactList;
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword () {
        return password;
    }

    public List<Contact> getContacts(){
        return ContactList;
    }

    public void addContact(Contact contact){
        ContactList.add(contact);
    }

}
