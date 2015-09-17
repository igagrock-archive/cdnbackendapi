package com.cdn.backend.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
public class ErrorModel {
	private String error_type;
	private int code;
	private String error_message;

	public String getError_type() {
		return error_type;
	}

	public int getCode() {
		return code;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public ErrorModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErrorModel(String error_type, int code, String error_message) {
		super();
		this.error_type = error_type;
		this.code = code;
		this.error_message = error_message;
	}

}
