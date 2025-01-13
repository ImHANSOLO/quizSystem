package com.bfs.logindemo.repository;

import com.bfs.logindemo.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    @Query("SELECT c FROM Contact c ORDER BY c.time DESC")
    List<Contact> findAllOrderByTimeDesc();
}
