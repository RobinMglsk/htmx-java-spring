package net.mglsk.htmx.web.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.groups.Default;
import net.mglsk.htmx.data.entity.Contact;
import net.mglsk.htmx.data.repository.ContactRepository;
import net.mglsk.htmx.data.validation.groups.CreateGroup;
import net.mglsk.htmx.data.validation.groups.UpdateGroup;
import net.mglsk.htmx.web.exceptions.NotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    private final ContactRepository contactsRepository;

    public ContactController(ContactRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String contacts(@RequestParam(value = "q", required = false) String q, Model model) {

        model.addAttribute("q", q);

        if (StringUtils.hasText(q)) {
            model.addAttribute("contacts", contactsRepository.findByFirstNameContainingOrLastNameContaining(q, q));
        } else {
            model.addAttribute("contacts", contactsRepository.findAll());
        }

        return "contacts/index";
    }

    @GetMapping(path = "/new", produces = MediaType.TEXT_HTML_VALUE)
    public String getNewContactForm(Model model) {
        model.addAttribute("contact", new Contact());

        return "contacts/new";
    }

    @PostMapping(path = "/new", produces = MediaType.TEXT_HTML_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String processNewContactForm(@Validated({CreateGroup.class, Default.class}) @ModelAttribute Contact contact, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("contact", contact);
            return "contacts/new";
        }

        contactsRepository.save(contact);
        redirectAttributes.addFlashAttribute("message", "Created New Contact!");
        return "redirect:/contacts";
    }

    @GetMapping(path = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String getContactDetails(@PathVariable("id") long id, Model model) {
        Optional<Contact> contact = this.contactsRepository.findById(id);

        if(contact.isEmpty()){
            throw new NotFoundException("Contact not found");
        }

        model.addAttribute("contact", contact.get());

        return "contacts/details";
    }

    @GetMapping(path = "/{id}/email", produces = MediaType.TEXT_HTML_VALUE)
    public String validateEmailAddress(@PathVariable("id") long id, @RequestParam(value = "email", required = true) String email, Model model) {
        Optional<Contact> contact = this.contactsRepository.findById(id);

        if(contact.isEmpty()){
            throw new NotFoundException("Contact not found");
        }

        model.addAttribute("contact", contact.get());

        return "contacts/details";
    }

    @GetMapping(path = "/{id}/edit", produces = MediaType.TEXT_HTML_VALUE)
    public String getEditContactForm(@PathVariable("id") long id, Model model) {

        Optional<Contact> contact = this.contactsRepository.findById(id);

        if(contact.isEmpty()){
            throw new NotFoundException("Contact not found");
        }

        model.addAttribute("contact", contact.get());

        return "contacts/edit";
    }

    @PostMapping(path = "/{id}/edit", produces = MediaType.TEXT_HTML_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String processEditContactForm(@PathVariable("id") long id, @Validated({UpdateGroup.class, Default.class}) @ModelAttribute Contact contact,
            BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            model.addAttribute("contact", contact);
            return "contacts/edit";
        }


        contact.setId(id);
        contactsRepository.save(contact);
        redirectAttributes.addFlashAttribute("message", "Updated contact!");
        return "redirect:/contacts";
    }


    @DeleteMapping(path = "/{id}", produces = MediaType.TEXT_HTML_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String deleteContact(@PathVariable("id") long id, HttpServletRequest request, RedirectAttributes redirectAttributes) {
       
        Optional<Contact> contact = this.contactsRepository.findById(id);

        if(contact.isEmpty()){
            throw new NotFoundException("Contact not found");
        }

        contactsRepository.delete(contact.get());
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.SEE_OTHER);
        redirectAttributes.addFlashAttribute("message", "Deleted contact!");
        return "redirect:/contacts";
    }

}
