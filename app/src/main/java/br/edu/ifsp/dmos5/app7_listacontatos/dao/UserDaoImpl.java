package br.edu.ifsp.dmos5.app7_listacontatos.dao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.ifsp.dmos5.app7_listacontatos.model.Contact;
import br.edu.ifsp.dmos5.app7_listacontatos.model.User;

public class UserDaoImpl implements UserDao {

    private final List<User> database;

    public UserDaoImpl() {
        database = new ArrayList<>(5);

        List<Contact> contatos = new ArrayList<Contact>();
        contatos.add(new Contact("Mariazinha", "Maria Helena","(16)99445-7798"));
        contatos.add(new Contact("Joaninha", "Joana Silva","(11)99659-9974"));
        contatos.add(new Contact("Luizinha", "Luiza Mendes","(81)98199-1234"));

        database.add(new User((List<Contact>) contatos,"aluz", "!&ToI644"));
        database.add(new User((List<Contact>) contatos,"esilva", "hSWf&295"));
        database.add(new User((List<Contact>) contatos,"lcavalheiro", "123"));
        database.add(new User((List<Contact>) contatos,"htrevisan", "73d@F0Wr"));
        database.add(new User((List<Contact>) contatos,"jsilva", "4W2di5#2"));
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return database.stream()
                .filter(user1 -> user1.getUsername().equals(username))
                .findAny()
                .orElse(null);
    }

    @Override
    public void addUser(User user) {
        if (user != null) {
            database.add(user);
        }
    }

    public List<User> findAll(Order order) {
        Comparator<User> comparator = Comparator.comparing(User::getUsername);
        if(order == Order.ASCENDING) {
            return database.stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());
        }else{
            return database.stream()
                    .sorted(comparator.reversed())
                    .collect(Collectors.toList());
        }
    }

    public Contact findContactByUsername(String username, String surname) {
        Contact eq = null;
        List<Contact> contacts = new ArrayList<>();

        for (User user: database){
            if (user.getUsername().equals(username)) {
                contacts = user.getContacts();
                for (Contact contact : contacts) {
                    if (contact.getSurname().equals(surname)) {
                        eq = contact;
                    }
                }
            }
        }

        System.out.println(eq);

        return eq;
    }

    public List<Contact> contacts(String username){
        return findByUsername(username).getContacts();
    }

    public void addContact(User user, Contact contact){
        if (user != null && contact != null){
            user.addContact(contact);
        }
    }
}

