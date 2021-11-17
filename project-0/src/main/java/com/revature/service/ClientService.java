package com.revature.service;

import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.ClientDAO;
import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.exceptions.NotFoundException;
import com.revature.exceptions.InvalidParameterException;
import com.revature.model.Client;

public class ClientService {

	private Logger logger = LoggerFactory.getLogger(ClientService.class);

	private ClientDAO clientDao;

	// Mockito Mock Test
	public ClientService(ClientDAO clientDao) {
		this.clientDao = clientDao;
	}

	public ClientService() {
		this.clientDao = new ClientDAO();
	}

	public Client editClientById(String clientId, String editFirstName, String editLastName, String editPhoneNumber,
			String editPinCode, String editStreet) throws NotFoundException, SQLException, InvalidParameterException {

		logger.info("editClientById(clientId...) invoked");

		try {

			int client_id = Integer.parseInt(clientId);

			Client clientIdToEdit = this.clientDao.getClientById(client_id);

			if (clientIdToEdit == null) {
				throw new NotFoundException("Client with an id of " + clientId + " not found");
			}

			AddOrUpdateClientDTO dto = new AddOrUpdateClientDTO(editFirstName, editLastName, editPhoneNumber,
					editPinCode, editStreet);

			Client updatedClient = this.clientDao.updateClient(client_id, dto);

			if (updatedClient == null) {
				throw new NotFoundException("Cannot update client, sorry");
			}

			return updatedClient;

		} catch (NumberFormatException e) {
			throw new NotFoundException("Id provided is not an int convertable value, please enter the correct id");
		}
	}

	public List<Client> getAllClients() throws SQLException {
		logger.info("getAllClients() invoked");

		List<Client> clients = this.clientDao.getAllClients();

		return clients;
	}

	public Client getClientById(String clientId) throws SQLException, NotFoundException, InvalidParameterException {

		logger.info("getClientById(clientId) invoked");

		try {
			int id = Integer.parseInt(clientId);

			Client c = this.clientDao.getClientById(id);

			if (c == null) {
				throw new NotFoundException("Client with id of " + id + " was not found");
			}

			return c;

		} catch (NumberFormatException e) {
			throw new NotFoundException("Id provided is not an int convertable value");
		}
	}

	public Client addClient(AddOrUpdateClientDTO dto)
			throws InvalidParameterException, SQLException, NotFoundException {

		logger.info("addClient(dto) invoked");

		try {

			if (dto.getFirstName().trim().equals("")) {
				throw new NotFoundException("first name cannot be blank");
			}

			if (dto.getLastName().trim().equals("")) {
				throw new NotFoundException("last name cannot be blank");
			}
		

			if (dto.getPinCode().trim().equals("")) {
				throw new NotFoundException("pin code cannot be blank");
			}

			if (dto.getStreet().trim().equals("")) {
				throw new NotFoundException("street name cannot be blank");
			}

			if (dto.getPhoneNumber().trim().equals("")) {
				throw new NotFoundException("phone number cannot be blank");
			}
			
			if ((dto.getLastName().trim().equals("")) && (dto.getFirstName().trim().equals("")) 
					&& (dto.getPinCode().trim().equals("")) && (dto.getPinCode().trim().equals("")) 
					&& (dto.getStreet().trim().equals("")) && (dto.getPhoneNumber().trim().equals(""))) {
				
				throw new NotFoundException("Please fill in the blanks, namaste (thank you)!");
				
			}

			dto.setFirstName(dto.getFirstName().trim());
			dto.setLastName(dto.getLastName().trim());
			dto.setStreet(dto.getStreet().trim());
			dto.setPinCode(dto.getPinCode().trim());
			dto.setPhoneNumber(dto.getPhoneNumber().trim());

			Client insertedClient = this.clientDao.addClient(dto);

			return insertedClient;

		} catch (NumberFormatException e) {
			throw new NotFoundException("Invalid parameter was thrown");
		}

	}

	public void deleteClientById(String cliendId) throws SQLException, NotFoundException, InvalidParameterException {

		logger.info("deleteClientById(cliendId) invoked");

		try {
			int id = Integer.parseInt(cliendId);

			Client client = this.clientDao.getClientById(id);
			if (client == null) {
				throw new NotFoundException(
						"Client with id " + id + " was not found, and therefore, sorry the client cannot be deleted");

			}

			this.clientDao.deleteClientId(id);

		} catch (NumberFormatException e) {
			throw new NotFoundException("Id supplied is not an int");
		}
	}

	public void deleteAllClient() throws SQLException, InvalidParameterException, NotFoundException {

		logger.info("deleteAllClients() invoked");

		try {

			List<Client> client = this.clientDao.getAllClients();

			if (client == null) {
				throw new SQLException("Unable to delete any record, please check if record exists");
			}

			this.clientDao.deleteAllClients();

		} catch (NumberFormatException e) {
			throw new NotFoundException("Invalid parameter suppplied");
		}
	}

}
