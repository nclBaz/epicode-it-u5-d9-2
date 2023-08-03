package riccardogulin.u5d9.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionsHandler {

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorsPayload handleBadRequest(BadRequestException e) {
		return new ErrorsPayload(e.getMessage(), new Date(), 13212);
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorsPayload handleNotFound(NotFoundException e) {
		return new ErrorsPayload(e.getMessage(), new Date(), 1239132);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorsPayload handleGeneric(Exception e) {
		log.error(e.getMessage());
		e.printStackTrace();
		return new ErrorsPayload("Errore generico, risolveremo il prima possibile", new Date(), 2321);
	}

}
