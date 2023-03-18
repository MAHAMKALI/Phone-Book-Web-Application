package com.iThelp.phonebook.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iThelp.phonebook.Entities.Contact;
import com.iThelp.phonebook.repositories.ContactRepo;
import com.iThelp.phonebook.service.ContactService;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @Mock
    private ContactRepo contactRepo;
    
    @InjectMocks
    private ContactService contactService;
    
    private  Contact contact1;
    
    private  Contact contact2;
    
    private List<Contact> contacts;
    
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        contact1 = new Contact(1, "John","john.doe@example.com","9898567854","Y");
        contact2 = new Contact(2, "swe","swe.doer@example.com","7798567854","Y");
        contacts = Arrays.asList(contact1, contact2);
    }

    @Test
    void testAddContact() {
        // Given
        

        when(contactRepo.save(contact1)).thenReturn(contact1);

        // When
        Contact savedContact = contactService.addContact(contact1);

        // Then
        assertNotNull(savedContact);
        assertEquals(1, savedContact.getId());
        assertEquals("John", savedContact.getName());
        assertEquals("Y", savedContact.getActiveSw());
    }

    @Test
    void testGetAllContacts() {

        when(contactRepo.findByActiveSw("Y")).thenReturn(contacts);

        // When
        List<Contact> foundContacts = contactService.getAllContacts();

        // Then
        assertNotNull(foundContacts);
        assertEquals(2, foundContacts.size());
    }

    @Test
    void testGetContact() {

        when(contactRepo.findById(1)).thenReturn(Optional.of(contact1));

        Contact foundContact = contactService.getContact(1);

        assertNotNull(foundContact);
        assertEquals(1, foundContact.getId());
        assertEquals("John", foundContact.getName());
        assertEquals("Y", foundContact.getActiveSw());
    }

    @Test
    void testUpdateContact() {
 
        when(contactRepo.findById(1)).thenReturn(Optional.of(contact1));
  
        when(contactRepo.save(contact1)).thenReturn(contact1);

        // Then
        assertEquals("Record has updated", contactService.updateContact(contact1));
        assertEquals("Y", contact1.getActiveSw());
    }

    @Test
    void testDeleteContact() {
      
        when(contactRepo.findById(1)).thenReturn(Optional.of(contact1));

        String result = contactService.deleteContact(1);

        assertEquals("Contact has deleted", result);
        assertEquals("N", contact1.getActiveSw());
    }
}

