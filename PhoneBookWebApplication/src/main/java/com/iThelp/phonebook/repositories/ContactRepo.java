package com.iThelp.phonebook.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iThelp.phonebook.Entities.Contact;

@Repository
public interface ContactRepo extends JpaRepository<Contact, Serializable>{
	
	public List<Contact> findByActiveSw(String activeSw);
}
