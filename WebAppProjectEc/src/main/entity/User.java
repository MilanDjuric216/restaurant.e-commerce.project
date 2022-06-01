package main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "users")
@Table
public class User {

	@Id
	@Column
	private String username;
	@Column
	private String password;
	@Column
	private String name;
	@Column
	private String surname;
	@Column
	private boolean enabled;
	
	public User() {
		
	}
	
	public User(String username, String password, String name, String surname, boolean enabled) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.enabled = enabled;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	
	
}
