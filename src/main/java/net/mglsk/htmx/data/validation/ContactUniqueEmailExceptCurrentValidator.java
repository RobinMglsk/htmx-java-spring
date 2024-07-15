package net.mglsk.htmx.data.validation;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.mglsk.htmx.data.entity.Contact;
import net.mglsk.htmx.data.repository.ContactRepository;

public class ContactUniqueEmailExceptCurrentValidator implements ConstraintValidator<ContactUniqueEmailExceptCurrent, Contact> {

    @Autowired
    private ContactRepository contactRepository;
    private String idField;
    private String emailField;
    private String message;

    @Override
    public void initialize(ContactUniqueEmailExceptCurrent constraintAnnotation) {
        this.idField = constraintAnnotation.idField();
        this.emailField = constraintAnnotation.emailField();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Contact value, ConstraintValidatorContext context) {

        Long id = (Long) new BeanWrapperImpl(value).getPropertyValue(idField);
        String email = (String) new BeanWrapperImpl(value).getPropertyValue(emailField);

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addPropertyNode(emailField).addConstraintViolation();

        if (id != null && email != null) {
            try {
                return (contactRepository == null || !contactRepository.existsByEmailAndIdNot(email, id));
            } catch (IllegalArgumentException e) {
                return false; // or handle exception
            }
        }

        return false;
    }

    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
}