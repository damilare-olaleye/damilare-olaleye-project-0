package com.revature.model;

import java.util.Date;
import java.util.Objects;

public class Client {

	private int id;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private Date activeDate;
	private String street;
	private String pinCode;
	private String emailId;
	private String phoneNumber;

	public Client() {

	}

	public Client(int id, String firstName, String lastName, Date dateOfBirth, Date activeDate, String street, String pinCode,
			String emailId, String phoneNumber) {

		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.activeDate = activeDate;
		this.street = street;
		this.pinCode = pinCode;
		this.emailId = emailId;
		this.phoneNumber = phoneNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(activeDate, dateOfBirth, emailId, firstName, lastName, phoneNumber, pinCode, street);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(activeDate, other.activeDate) && Objects.equals(dateOfBirth, other.dateOfBirth)
				&& Objects.equals(emailId, other.emailId) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(phoneNumber, other.phoneNumber)
				&& Objects.equals(pinCode, other.pinCode) && Objects.equals(street, other.street);
	}

	@Override
	public String toString() {
		return "Client [firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth
				+ ", activeDate=" + activeDate + ", street=" + street + ", pinCode=" + pinCode + ", emailId=" + emailId
				+ ", phoneNumber=" + phoneNumber + "]";
	}

}
