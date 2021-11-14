package com.revature.service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.AccountDAO;
import com.revature.dao.ClientDAO;
import com.revature.dto.AddOrUpdateAccountDTO;
import com.revature.exceptions.InvalidParameterException;
import com.revature.exceptions.NotFoundException;
import com.revature.model.Account;
import com.revature.model.Client;

import io.javalin.http.Context;

public class AccountService {

	private Logger logger = LoggerFactory.getLogger(AccountService.class);

	private AccountDAO accountDao;
	private ClientDAO clientDao;

	public AccountService() {
		this.accountDao = new AccountDAO();
		this.clientDao = new ClientDAO();
	}

	// Mockito Mock Test
	private AccountService(AccountDAO accountDao, ClientDAO clientDao) {
		this.accountDao = accountDao;
		this.clientDao = clientDao;

	}

	public Account addAccount(String client_id, AddOrUpdateAccountDTO dto)
			throws InvalidParameterException, SQLException {

		logger.info("addClient(dto) invoked");

		// verify account status
		Set<String> validStatus = new HashSet<>();
		validStatus.add("active");
		validStatus.add("inactive");
		validStatus.add("invalid");

		// validate account status
		if (!validStatus.contains(dto.getAccountStatus())) {
			throw new InvalidParameterException("You entered an invalid account status");
		}

		// verify account type
		Set<String> validAccountType = new HashSet<>();
		validAccountType.add("savings");
		validAccountType.add("checkings");
		validAccountType.add("others");

		// validate account type
		if (!validAccountType.contains(dto.getAccountType())) {
			throw new InvalidParameterException(
					"You entered an invalid account type. Please enter either savings, checking, or others");
		}

		// validate total account balance
		if (dto.getAccountTotalBalance() < 0) {
			throw new InvalidParameterException("Total account balance cannot be less than zero");

		}

		try {
			int clientId = Integer.parseInt(client_id);

			if (clientId == -1) {
				throw new InvalidParameterException("ClientId cannot be negative 1. Please enter the right client ID");
			}

			Account account = this.accountDao.addIntoAccount(clientId, dto);

			return account;

		} catch (NumberFormatException e) {
			throw new InvalidParameterException(
					"Total balance can only be an integer or double values, please try again");
		}

	}

	// THIS GET ALL ACCOUNT ASSOCIATED WITH CLIENTS AND CLIENT ID
	// PLUS WE CHECK IF CLIENT ID AMOUNT BALANCE IS GREATER OR LESS THAN

	public List<Account> getAllAccountsByClientId(String clientId, Context ctx)
			throws SQLException, InvalidParameterException, NotFoundException {

		try {

			List<Account> accounts;
			
			int client_id = Integer.parseInt(clientId);

			if (ctx.queryParam("amountGreaterThan") != null && ctx.queryParam("amountLessThan") != null) {
				int greaterThan = Integer.parseInt(ctx.queryParam("amountGreaterThan"));
				int lessThan = Integer.parseInt(ctx.queryParam("amountLessThan"));

				accounts = this.accountDao.getAllAccountByClientsId(client_id, greaterThan, lessThan);

			} else if (ctx.queryParam("amountLessThan") != null) {

				int lessThan = Integer.parseInt(ctx.queryParam("amountLessThan"));

				accounts = this.accountDao.getAllAccountByClientsId(client_id, 200, lessThan);

			} else if (ctx.queryParam("amountGreaterThan") != null) {

				int greaterThan = Integer.parseInt(ctx.queryParam("amountGreaterThan"));

				accounts = this.accountDao.getAllAccountByClientsId(client_id, greaterThan, 2000);

			} else {
				accounts = this.accountDao.getAllAccountByClientsId(client_id, 200, 2000);

			}

			return accounts;

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Client not found");
		}

	}

	// THIS GET ALL ACCOUNT ASSOCIATED WITH CLIENTS

	public List<Account> getAllAccounts() throws SQLException, NotFoundException, InvalidParameterException {

		logger.info("getAllAccounts() invoked");

		List<Account> account = this.accountDao.getAllAccounts();

		if (account == null) {
			throw new NotFoundException("Accounts not found");
		} else {
			return account;
		}

	}

	// THIS ACCOUNT ASSOCIATED WITH CLIENTS

	public Account getAccount(String clientId, String accountId)
			throws InvalidParameterException, SQLException, NotFoundException {

		try {

			int client_id = Integer.parseInt(clientId);
			int account_id = Integer.parseInt(accountId);

			Account getAccountById = this.accountDao.getAccountById(client_id, account_id);

			if (getAccountById == null) {
				throw new NotFoundException("Sorry the client with " + client_id
						+ " you're looking for does not match account id of " + account_id);
			}

			return getAccountById;

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Id supplied is not an int value");
		}
	}

	// EDIT ACCOUNT ID IF AND ONLY WE HAVE CLIENT ID

	public Account editAccountClientId(String clientId, String accountId)
			throws InvalidParameterException, SQLException, NotFoundException {

		logger.info("editAccountClientId) invoked");

		try {

			int client_id = Integer.parseInt(clientId);
			int account_id = Integer.parseInt(accountId);

			Account accountIdToEdit = this.accountDao.getAccountById(client_id, account_id);

			if (accountIdToEdit == null) {
				throw new NotFoundException(
						"Client with an id of " + client_id + " with account id of " + account_id + " not found");
			}

			AddOrUpdateAccountDTO dto = new AddOrUpdateAccountDTO(account_id, accountIdToEdit.getAccountStatus(),
					accountIdToEdit.getAccountNumber(), accountIdToEdit.getAccountTotalBalance(),
					accountIdToEdit.getAccountType(), client_id);

			Account updatedAccount = this.accountDao.updateAccount(client_id, account_id, dto);

			return updatedAccount;

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Id supplied is not an integer value");
		}

	}

	// DELETING THE ENTIRE ACCOUNT. NOT NEEDED

	public void deleteAccount(String clientId, String accountId) throws InvalidParameterException, SQLException {

		logger.info("deleteAccount) invoked");

		try {

			int client_id = Integer.parseInt(clientId);
			int account_id = Integer.parseInt(accountId);

			this.accountDao.deleteAccount(client_id, account_id);
		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Id supplied is not an integer value");
		}
	}

	// DELETE ACCOUNT ID IF AND ONLY IT MATCHES WITH CLIENT ID

	public void deleteAccountByClientId(String clientId, String accountId)
			throws InvalidParameterException, SQLException, NotFoundException {

		logger.info("deleteAccountByClientI(clientId, accountId) invoked");

		try {

			int client_id = Integer.parseInt(clientId);
			int account_id = Integer.parseInt(accountId);

			Account accountToDelete = this.accountDao.getAccountById(client_id, account_id);

			if (accountToDelete == null) {
				throw new NotFoundException(
						"Account with id " + account_id + " was not found, and therefore, account cannot be deleted");
			} else {

				this.accountDao.deleteClientAndAccountId(client_id, account_id);
			}

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Id supplied is not an integer value");
		}

	}

}
