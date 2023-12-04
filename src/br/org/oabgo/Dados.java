package br.org.oabgo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Dados {

	@SerializedName("data")
	@Expose
	private List<Data> data = null;
	@SerializedName("status")
	@Expose
	private String status;

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
