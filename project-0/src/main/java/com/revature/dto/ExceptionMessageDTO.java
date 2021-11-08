package com.revature.dto;

import java.util.Objects;

public class ExceptionMessageDTO {

	private String messsage;

	public ExceptionMessageDTO() {

	}

	public ExceptionMessageDTO(Exception e) {
		this.messsage = e.getMessage();
	}

	public String getMesssage() {
		return messsage;
	}

	public void setMesssage(String messsage) {
		this.messsage = messsage;
	}

	@Override
	public int hashCode() {
		return Objects.hash(messsage);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExceptionMessageDTO other = (ExceptionMessageDTO) obj;
		return Objects.equals(messsage, other.messsage);
	}

	@Override
	public String toString() {
		return "ExceptionMessageDTO [messsage=" + messsage + "]";
	}

}
