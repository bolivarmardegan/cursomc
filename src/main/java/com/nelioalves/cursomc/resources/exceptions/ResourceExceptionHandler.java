package com.nelioalves.cursomc.resources.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
