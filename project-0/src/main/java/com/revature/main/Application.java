package com.revature.main;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controller.ClientController;
import com.revature.controller.ExceptionMappingController;

import io.javalin.Javalin;

public class Application {

	public static void main(String[] args) {
		
		Javalin app = Javalin.create();
		
		Logger logger = LoggerFactory.getLogger(Application.class);
		
		app.before(ctx -> {
			logger.info(ctx.method() + " request received to the " + ctx.path() + " endpoint");
		});
		
		ClientController controller = new ClientController();
		controller.registerEndpoints(app);
		
		ExceptionMappingController exceptionController = new ExceptionMappingController();
		exceptionController.mapExceptions(app);
		
		app.start();
		

	}

}
