package com.iThelp.phonebook.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iThelp.phonebook.Entities.Contact;
import com.iThelp.phonebook.service.ContactService;

@ExtendWith(MockitoExtension.class)
class ContactControllerTest {

    @Mock
    private ContactService contactService;
	
	@InjectMocks
    private ContactController contactController;

	@Autowired
    private MockMvc mockMvc;
   
	private Contact contact1;
	private Contact contact2;
	
	private List<Contact> contacts;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();
        contact1 = new Contact(1, "John","john.doe@example.com","9898567854","Y");
        contact2 = new Contact(2, "swe","swe.doer@example.com","7798567854","Y");
        contacts = Arrays.asList(contact1, contact2);
    }

    @Test
    void testAddContact() throws Exception {
        
        when(contactService.addContact(any(Contact.class))).thenReturn(contact1);
        
        assertEquals(HttpStatus.CREATED, contactController.addContact(contact1).getStatusCode());

        mockMvc.perform(post("/contacts/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contact1)))
              //.content("{ \"name\": \"John\", \"email\": \"john.doe@example.com\",\"phno\": \"9898567854\",\"activeSw\": \"Y\" }"))
                .andDo(print())
        		.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
				.andExpect(jsonPath("$.phno").value("9898567854"))
				.andExpect(jsonPath("$.activeSw").value("Y"));
    }

    @Test
    void testGetContact() throws Exception {
   
        when(contactService.getContact(1)).thenReturn(contact1);
        
        assertEquals(HttpStatus.OK, contactController.getContact("1").getStatusCode());

        mockMvc.perform(get("/contacts/contact?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
				.andExpect(jsonPath("$.phno").value("9898567854"))
				.andExpect(jsonPath("$.activeSw").value("Y"));
    }
	
	@Test
    void testAllContacts() throws Exception {
       
        when(contactService.getAllContacts()).thenReturn(contacts);
        
        assertEquals(HttpStatus.OK, contactController.allContacts().getStatusCode());
        
        mockMvc.perform(get("/contacts/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[0].phno").value("9898567854"))
				.andExpect(jsonPath("$[0].activeSw").value("Y"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("swe"))
                .andExpect(jsonPath("$[1].email").value("swe.doer@example.com"))
                .andExpect(jsonPath("$[1].phno").value("7798567854"))
				.andExpect(jsonPath("$[1].activeSw").value("Y"));
    }
	
	  @Test
	  public void updateContactTest() throws Exception {
	    
	    //doReturn("Contact updated successfully").when(contactService).updateContact(any(Contact.class));
		  
		when(contactService.updateContact(contact1)).thenReturn("Contact updated successfully");
		
		assertEquals(HttpStatus.OK,contactController.updateContact(contact1).getStatusCode());
		
	    mockMvc.perform(put("/contacts/update")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(contact1)))
	            .andExpect(content().string("Contact updated successfully"))
	            .andExpect(status().isOk());
	    
	  }
	
	  @Test
	  public void deleteContactTest() throws Exception {
	    //doReturn("Contact deleted successfully").when(contactService).deleteContact(1);
	    when(contactService.deleteContact(1)).thenReturn("Contact deleted successfully");
	    
		assertEquals(HttpStatus.OK, contactController.deleteContact("1").getStatusCode());
		
	   mockMvc.perform(delete("/contacts/delete/1"))
	   		  .andExpect(content().string("Contact deleted successfully"))
	          .andExpect(status().isOk());
	          


	   
	  }

}