package com.nelioalves.cursomc.resources.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nelioalves.cursomc.services.exceptions.DataIntegretyException;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e,HttpServletRequest request){
		StandardError err =  new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), 
				"Não encontrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
		
	}
	
	@ExceptionHandler(DataIntegretyException.class)
	public ResponseEntity<StandardError> dataIntegrety(DataIntegretyException e,HttpServletRequest request){
		StandardError err =  new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Não é possível excluir uma categoria que possui Produtos.", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e,HttpServletRequest request){
		ValidationError err =  new ValidationError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Erro de validação.", e.getMessage(), request.getRequestURI());
		for (FieldError error : e.getBindingResult().getFieldErrors()) {
			err.addError(error.getField(), error.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
		
	}
}
