package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dto.AddOrUpdateAccountDTO;
import com.revature.model.Account;
import com.revature.util.JDBCUtility;

public class AccountDAO {

	// CRUD
	/*
	 * 1. C -> CREATE 2. R -> READ 3. U -> UPDATE 4. D -> DELETE
	 */

	// CREATE ACCOUNT
	public Account addIntoAccount(int clientId, AddOrUpdateAccountDTO dto) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "INSERT INTO account (account_status, account_number, account_total_balance, account_type, client_id) VALUES (?,?,?,?,?)";

			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, dto.getAccountStatus());
			pstmt.setDouble(2, dto.getAccountNumber());
			pstmt.setDouble(3, dto.getAccountTotalBalance());
			pstmt.setString(4, dto.getAccountType());
			pstmt.setInt(5, clientId);

			int numberOfRecordsInserted = pstmt.executeUpdate();

			if (numberOfRecordsInserted != 1) {
				throw new SQLException("Cannot add new account, numbers of record inserted is not equal to 1");
			}

			ResultSet rs = pstmt.getGeneratedKeys();

			rs.next();
			int automaticallyGeneratedId = rs.getInt(1);

			return new Account(automaticallyGeneratedId, dto.getAccountStatus(), dto.getAccountNumber(),
					dto.getAccountTotalBalance(), dto.getAccountType(), clientId);

		}
	}

	// READ ACCOUNT
	public List<Account> getAllAccounts() throws SQLException {

		List<Account> listOfAccounts = new ArrayList<>();

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM account";

			PreparedStatement pstmt = con.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("account_id");
				String accountStatus = rs.getString("account_status");
				float accountNumber = rs.getFloat("account_number");
				float accountTotalBal = rs.getFloat("account_total_balance");
				int clientId = rs.getInt("client_id");
				String accountType = rs.getString("account_type");

				Account clientAccount = new Account(id, accountStatus, accountNumber, accountTotalBal, accountType,
						clientId);

				listOfAccounts.add(clientAccount);
			}
		}

		return listOfAccounts;
	}

	public List<Account> getAllAccountByClientsId(int client_id, int amountGreaterThan, int amountLessThan)
			throws SQLException {

		List<Account> accounts = new ArrayList<>();

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM account WHERE client_id = ? AND account_total_balance >= ? AND account_total_balance <= ?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, client_id);
			pstmt.setInt(2, amountGreaterThan);
			pstmt.setInt(3, amountLessThan);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int account_id = rs.getInt("account_id");
				String accountStatus = rs.getString("account_status");
				float accountNumber = rs.getFloat("account_number");
				float totalBalance = rs.getInt("account_total_balance");
				String accountType = rs.getString("account_type");

				Account getAllAccById = new Account(account_id, accountStatus, accountNumber, totalBalance, accountType,
						client_id);

				accounts.add(getAllAccById);
			}

			return accounts;
		}

	}

	public Account getAccountById(int client_id, int account_id) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM account WHERE account_id = ? AND client_id = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, account_id);
			pstmt.setInt(2, client_id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return new Account(account_id, rs.getString("account_status"), rs.getFloat("account_number"),
						rs.getFloat("account_total_balance"), rs.getString("account_type"), client_id);

			} else {
				return null;
			}
		}
	}

	// UPDATE ACCOUNT
	public Account updateAccount(int clientId, int accountId, AddOrUpdateAccountDTO account) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "UPDATE account SET account_status = ?, account_number = ?,"
					+ "account_total_balance = ?, account_type = ?" + "WHERE client_id = ? AND account_id = ?;";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, account.getAccountStatus());
			pstmt.setDouble(2, account.getAccountNumber());
			pstmt.setDouble(3, account.getAccountTotalBalance());
			pstmt.setString(4, account.getAccountType());
			pstmt.setInt(5, clientId);
			pstmt.setInt(6, accountId);

			int numberOfRecordsUpdated = pstmt.executeUpdate();

			if (numberOfRecordsUpdated != 1) {
				throw new SQLException(
						"Unable to update account record with id of " + accountId + ", no refrences to " + clientId);
			}

			return new Account(accountId, account.getAccountStatus(), account.getAccountNumber(),
					account.getAccountTotalBalance(), account.getAccountType(), clientId);
		}

	}

	public void editAccountId(int clientId, int accountId) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "INSERT INTO account (client_id, account_status, account_number, account_total_balance, account_type) "
					+ "VALUES (?,?,?,?,?);";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, clientId);
			pstmt.setInt(2, accountId);

			int numberOfRecordsEdited = pstmt.executeUpdate();

			if (numberOfRecordsEdited != 1) {
				throw new SQLException("Unable to edit account record with id of " + clientId);

			}

		}

	}

	// DELETE ACCOUNT

	public void deleteClientAndAccountId(int clientId, int accountId) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "DELETE FROM account WHERE account_id = ? AND client_id = ?;";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, clientId);
			pstmt.setInt(2, accountId);

			pstmt.executeUpdate();

		}

	}

	public void deleteAccountByClientId(int clientId, int accountId) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "DELETE FROM account WHERE client_id = ? AND account_id = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, clientId);
			pstmt.setInt(2, accountId);

			int numberOfRecordsDeleted = pstmt.executeUpdate();

			if (numberOfRecordsDeleted != 1) {
				throw new SQLException("Unable to delete account" + accountId + "record with client id of " + clientId);
			}
		}
	}

	public void deleteAccount(int client_id, int account_id) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "DELETE FROM account WHERE client_id = ? AND account_id = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, client_id);
			pstmt.setInt(2, account_id);

			int ids = pstmt.executeUpdate();

			if (ids != 1) {
				throw new SQLException("Account cannot be delete, make sure ClientID exist");
			}
		}
	}

}
