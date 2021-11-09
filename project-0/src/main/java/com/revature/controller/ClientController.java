package com.revature.controller;

import java.util.List;

import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.model.Client;
import com.revature.service.ClientService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ClientController {

	private ClientService clientService;

	public ClientController() {
		this.clientService = new ClientService();
	}

	// WE DO NOT NEED THIS
	private Handler editClientFirstName = (ctx) -> {
		String clientId = ctx.pathParam("id");

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

	private Handler getClientById = (ctx) -> {
		String id = ctx.pathParam("id");

		Client c = this.clientService.getClientById(id);
		ctx.json(c);
	};

	private Handler editClientById = (ctx) -> {
		String id = ctx.pathParam("id");

		this.clientService.editClientById(id);
		
	};

	private Handler deleteClientById = (ctx) -> {
		String id = ctx.pathParam("id");

		this.clientService.deleteClientById(id);

	};

	private Handler deleteAllClients = (ctx) -> {

		this.clientService.deleteAllClient();

	};

	public void registerEndpoints(Javalin app) {

		app.post("/clients", addClient);
		app.get("/clients", getAllClients);  //works
		app.get("/clients/{id}", getClientById); //works
		app.put("/clients/{id}", editClientById);
		app.delete("/clients/{id}", deleteClientById);
		app.delete("/clients", deleteAllClients);
	}
}
