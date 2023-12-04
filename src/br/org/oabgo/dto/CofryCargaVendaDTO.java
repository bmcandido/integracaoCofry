package br.org.oabgo.dto;

import java.io.Serializable;
import java.util.List;

public class CofryCargaVendaDTO implements Serializable {

	private static final long serialVersionUID = 7890204582418217470L;

	private Integer errorCode;
	
	private String errorMessage;
	
	private List<CofryStoreCargaVendaDTO> store;

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<CofryStoreCargaVendaDTO> getStore() {
		return store;
	}

	public void setStore(List<CofryStoreCargaVendaDTO> store) {
		this.store = store;
	}
}

