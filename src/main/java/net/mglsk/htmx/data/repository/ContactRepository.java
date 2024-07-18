package net.mglsk.htmx.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.mglsk.htmx.data.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
    Page<Contact> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName, Pageable  pageable);

    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);


}
