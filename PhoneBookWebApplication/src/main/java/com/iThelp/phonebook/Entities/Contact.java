package com.iThelp.phonebook.Entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="CONTACT_DETAILS")
public class Contact {
	@javax.persistence.Id
	@GeneratedValue
	private int Id;
	private String name;
	private String email;
	private String phno;
	private String activeSw;
}
