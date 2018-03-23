package com.lgc.ctps.sgpa.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.lgc.ctps.sgpa.domain.enumeration.BusinessTypeMessageEnum;

@ControllerAdvice
public class BusinessExceptionController {

	private static final Logger logger = LoggerFactory.getLogger(BusinessExceptionController.class);

	@Autowired
	private MessageSource messageSource;

	/**
	 * Caso o erro não esteja sendo tratado, lançaremos mensagem generica
	 *
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public BusinessError handleException(Exception exception, WebRequest request) {
		List<String> messages = Arrays.asList(messageSource.getMessage("error.001", null, LocaleContextHolder.getLocale()));
		logger.error(exception.getLocalizedMessage());
		return new BusinessError(messages, exception, BusinessTypeMessageEnum.ERROR);
	}

	/**
	 * Transações não efetuadas
	 *
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { UnexpectedRollbackException.class, DataAccessException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public BusinessError handleDatabaseException(Exception exception, WebRequest request) {
		List<String> messages = Arrays.asList(messageSource.getMessage("error.002", null, LocaleContextHolder.getLocale()));
		logger.error(exception.getLocalizedMessage());
		return new BusinessError(messages, exception, BusinessTypeMessageEnum.ERROR);
	}

	/**
	 * Se o client angular tentar enviar um parametro invalido.
	 *
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public BusinessError handleHttpMessageNotReadableException(Exception exception, WebRequest request) {
		List<String> messages = Arrays.asList(messageSource.getMessage("warning.001", null, LocaleContextHolder.getLocale()));
		logger.error(exception.getLocalizedMessage());
		return new BusinessError(messages, exception, BusinessTypeMessageEnum.WARNING);
	}

	/**
	 * Constraint Violadas
	 *
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { DataIntegrityViolationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public BusinessError handleDataIntegrityViolationException(Exception exception, WebRequest request) {
		List<String> messages = Arrays.asList(messageSource.getMessage("warning.002", null, LocaleContextHolder.getLocale()));
		logger.error(exception.getLocalizedMessage());
		return new BusinessError(messages, exception, BusinessTypeMessageEnum.WARNING);
	}

	/**
	 * Bean Validation Error
	 *
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public BusinessError handleMethodArgumentNotValid(MethodArgumentNotValidException exception, WebRequest request) {
		logger.error(exception.getLocalizedMessage());
		return beanValidationErrorListBuilder(exception.getBindingResult());
	}

	private BusinessError beanValidationErrorListBuilder(BindingResult bindingResult) {
		List<String> messages = new ArrayList<>();
		List<String> debugMessages = new ArrayList<>();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			messages.add(messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()));
			debugMessages.add(fieldError.toString());
		}

		return new BusinessError(messages, debugMessages, BusinessTypeMessageEnum.ERROR);
	}
}