package com.revature.dto;

import java.util.Objects;

public class AddOrUpdateAccountDTO {
	private int id;
	private String accountStatus;
	private float accountNumber;
	private float accountTotalBalance;
	private String accountType;
	private int clientId;

	public AddOrUpdateAccountDTO() {

	}

	public AddOrUpdateAccountDTO(int id, String accountStatus, float accountNumber, float accountTotalBalance,
			String accountType, int clientId) {
		super();
		this.id = id;
		this.accountStatus = accountStatus;
		this.accountNumber = accountNumber;
		this.accountTotalBalance = accountTotalBalance;
		this.accountType = accountType;
		this.clientId = clientId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public float getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(float accountNumber) {
		this.accountNumber = accountNumber;
	}

	public float getAccountTotalBalance() {
		return accountTotalBalance;
	}

	public void setAccountTotalBalance(float accountTotalBalance) {
		this.accountTotalBalance = accountTotalBalance;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountNumber, accountStatus, accountTotalBalance, accountType, clientId, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddOrUpdateAccountDTO other = (AddOrUpdateAccountDTO) obj;
		return Float.floatToIntBits(accountNumber) == Float.floatToIntBits(other.accountNumber)
				&& Objects.equals(accountStatus, other.accountStatus)
				&& Float.floatToIntBits(accountTotalBalance) == Float.floatToIntBits(other.accountTotalBalance)
				&& Objects.equals(accountType, other.accountType) && clientId == other.clientId && id == other.id;
	}

	@Override
	public String toString() {
		return "AddOrUpdateAccountDTO [id=" + id + ", accountStatus=" + accountStatus + ", accountNumber="
				+ accountNumber + ", accountTotalBalance=" + accountTotalBalance + ", accountType=" + accountType
				+ ", clientId=" + clientId + "]";
	}

	  
}
