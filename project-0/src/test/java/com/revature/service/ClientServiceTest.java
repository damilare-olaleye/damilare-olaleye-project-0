package com.revature.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revature.dao.ClientDAO;
import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.exceptions.InvalidParameterException;
import com.revature.exceptions.NotFoundException;
import com.revature.model.Client;

public class ClientServiceTest {

	// Positive test

	@Test
	public void testGetAllClientsPositve() throws SQLException {

		// ARRANGE

		ClientDAO mockClientDao = mock(ClientDAO.class);

		Client client1 = new Client(2, "Tim", "Duncan", "1245 N oldarm st", "1222", "4187263545");
		Client client2 = new Client(3, "Tony", "Parker", "125 W Texas st", "1222", "7653425142");
		Client client3 = new Client(4, "Manu", "Ginobili", "987 San Antonio", "1222", "8881234356");

		List<Client> clientsFromDao = new ArrayList<>();
		clientsFromDao.add(client1);
		clientsFromDao.add(client2);
		clientsFromDao.add(client3);

		when(mockClientDao.getAllClients()).thenReturn(clientsFromDao);
		ClientService clientService = new ClientService(mockClientDao);

		// ACT

		List<Client> actual = clientService.getAllClients();

		// ASSERT

		List<Client> expected = new ArrayList<>();
		expected.add(new Client(2, "Tim", "Duncan", "1245 N oldarm st", "1222", "4187263545"));
		expected.add(new Client(3, "Tony", "Parker", "125 W Texas st", "1222", "7653425142"));
		expected.add(new Client(4, "Manu", "Ginobili", "987 San Antonio", "1222", "8881234356"));

		Assertions.assertEquals(expected, actual);

	}

	// Negative Test

	@Test
	public void testGetAllClientsSQLExceptionOccursNegative() throws SQLException {

		// ARRANGE

		ClientDAO mockClientDao = mock(ClientDAO.class);

		when(mockClientDao.getAllClients()).thenThrow(SQLException.class);

		ClientService clientService = new ClientService(mockClientDao);

		// ACT AND ASSERT

		Assertions.assertThrows(SQLException.class, () -> {
			clientService.getAllClients();
		});
	}

	// Positive Test

	@Test
	public void testGetClientByIdPositive() throws SQLException, NotFoundException, InvalidParameterException {

		// ARRANGE

		ClientDAO mockClientDao = mock(ClientDAO.class);

		when(mockClientDao.getClientById(eq(3)))
				.thenReturn(new Client(3, "Kevin", "O'leary", "3736 W Cubec Rd", "3726", "9002839857"));

		ClientService clientService = new ClientService(mockClientDao);

		// ACT

		Client actual = clientService.getClientById("3");

		// ASSERT

		Assertions.assertEquals(new Client(3, "Kevin", "O'leary", "3736 W Cubec Rd", "3726", "9002839857"), actual);

	}

	// Negative Test
	// NotFoundException thrown (client not found)

	@Test
	public void testGetClientbyIdNotFoundNegative() {

		// ARRANGE

		ClientDAO mockClientDao = mock(ClientDAO.class);

		ClientService clientService = new ClientService(mockClientDao);

		Assertions.assertThrows(NotFoundException.class, () -> {

			clientService.getClientById("5000");
		});

	}

	// Negative Test
	// InvalidParameter thrown

	@Test
	public void testGetClientIdAlphabethIdNegative() {

		// ARRANGE

		ClientDAO mockClientDao = mock(ClientDAO.class);

		ClientService clientService = new ClientService(mockClientDao);

		Assertions.assertThrows(InvalidParameterException.class, () -> {

			clientService.getClientById("abdcfj");
		});

	}

	// Negative Test
	// InvalidParameter thrown

	@Test
	public void testGetClientIdSpaceIdNegative() {

		// ARRANGE

		ClientDAO mockClientDao = mock(ClientDAO.class);

		ClientService clientService = new ClientService(mockClientDao);

		Assertions.assertThrows(InvalidParameterException.class, () -> {

			clientService.getClientById(" ");
		});

	}

