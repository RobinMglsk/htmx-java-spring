package net.mglsk.htmx.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "contacts")
public class Contact {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    @NotEmpty(message = "First name is required")
    @Size(min = 2, message = "First name should be atleast 2 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Last name is required")
    @Size(min = 2, message = "Last name should be atleast 2 characters")
    private String lastName;

    @Column(name = "phone")
    @NotEmpty(message = "Phone is required")
    @Size(min = 10, message = "Phone number should be atleast 10 characters")
    private String phone;

    @Column(name = "email")
    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
}