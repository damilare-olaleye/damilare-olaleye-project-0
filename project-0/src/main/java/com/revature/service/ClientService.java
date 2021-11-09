package com.revature.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.ClientDAO;
import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.InvalidParameterException;
import com.revature.model.Client;

public class ClientService {

	private Logger logger = LoggerFactory.getLogger(ClientService.class);

	private ClientDAO clientDao;

	// MOCK FOR TEST
	public ClientService(ClientDAO clientDao) {
		this.clientDao = clientDao;
	}

	public ClientService() {
		this.clientDao = new ClientDAO();
	}

	// WE DO NOT NEED THIS
	public Client editClientFirstName(String clientId, String changedName)
			throws ClientNotFoundException, InvalidParameterException, SQLException {

		try {
			int id = Integer.parseInt(clientId);

			Client clientIdToEdit = this.clientDao.getClientById(id);

			if (clientIdToEdit == null) {
				throw new ClientNotFoundException("Client with an id of " + clientId + "not found");
			}

			AddOrUpdateClientDTO dto = new AddOrUpdateClientDTO(changedName, clientIdToEdit.getLastName(),
					clientIdToEdit.getPhoneNumber(), clientIdToEdit.getPinCode(), clientIdToEdit.getStreet());

			Client updatedClient = this.clientDao.updateClient(id, dto);

			return updatedClient;

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Id provided is not an int convertable value");
		}
	}

	public void editClientById(String cliendId)
			throws SQLException, ClientNotFoundException, InvalidParameterException {

		try {
			int id = Integer.parseInt(cliendId);

			Client client = this.clientDao.getClientById(id);
			if (client == null) {
				throw new ClientNotFoundException(
						"Client with id " + id + " was not found, and therefore, we client cannot be edited");

			}

			this.clientDao.editClientId(id);

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Id supplied is not an int");
		}
	}

	public List<Client> getAllClients() throws SQLException {
		logger.info("getAllStudents() invoked");

		List<Client> clients = this.clientDao.getAllClients();

		return clients;
	}

	public Client getClientById(String clientId)
			throws SQLException, ClientNotFoundException, InvalidParameterException {

		try {
			int id = Integer.parseInt(clientId);

			Client c = this.clientDao.getClientById(id);

			if (c == null) {
				throw new ClientNotFoundException("Client with id of " + clientId + " was not found");
			}

			return c;

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Id provided is noto an int convertable value");
		}
	}

	// Double Check this
	public Client addClient(AddOrUpdateClientDTO dto) throws InvalidParameterException, SQLException {

		if (dto.getFirstName().trim().equals("")) {
			throw new InvalidParameterException("First name cannot be blank");
		}

		if (dto.getLastName().trim().equals("")) {
			throw new InvalidParameterException("Last name cannnot be blank");
		}

		if (dto.getPinCode().trim().equals("")) {
			throw new InvalidParameterException("PinCode cannot be blank");
		}

		if (dto.getStreet().trim().equals("")) {
			throw new InvalidParameterException("PinCode cannot be blank");
		}

		if (dto.getPhoneNumber().trim().equals("")) {
			throw new InvalidParameterException("Phone Number cannot be blank");
		}

		dto.setFirstName(dto.getFirstName().trim());
		dto.setLastName(dto.getLastName().trim());
		dto.setStreet(dto.getStreet().trim());
		dto.setPinCode(dto.getPinCode().trim());
		dto.setPhoneNumber(dto.getPhoneNumber().trim());

		Client insertedClient = this.clientDao.addClient(dto);

		return insertedClient;
	}

	public void deleteClientById(String cliendId)
			throws SQLException, ClientNotFoundException, InvalidParameterException {

		try {
			int id = Integer.parseInt(cliendId);

			Client client = this.clientDao.getClientById(id);
			if (client == null) {
				throw new ClientNotFoundException(
						"Client with id " + id + " was not found, and therefore, we client cannot be deleted");

			}

			this.clientDao.deleteClientId(id);

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Id supplied is not an int");
		}
	}

	public void deleteAllClient() throws SQLException, InvalidParameterException {

		logger.info("deleteAllClients() invoked");

		try {

			List<Client> client = this.clientDao.getAllClients();

			if (client == null) {
				throw new SQLException("Unable to delete any record, please check if record exists");
			}

			this.clientDao.deleteAllClients();

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Invalid parameter suppplied");
		}
	}

}