	// Negative Test
	// InvalidParameter thrown

	@Test
	public void testGetClientIdInvalidCharactersIdNegative() {

		// ARRANGE

		ClientDAO mockClientDao = mock(ClientDAO.class);

		ClientService clientService = new ClientService(mockClientDao);

		Assertions.assertThrows(InvalidParameterException.class, () -> {

			clientService.getClientById("./-`x");
		});

	}

	// Negative Test
	// InvalidParameter thrown

	@Test
	public void testGetClientByIdDecimalIdNegative() {

		// ARRANGE

		ClientDAO mockClientDao = mock(ClientDAO.class);

		ClientService clientService = new ClientService(mockClientDao);

		Assertions.assertThrows(InvalidParameterException.class, () -> {

			clientService.getClientById("1.0");
		});

	}

	// Positive Test

	@Test
	public void testEditIdOfClientPositive() throws SQLException, NotFoundException, InvalidParameterException {

		// ARRANGE

		ClientDAO mockClientDao = mock(ClientDAO.class);

		when(mockClientDao.getClientById(eq(3)))
				.thenReturn(new Client(3, "Kevin", "O'leary", "3736 W Cubec Rd", "3726", "9002839857"));

		AddOrUpdateClientDTO dto = new AddOrUpdateClientDTO("Draymond", "O'leary", "3736 W Cubec Rd", "3726",
				"9002839857");

		when(mockClientDao.updateClient(eq(3), eq(dto)))
				.thenReturn(new Client(3, "Draymond", "O'leary", "3736 W Cubec Rd", "3726", "9002839857"));

		ClientService clientService = new ClientService(mockClientDao);

		// ACT

		Client actual = clientService.editClientById("3", "Draymond", "O'leary", "3736 W Cubec Rd", "3726",
				"9002839857");

		// ASSERT

		Client expected = new Client(3, "Draymond", "O'leary", "3736 W Cubec Rd", "3726", "9002839857");

		Assertions.assertEquals(expected, actual);

	}

	// Negative Test

	@Test
	public void testEditIdOfClientNotFoundException() {

		// ARRANGE

		ClientDAO mockClientDao = mock(ClientDAO.class);

		ClientService clientService = new ClientService(mockClientDao);

		// ACT AND ASSERT
		Assertions.assertThrows(NotFoundException.class, () -> {

			clientService.editClientById("78", "Babarra", "Cocorran", "7778903746", "2324", "8327 W south Bend");
		});

	}

	// Negative Test

	@Test
	public void testEditIdOfClientInvalidParameterfForSpacing() {

		// ARRANGE

		ClientDAO mockClientDao = mock(ClientDAO.class);

		ClientService clientService = new ClientService(mockClientDao);

		// ACT AND ASSERT
		Assertions.assertThrows(InvalidParameterException.class, () -> {

			clientService.editClientById(" ", "Babarra", "Cocorran", "7778903746", "2324", "8327 W south Bend");
		});

	}

	// Negative Test

	@Test
	public void testEditIdOfClientInvalidParameterfForInvalidIdCharacter() {

		// ARRANGE

		ClientDAO mockClientDao = mock(ClientDAO.class);

		ClientService clientService = new ClientService(mockClientDao);

		// ACT AND ASSERT
		Assertions.assertThrows(InvalidParameterException.class, () -> {

			clientService.editClientById("- _ -", "Babarra", "Cocorran", "7778903746", "2324", "8327 W south Bend");
		});

	}

	// Positive

	@Test
	public void testGetAddClientPositive() throws SQLException, NotFoundException, InvalidParameterException {

		// ARRANGE

		ClientDAO mockClientDao = mock(ClientDAO.class);

		when(mockClientDao.getClientById(eq(3)))
				.thenReturn(new Client(3, "Lucas", "Poldoski", "3736 W Cubec Rd", "3726", "9002839857"));

		ClientService clientService = new ClientService(mockClientDao);

		// ACT

		Client actual = clientService.getClientById("3");

		// ASSERT

		Assertions.assertEquals(new Client(3, "Lucas", "Poldoski", "3736 W Cubec Rd", "3726", "9002839857"), actual);

	}

