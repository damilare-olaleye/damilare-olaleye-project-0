package com.revature.controller;

import java.util.List;

import com.revature.dto.AddOrUpdateClientDTO;
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

		Client newClient = this.clientService.editClientById(clientId, dto.getFirstName(), dto.getLastName(),
				dto.getPhoneNumber(), dto.getPinCode(), dto.getStreet());

		ctx.json(newClient);
		ctx.status(201);
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

		ctx.status(201);

	};

	private Handler deleteAllClients = (ctx) -> {

		this.clientService.deleteAllClient();

		ctx.status(201);

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
		app.put("/clients/{client_id}", editClientById);
		app.delete("/clients/{client_id}", deleteClientById); // works

		app.delete("/clients", deleteAllClients); // not necessary

	}
}
