package net.mglsk.htmx.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.mglsk.htmx.data.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
}
