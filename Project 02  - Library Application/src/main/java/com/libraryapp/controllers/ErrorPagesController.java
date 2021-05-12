package com.libraryapp.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPagesController implements ErrorController {

	@RequestMapping(value="/error")
	public String errorHandler(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		if (status != null) {
			
			int statusCode = Integer.valueOf(status.toString());
			
			if (statusCode == HttpStatus.BAD_REQUEST.value()) {
				return "error-pages/400-error.html";
			}
			
			if (statusCode == HttpStatus.FORBIDDEN.value()) {
				return "error-pages/403-error.html";
			}
			
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "error-pages/404-error.html";
			}
			
			if (statusCode == HttpStatus.REQUEST_TIMEOUT.value()) {
				return "error-pages/408-error.html";
			}
			
			if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return "error-pages/500-error.html";
			}
			
			if (statusCode == HttpStatus.BAD_GATEWAY.value()) {
				return "error-pages/502-error.html";
			}
			
			if (statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
				return "error-pages/503-error.html";
			}
			
			if (statusCode == HttpStatus.GATEWAY_TIMEOUT.value()) {
				return "error-pages/504-error.html";
			}
		}
		
		return "error/general-error-page.html";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}