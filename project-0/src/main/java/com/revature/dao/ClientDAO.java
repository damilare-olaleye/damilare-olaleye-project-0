package com.revature.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.model.Client;
import com.revature.util.JDBCUtility;

public class ClientDAO {

	// CRUD
	/*
	 * 1. C -> CREATE 2. R -> READ 3. U -> UPDATE 4. D -> DELETE
	 */

	// CREATE CLIENT
	public Client addClient(AddOrUpdateClientDTO client) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "INSERT INTO client (client_first_name, client_last_name, client_dob, client_active_date, "
					+ "client_street, client_pin_code, client_email_id, client_phone_number)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, client.getFirstName());
			pstmt.setString(2, client.getLastName());
			pstmt.setDate(3, (Date) client.getDateOfBirth());
			pstmt.setDate(4, (Date) client.getActiveDate());
			pstmt.setString(5, client.getStreet());
			pstmt.setString(6, client.getPinCode());
			pstmt.setString(7, client.getEmailId());
			pstmt.setString(8, client.getPhoneNumber());

			int numberOfRecordsInserted = pstmt.executeUpdate();

			if (numberOfRecordsInserted != 1) {
				throw new SQLException("Cannot add new clients");
			}

			ResultSet rs = pstmt.getGeneratedKeys();

			rs.next();
			int automaticallyGeneratedId = rs.getInt(1);

			return new Client(automaticallyGeneratedId, client.getFirstName(), client.getLastName(),
					client.getDateOfBirth(), client.getActiveDate(), client.getStreet(), client.getPinCode(),
					client.getEmailId(), client.getPhoneNumber());
		}
	}

	// READ CLIENT
	public List<Client> getAllClients() throws SQLException {

		List<Client> listOfClients = new ArrayList<>();

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM client";
			PreparedStatement pstmt = con.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("client_id");
				String firstName = rs.getString("client_first_name");
				String lastName = rs.getString("client_last_name");
				Date dateOfBirth = rs.getDate("client_dob");
				Date activeDate = rs.getDate("client_active_date");
				String clientStreet = rs.getString("client_street");
				String pinCode = rs.getString("client_pin_code");
				String emailId = rs.getString("client_email_id");
				String phoneNumber = rs.getString("client_phone_number");

				Client c = new Client(id, firstName, lastName, dateOfBirth, activeDate, clientStreet, pinCode, emailId,
						phoneNumber);

				listOfClients.add(c);
			}
		}

		return listOfClients;
	}

	public Client getClientById(int id) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM client WHERE client_id = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return new Client(rs.getInt("client_id"), rs.getString("client_first_name"),
						rs.getString("client_last_name"), rs.getDate("client_dob"), rs.getDate("client_active_date"),
						rs.getString("client_Street"), rs.getString("client_pin_code"), rs.getString("client_email_id"),
						rs.getString("client_phone_number"));

			} else {
				return null;
			}
		}
	}

	// UPDATE CLIENT
	public Client updateClient(int clientId, AddOrUpdateClientDTO client) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "UPDATE client " + "SET client_first_name = ?," + "		  client_last_name = ?,"
					+ "		  client_dob = ?," + "		  client_active_date = ?," + "		  client_street = ?,"
					+ "		  client_pin_code = ?," + "       client_email_id ?," + "       client_phone_number"
					+ "WHERE " + "client_id = ?;";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, client.getFirstName());
			pstmt.setString(2, client.getLastName());
			pstmt.setDate(3, (Date) client.getDateOfBirth());
			pstmt.setDate(4, (Date) client.getActiveDate());
			pstmt.setString(5, client.getStreet());
			pstmt.setString(6, client.getPinCode());
			pstmt.setString(7, client.getEmailId());
			pstmt.setString(8, client.getPhoneNumber());
			pstmt.setInt(8, clientId);

			int numberOfRecordsUpdated = pstmt.executeUpdate();

			if (numberOfRecordsUpdated != 1) {
				throw new SQLException("Unable to update client recotd with id of " + clientId);
			}

		}

		return new Client(clientId, client.getFirstName(), client.getLastName(), client.getDateOfBirth(),
				client.getActiveDate(), client.getStreet(), client.getPinCode(), client.getEmailId(),
				client.getPhoneNumber());
	}

	public void editClientId(int id) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "UPDATE FROM client WHERE client_id = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);

			int numberOfRecordsEdited = pstmt.executeUpdate();

			if (numberOfRecordsEdited != 1) {
				throw new SQLException("Unable to edit student recored with id of " + id);
			}
		}
	}

	// DELETE CLIENT
	public void deleteClientId(int id) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "DELETE FROM client WHERE client_id = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);

			int numberOfRecordsDeleted = pstmt.executeUpdate();

			if (numberOfRecordsDeleted != 1) {
				throw new SQLException("Unable to delete student recored with id of " + id);
			}
		}
	}

	public void deleteAllClients() throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "DELETE FROM client";
			PreparedStatement pstmt = con.prepareStatement(sql);

			int numberOfRecordsDeleted = pstmt.executeUpdate();

			if (numberOfRecordsDeleted == 0) {
				throw new SQLException("Unable to delete any records, please check if records exist in the table");
			}
		}
	}
}