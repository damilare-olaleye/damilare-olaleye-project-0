package com.revature.dto;

import java.util.Objects;

public class AddOrUpdateAccountDTO {
	private int id;
	private String accountStatus;
	private double accountNumber;
	private double accountTotalBalance;
	private String accountType;
	private int clientId;

	public AddOrUpdateAccountDTO() {

	}

	public AddOrUpdateAccountDTO(int id, String accountStatus, double accountNumber, double accountTotalBalance,
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

	public double getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(double accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getAccountTotalBalance() {
		return accountTotalBalance;
	}

	public void setAccountTotalBalance(double accountTotalBalance) {
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
		return Double.doubleToLongBits(accountNumber) == Double.doubleToLongBits(other.accountNumber)
				&& Objects.equals(accountStatus, other.accountStatus)
				&& Double.doubleToLongBits(accountTotalBalance) == Double.doubleToLongBits(other.accountTotalBalance)
				&& Objects.equals(accountType, other.accountType) && clientId == other.clientId && id == other.id;
	}

	@Override
	public String toString() {
		return "AddOrUpdateAccountDTO [id=" + id + ", accountStatus=" + accountStatus + ", accountNumber="
				+ accountNumber + ", accountTotalBalance=" + accountTotalBalance + ", accountType=" + accountType
				+ ", clientId=" + clientId + "]";
	}


	  
}
