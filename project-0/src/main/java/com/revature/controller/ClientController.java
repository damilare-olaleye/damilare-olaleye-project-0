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

		if (newClient == null) {
			ctx.json("Cannnot edit client id, make sure id is correct" + clientId);
			ctx.status(400);
		}
		
		ctx.json("Succesfully edit client by ID! " + newClient);
		ctx.status(201);
	};

	private Handler addClient = (ctx) -> {
		AddOrUpdateClientDTO dto = ctx.bodyAsClass(AddOrUpdateClientDTO.class);

		Client c = this.clientService.addClient(dto);
		
		if (c == null) {
			ctx.json("Cannnot add client, make sure id is correct");
			ctx.status(400);
		}
		
		ctx.json(c);
		ctx.status(201);
	};

	private Handler getAllClients = (ctx) -> {
		List<Client> clients = this.clientService.getAllClients();
		
		if (clients == null) {
			ctx.json("Cannnot get all clients");
			ctx.status(400);
		}
		
		ctx.json(clients);
		ctx.status(201);
	};

	private Handler deleteClientById = (ctx) -> {
		String id = ctx.pathParam("client_id");

		this.clientService.deleteClientById(id);
		
		ctx.json("Succesfull deleted client, ADD CLIENT if need be");
		ctx.status(201);

	};

	private Handler deleteAllClients = (ctx) -> {

		this.clientService.deleteAllClient();

		ctx.json("All clients have be successfully deleted. ADD CLIENTS if need be");
		ctx.status(201);

	};

	private Handler getClientById = (ctx) -> {
		String id = ctx.pathParam("client_id");

		Client c = this.clientService.getClientById(id);
		
		if (c == null ) {
			ctx.json("Cannot get client by id");
			ctx.status(400);
		} 
		
		ctx.json(c);
		ctx.status(201);
	};

	public void registerEndpoints(Javalin app) {

		app.post("/clients", addClient); // works
		app.get("/clients", getAllClients); // works
		app.get("/clients/{client_id}", getClientById); // works
		app.put("/clients/{client_id}", editClientById);
		app.delete("/clients/{client_id}", deleteClientById); // works

		app.delete("/clients", deleteAllClients); // not necessary but works

	}
}
