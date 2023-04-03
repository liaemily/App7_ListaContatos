package br.edu.ifsp.dmos5.app7_listacontatos.dao;

import java.util.List;

import br.edu.ifsp.dmos5.app7_listacontatos.model.Contact;
import br.edu.ifsp.dmos5.app7_listacontatos.model.User;


public interface UserDao {

    List<User> getAll();

    List<Contact> contacts(String username);

    User findByUsername(String username);

    void addUser(User user);

    void addContact(User user, Contact contact);

    Contact findContactByUsername(String username, String surname);
}
