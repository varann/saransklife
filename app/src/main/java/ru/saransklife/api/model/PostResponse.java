package ru.saransklife.api.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by asavinova on 26/10/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostResponse {

	private boolean result;
	private String error;
	@JsonProperty("error_code")
	private Integer errorCode;

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
