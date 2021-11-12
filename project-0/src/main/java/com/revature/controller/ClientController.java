package com.revature.controller;

import java.util.List;

import com.revature.dto.AddOrUpdateAccountDTO;
import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.model.Account;
import com.revature.model.Client;
import com.revature.service.AccountService;
import com.revature.service.ClientService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ClientController {

	private ClientService clientService;
	private AccountService accountService;

	public ClientController() {
		this.clientService = new ClientService();
		this.accountService = new AccountService();
	}

	private Handler editClientById = (ctx) -> {
		String clientId = ctx.pathParam("client_id");

		AddOrUpdateClientDTO dto = ctx.bodyAsClass(AddOrUpdateClientDTO.class);
		Client clientThatWasJustEdited = this.clientService.editClientFirstName(clientId, dto.getFirstName());

		ctx.json(clientThatWasJustEdited);
	};

	private Handler addClient = (ctx) -> {
		AddOrUpdateClientDTO dto = ctx.bodyAsClass(AddOrUpdateClientDTO.class);

		Client c = this.clientService.addClient(dto);
		ctx.json(c);
		ctx.status(201);
	};

	private Handler getAllClients = (ctx) -> {
		List<Client> clients = this.clientService.getAllClients();

		ctx.json(clients);
	};

	private Handler deleteClientById = (ctx) -> {
		String id = ctx.pathParam("client_id");

		this.clientService.deleteClientById(id);

	};

	private Handler deleteAllClients = (ctx) -> {

		this.clientService.deleteAllClient();

	};

	private Handler getClientById = (ctx) -> {
		String id = ctx.pathParam("client_id");

		Client c = this.clientService.getClientById(id);
		ctx.json(c);
	};

	public void registerEndpoints(Javalin app) {

		app.post("/clients", addClient); // works
		app.get("/clients", getAllClients); // works
		app.get("/clients/{client_id}", getClientById); // works
		app.put("/clients/{client_id}", editClientById); // works
		app.delete("/clients/{client_id}", deleteClientById); // works

		app.delete("/clients", deleteAllClients); // not necessary


	}
}
