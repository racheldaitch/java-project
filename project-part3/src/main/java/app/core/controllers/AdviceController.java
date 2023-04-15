package app.core.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import app.core.exception.CouponSystemException;

@RestControllerAdvice
public class AdviceController {
	@ExceptionHandler(CouponSystemException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleCouponSystemException(CouponSystemException e) {
		return e.getMessage();
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleGeneralException(Exception e) {
		return e.getMessage();
	}

}
