package com.revature.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controller.AccountController;
import com.revature.controller.ClientController;
import com.revature.controller.ExceptionMappingController;

import io.javalin.Javalin;

public class Application {

	public static void main(String[] args) {

		// Create the javalin object and also configure CORS settings

		// CORS is a security setting in modern web browsers such as Chrome
		// that prevent arbitary Http requests to be sent by potentially malicious JS
		// Code
		// The backend needs to be configured to tell the browser what sources are
		// "trusted"
		// In essence, becuse the JS code is being served from http://localhost:55000
		// on the backend that those are trusted sources.

		Javalin app = Javalin.create((config) -> {

			config.enableCorsForOrigin("http://localhost:5500", "http://127.0.0.1:5500");
		});

		Logger logger = LoggerFactory.getLogger(Application.class);

		app.before(ctx -> {
			logger.info(ctx.method() + " request received to the " + ctx.path() + " endpoint");
		});

		ClientController clientController = new ClientController();
		clientController.registerEndpoints(app);

		AccountController accountController = new AccountController();
		accountController.registerEndpoints(app);

		ExceptionMappingController exceptionController = new ExceptionMappingController();
		exceptionController.mapExceptions(app);

		app.start();

	}

}
