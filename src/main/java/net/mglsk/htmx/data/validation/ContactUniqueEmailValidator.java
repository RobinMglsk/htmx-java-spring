package net.mglsk.htmx.data.validation;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.mglsk.htmx.data.repository.ContactRepository;

public class ContactUniqueEmailValidator implements ConstraintValidator<ContactUniqueEmail, String> {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public void initialize(ContactUniqueEmail constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && (contactRepository == null || !contactRepository.existsByEmail(value));
    }

    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
}