package ru.saransklife.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asavinova on 20/03/15.
 */
public class LoginResponse extends Response {

	private Login response;

	public Login getResponse() {
		return response;
	}

	public void setResponse(Login response) {
		this.response = response;
	}

	public class Login {

		@SerializedName("data_version")
		private String dataVersion;

		private String token;

		public String getDataVersion() {
			return dataVersion;
		}

		public void setDataVersion(String dataVersion) {
			this.dataVersion = dataVersion;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}
	}

}