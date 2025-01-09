package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.ContactDao;
import com.bfs.logindemo.domain.Contact;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactDao contactDao;

    public ContactService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public void submitContact(Contact contact) {
        contactDao.save(contact);
    }

    public List<Contact> listAllContacts() {
        return contactDao.findAll();
    }
}