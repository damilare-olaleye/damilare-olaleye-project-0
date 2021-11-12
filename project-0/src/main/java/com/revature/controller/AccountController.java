package com.revature.controller;

import java.util.List;

import com.revature.dto.AddOrUpdateAccountDTO;
import com.revature.model.Account;
import com.revature.service.AccountService;
import com.revature.service.ClientService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AccountController {

	private AccountService accountService;
	private ClientService clientService;

	public AccountController() {
		this.accountService = new AccountService();
		this.clientService = new ClientService();
	}

	private Handler addAccount = (ctx) -> {

		String clientId = ctx.pathParam("client_id");

		if (this.clientService.getClientById(clientId) != null) {
			
			AddOrUpdateAccountDTO dto = ctx.bodyAsClass(AddOrUpdateAccountDTO.class);
			Account accounts = this.accountService.addAccount(clientId, dto);

			ctx.json(accounts);
			ctx.status(201);

		} else {
			ctx.status(400);
		}

	};
	
	private Handler getAllAccountsByClientId = (ctx) -> {

		String clientId = ctx.pathParam("client_id");

		List<Account> accounts = this.accountService.getAllAccountsByClientId(clientId, ctx);

		ctx.json(accounts);

	};

	private Handler getAllAccountsByAccountId = (ctx) -> {

		List<Account> accounts = this.accountService.getAllAccounts();

		ctx.json(accounts);
	};


	private Handler editAccountsByClientId = (ctx) -> {

		String clientId = ctx.pathParam("client_id");

		String accountId = ctx.pathParam("account_id");
		
		Account accounts = this.accountService.editAccountClientId(clientId, accountId);

		ctx.json(accounts);
	};

	private Handler deleteAccountByClientId = (ctx) -> {

		String clientId = ctx.pathParam("client_id");
		String accountId = ctx.pathParam("account_id");

		this.accountService.deleteAccountByClientId(clientId, accountId);

	};

	public void registerEndpoints(Javalin app) {

		app.post("/clients/{client_id}/accounts", addAccount); // works
		app.get("/clients/{client_id}/accounts", getAllAccountsByClientId); // works
		app.get("/clients/{client_id}/accounts/{account_id}", getAllAccountsByAccountId); // works
		app.put("/clients/{client_id}/accounts/{account_id}", editAccountsByClientId); // works
		app.delete("/clients/{client_id}/accounts/{account_id}", deleteAccountByClientId); // works

	}

}
