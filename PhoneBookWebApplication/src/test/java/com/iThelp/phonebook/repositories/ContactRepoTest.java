package com.iThelp.phonebook.repositories;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.iThelp.phonebook.Entities.Contact;


@ExtendWith(MockitoExtension.class)
@DataJpaTest
class ContactRepoTest {

    @Mock
    private ContactRepo contactRepo;
    
    private Contact contact1;
    
    private Contact contact2;
    
    private List<Contact> contacts;
    
    @BeforeEach
    void setUp() {
    	contact1 = new Contact(1,"John", "john.doe@example.com", "1234567890", "Y");
        contact2 = new Contact(2,"Doe", "doe@example.com", "0987654321", "Y");
        //Contact contact3 = new Contact(3,"Smith", "jack.smith@example.com", "4567890123", "N");
        contacts = new ArrayList<>();
        contacts.add(contact1);
        contacts.add(contact2);
    }
    

    @Test
    void testFindByActiveSw_positive() {

        when(contactRepo.findByActiveSw("Y")).thenReturn(contacts);

        // Execute
        List<Contact> result = contactRepo.findByActiveSw("Y");

        // Verify
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals("john.doe@example.com", result.get(0).getEmail());
        assertEquals("1234567890", result.get(0).getPhno());
        assertEquals("Y", result.get(0).getActiveSw());
        
        assertEquals("Doe", result.get(1).getName());
        assertEquals("doe@example.com", result.get(1).getEmail());
        assertEquals("0987654321", result.get(1).getPhno());
        assertEquals("Y", result.get(1).getActiveSw());
    }
    
    @Test
    public void testFindByActiveSw_negative() {
    	 when(contactRepo.findByActiveSw("N")).thenReturn(new ArrayList());

         // Execute
         List<Contact> result = contactRepo.findByActiveSw("N");

         // Verify
         assertEquals(0, result.size());
    }

}

