package com.bfs.logindemo.service;

import com.bfs.logindemo.entity.Contact;
import com.bfs.logindemo.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepo;

    public ContactService(ContactRepository contactRepo) {
        this.contactRepo = contactRepo;
    }

    public void submitContact(Contact contact) {
        contactRepo.save(contact);
    }

    public List<Contact> listAllContacts() {
        return contactRepo.findAllOrderByTimeDesc();
    }

    public Contact findById(int contactId) {
        return contactRepo.findById(contactId).orElse(null);
    }
}
