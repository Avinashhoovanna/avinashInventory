package com.inventory.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = {RestController.class})
public class InventoryExceptionController {
	@ExceptionHandler(value = InventoryException.class)
	public ResponseEntity<Object> exception(InventoryException exception) {
		return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
	}
}
