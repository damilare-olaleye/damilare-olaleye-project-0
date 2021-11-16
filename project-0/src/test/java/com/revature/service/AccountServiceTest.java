package com.revature.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revature.dao.AccountDAO;
import com.revature.dao.ClientDAO;
import com.revature.dto.AddOrUpdateAccountDTO;
import com.revature.exceptions.InvalidParameterException;
import com.revature.exceptions.NotFoundException;
import com.revature.model.Account;

public class AccountServiceTest {

	private AccountService accountService;
	private ClientService clientService;
	
	// Positive test
	
	@Test
	public void testgetAccountPositive() throws SQLException, NotFoundException, InvalidParameterException {
		
		// ARRANGE
		
		AccountDAO mockAccountDao = mock(AccountDAO.class);
		ClientDAO mockClientDao = mock(ClientDAO.class);
		
		Account firstAccount = new Account(12, "active", 3729139, 6735, "checkings", 15);
		Account secondAccount = new Account(1, "inactive", 3729139, 6735, "savings", 13);
		Account thirdAccount = new Account(2, "suspended", 3729139, 6735, "others", 3);

		List<Account> addAccount = new ArrayList<>();
		addAccount.add(firstAccount);
		addAccount.add(secondAccount);
		addAccount.add(thirdAccount);
		
		when(mockAccountDao.getAllAccounts()).thenReturn(addAccount);
		
		AccountService aService = new AccountService(mockAccountDao, mockClientDao);
		
		// ACT
		
		List<Account> actual = aService.getAllAccounts();
		
		// ASSERT
		
		List<Account> expected = new ArrayList<>();
		
		expected.add(firstAccount);
		expected.add(secondAccount);
		expected.add(thirdAccount);
		
		Assertions.assertEquals(expected, actual);
	}
	
	// Negative test
	
	@Test
	public void testgetAccountThrowsSQLExceptionNegative() throws SQLException {
		
		//ARRANGE
		
		AccountDAO mockAccountDao = mock(AccountDAO.class);
		ClientDAO mockClientDao = mock(ClientDAO.class);
		
		when(mockAccountDao.getAllAccounts()).thenThrow(SQLException.class);
		
		AccountService aService = new AccountService(mockAccountDao, mockClientDao);

		// ACT AND ASSERT
		
		Assertions.assertThrows(SQLException.class, () -> {
			
			aService.getAllAccounts();
		
		});
	}
	
	// Positive test
	
	@Test
	public void testAddAccountPositive() throws SQLException, InvalidParameterException {
		
		// ARRANGE
		
		AccountDAO mockAccountDao = mock(AccountDAO.class);
		ClientDAO mockClientDao = mock(ClientDAO.class);
		
		AddOrUpdateAccountDTO dto = new AddOrUpdateAccountDTO(3, "active", 1232, 2323, "checkings", 5);
		
		when(mockAccountDao.addIntoAccount(3, dto)).thenReturn(new Account(6, "active", 1232, 2323, "checkings", 5));
		
		AccountService accountService = new AccountService(mockAccountDao, mockClientDao);
		
		// ACT
		
		AddOrUpdateAccountDTO actDTO = new AddOrUpdateAccountDTO(3, "active", 1232, 2323, "checkings", 5);
		Account actual = accountService.addAccount("3", actDTO);
		
		// ASSERT
		
		Account expected = new Account(6, "active", 1232, 2323, "checkings", 5);
		Assertions.assertEquals(expected, actual);
		
	}
	
	// Negative Test
	
	@Test
	public void testAddAccountNegativeForInvalidParameter() {
		
		// ARRANGE 
		
		AccountDAO mockAccountDao = mock(AccountDAO.class);
		ClientDAO mockClientDao = mock(ClientDAO.class);
		
		AccountService accountService = new AccountService(mockAccountDao, mockClientDao);
		
		// ACT AND ASSERT
		
		AddOrUpdateAccountDTO dto = new AddOrUpdateAccountDTO(2, "../(%#", 1232, 2323, "checkings", 5);
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			
			accountService.addAccount("2", dto);
		});
	}
	
	@Test
	public void testAddAccountNegativeForInvalidInputForAccountType() {
		
		// ARRANGE 
		
		AccountDAO mockAccountDao = mock(AccountDAO.class);
		ClientDAO mockClientDao = mock(ClientDAO.class);
		
		AccountService accountService = new AccountService(mockAccountDao, mockClientDao);
		
		// ACT AND ASSERT
		
		AddOrUpdateAccountDTO dto = new AddOrUpdateAccountDTO(2, "active", 1232, 2323, "392930", 5);
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			
			accountService.addAccount("2", dto);
		});
	}
	

	
}