	// Negative

	@Test
	public void testGetAddClientNegativeAlphabeth() {
		// ARRANGE

		ClientDAO mockClientDao = mock(ClientDAO.class);

		ClientService clientService = new ClientService(mockClientDao);

		// ACT AND ASSERT
		Assertions.assertThrows(InvalidParameterException.class, () -> {

			clientService.getClientById("xyz");
		});
	}

	// Negative

	@Test
	public void testGetAddClientNegativeFloat() {
		// ARRANGE

		ClientDAO mockClientDao = mock(ClientDAO.class);

		ClientService clientService = new ClientService(mockClientDao);

		// ACT AND ASSERT
		Assertions.assertThrows(InvalidParameterException.class, () -> {

			clientService.getClientById("4789f");
		});
	}

	@Test
	public void testGetAddClientNegativeDecimal() {
		// ARRANGE

		ClientDAO mockClientDao = mock(ClientDAO.class);

		ClientService clientService = new ClientService(mockClientDao);

		// ACT AND ASSERT
		Assertions.assertThrows(InvalidParameterException.class, () -> {

			clientService.getClientById("3.14");
		});
	}

	@Test
	public void testAddClientPositive() throws SQLException, NotFoundException, InvalidParameterException {

		// ARRANGE

		ClientDAO mockClientDao = mock(ClientDAO.class);

		AddOrUpdateClientDTO dto = new AddOrUpdateClientDTO("Draymond", "O'leary", "3736 W Cubec Rd", "3726",
				"9002839857");

		when(mockClientDao.addClient(eq(dto)))
				.thenReturn(new Client(7, "Lori", "Grenier", "3036 W Cubec Rd", "3726", "9002839857"));

		ClientService clientService = new ClientService(mockClientDao);

		// ACT

		when(mockClientDao.addClient(eq(dto)))
				.thenReturn(new Client(7, "Lori", "Grenier", "3036 W Cubec Rd", "3726", "9002839857"));

		Client actual = clientService.addClient(dto);

		// ASSERT

		Assertions.assertEquals(new Client(7, "Lori", "Grenier", "3036 W Cubec Rd", "3726", "9002839857"), actual);

	}

	@Test
	public void testAddClientNegativeInvalidParameterExceptionForId() {

		// ARRANGE

		ClientDAO mockClientDao = mock(ClientDAO.class);

		ClientService clientService = new ClientService(mockClientDao);

		AddOrUpdateClientDTO dto = new AddOrUpdateClientDTO(" ", "O'leary", "3736 W Cubec Rd", "3726", "9002839857");

		// ACT AND ASSERT
		Assertions.assertThrows(InvalidParameterException.class, () -> {

			clientService.addClient(dto);
		});
	}

	@Test
	public void testAddClientNegativeInvalidParameterExceptionForFirstName() {

		// ARRANGE

		ClientDAO mockClientDao = mock(ClientDAO.class);

		ClientService clientService = new ClientService(mockClientDao);

		AddOrUpdateClientDTO dto = new AddOrUpdateClientDTO("4", " ", "3736 W Cubec Rd", "3726", "9002839857");

		// ACT AND ASSERT
		Assertions.assertThrows(InvalidParameterException.class, () -> {

			clientService.addClient(dto);
		});
	}

	@Test
	public void testAddClientNegativeInvalidParameterExceptionForPhoneNumber() {

		// ARRANGE

		ClientDAO mockClientDao = mock(ClientDAO.class);

		ClientService clientService = new ClientService(mockClientDao);

		AddOrUpdateClientDTO dto = new AddOrUpdateClientDTO("10", "O'leary", "3736 W Cubec Rd", "3726", " ");

		// ACT AND ASSERT
		Assertions.assertThrows(InvalidParameterException.class, () -> {

			clientService.addClient(dto);
		});
	}

	// POSITIVE

	@Test
	public void testDeleteClientByIdPositve() {

	}

}
