package com.revature.dto;

import java.util.Date;
import java.util.Objects;

public class AddOrUpdateClientDTO {

	private String firstName;
	private String lastName;
	private String street;
	private String pinCode;
	private String phoneNumber;

	public AddOrUpdateClientDTO() {

	}

	public AddOrUpdateClientDTO(String firstName, String lastName, String street, String pinCode, String phoneNumber) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.pinCode = pinCode;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName, phoneNumber, pinCode, street);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddOrUpdateClientDTO other = (AddOrUpdateClientDTO) obj;
		return Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(phoneNumber, other.phoneNumber) && Objects.equals(pinCode, other.pinCode)
				&& Objects.equals(street, other.street);
	}

	@Override
	public String toString() {
		return "AddOrUpdateClientDTO [firstName=" + firstName + ", lastName=" + lastName + ", street=" + street
				+ ", pinCode=" + pinCode + ", phoneNumber=" + phoneNumber + "]";
	}

}
