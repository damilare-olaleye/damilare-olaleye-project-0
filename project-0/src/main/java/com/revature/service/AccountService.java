package com.revature.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.AccountDAO;
import com.revature.dao.ClientDAO;
import com.revature.dto.AddOrUpdateAccountDTO;
import com.revature.exceptions.InvalidParameterException;
import com.revature.exceptions.NotFoundException;
import com.revature.model.Account;

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

//		if (dto.getAccountStatus().trim().equals("") || dto.getAccountStatus() != "active"
//				|| dto.getAccountStatus() != "suspended" || dto.getAccountStatus() != "inactive") {
//			throw new InvalidParameterException(
//					"Account Status cannot be blank, and/or must be active, inactive, or suspended");
//		}
//
//		if (dto.getAccountTotalBalance() < 0) {
//			throw new InvalidParameterException("Total account balance cannot be less than zero");
//
//		}
//
//		if (dto.getClientId() < 1) {
//			throw new InvalidParameterException("Client ID cannot be less than 1");
//		}
//
//		if (dto.getAccountType().trim().equals("") || dto.getAccountType() != "savings"
//				|| dto.getAccountType() != "checkings" || dto.getAccountType() != "others") {
//			throw new InvalidParameterException(
//					"Account type cannot be blank and must be either savings, checkings, or others ");
//		}

		try {
			int clientId = Integer.parseInt(client_id);

			Account account = this.accountDao.addIntoAccount(clientId, dto);

			return account;

		} catch (NumberFormatException e) {
			throw new InvalidParameterException(
					"Total balance can only be an integer or double values, please try again");
		}

	}

	public List<Account> getAllAccountsByClientId(String clientId, Context ctx) throws SQLException {

		List<Account> accounts;

		int client_id = Integer.parseInt(clientId);

		if (ctx.queryParam("amountGreaterThan") != null && ctx.queryParam("amountLessThan") != null) {
			int greaterThan = Integer.parseInt(ctx.queryParam("amountGreaterThan"));
			int lessThan = Integer.parseInt(ctx.queryParam("amountLessThan"));

			accounts = this.accountDao.getAllAccountByClientsId(client_id, greaterThan, lessThan);

		} else if (ctx.queryParam("amountLessThan") != null) {

			int lessThan = Integer.parseInt(ctx.queryParam("amountLessThan"));

			accounts = this.accountDao.getAllAccountByClientsId(client_id, 400, lessThan);

		} else if (ctx.queryParam("amountGreaterThan") != null) {

			int greaterThan = Integer.parseInt(ctx.queryParam("amountGreaterThan"));

			accounts = this.accountDao.getAllAccountByClientsId(client_id, greaterThan, 2000);

		} else {
			accounts = this.accountDao.getAllAccountByClientsId(client_id, 0, 400);

		}

		return accounts;
	}

	public List<Account> getAllAccounts() throws SQLException, NotFoundException, InvalidParameterException {

		logger.info("getAllAccounts() invoked");

		List<Account> account = this.accountDao.getAllAccounts();

		return account;

	}

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

	public Account editAccountClientId(String clientId, String accountId, AddOrUpdateAccountDTO dto)
			throws InvalidParameterException, SQLException, NotFoundException {

		logger.info("editAccountClientId) invoked");

		try {
			int client_id = Integer.parseInt(clientId);
			int account_id = Integer.parseInt(accountId);

			Account updatedAccount = this.accountDao.updateAccount(client_id, account_id, dto);

			return updatedAccount;

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Id supplied is not an int value");
		}

	}

	public void deleteAccount(String clientId, String accountId) throws InvalidParameterException, SQLException {

		logger.info("deleteAccount) invoked");

		try {

			int client_id = Integer.parseInt(clientId);
			int account_id = Integer.parseInt(accountId);

			this.accountDao.deleteAccount(client_id, account_id);
		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Id supplied is not an int value");
		}
	}

	public void deleteAccountByClientId(String clientId, int id)
			throws InvalidParameterException, SQLException, NotFoundException {

		logger.info("deleteAccountByClientId) invoked");

		try {

			int client_id = Integer.parseInt(clientId);

			Account accountToDelete = this.accountDao.getAccountById(client_id, id);

			if (accountToDelete == null) {
				throw new NotFoundException(
						"Account with id " + id + " was not found, and therefore, account cannot be deleted");
			}

			this.accountDao.deleteAccountId(client_id);

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Id supplied is not an int");
		}

	}

}
