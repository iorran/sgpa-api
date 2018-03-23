package com.lgc.ctps.sgpa.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgc.ctps.sgpa.domain.enumeration.BusinessTypeMessageEnum;

import lombok.Data;

@Data
class BusinessError {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private List<String> messages;
	private List<String> debugMessages;
	private Integer businessTypeMessage;

	BusinessError() {
		timestamp = LocalDateTime.now();
	}

	public BusinessError(Throwable ex) {
		this();
		this.messages = new ArrayList<>(Arrays.asList("Unexpected error"));
		this.debugMessages = Arrays.asList(ex.getLocalizedMessage());
		this.businessTypeMessage = BusinessTypeMessageEnum.ERROR.ordinal();
	}

	public BusinessError(List<String> messages, Throwable ex, BusinessTypeMessageEnum businessTypeMessage) {
		this();
		this.messages = messages;
		this.debugMessages = Arrays.asList(ex.getLocalizedMessage());
		this.businessTypeMessage = businessTypeMessage.ordinal();
	}

	public BusinessError(List<String> messages, List<String> debugMessages, BusinessTypeMessageEnum businessTypeMessage) {
		this();
		this.messages = messages;
		this.debugMessages = debugMessages;
		this.businessTypeMessage = businessTypeMessage.ordinal();
	}
}