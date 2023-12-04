package br.org.oabgo.dto;


import java.io.Serializable;
import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;

public class CofryStoreCargaVendaDTO implements Serializable {

	private static final long serialVersionUID = -4010422654752691213L;

	@SerializedName(value = "DTMVRI")
	private String dataMovimentacaoInterna;
	
	@SerializedName(value = "UUIDMVRI")
	private String identificadorMovimentacaoInterna;
	
	@SerializedName(value = "IDMAD")
	private Integer idMad;
	
	@SerializedName(value = "Advertiser")
	private String advertiser;
	
	@SerializedName(value = "Document")
	private String documento;
	
	@SerializedName(value = "ClientIDReceived")
	private Integer clientIdReceived;
	
	@SerializedName(value = "NameCompany")
	private String nomeCompanhia;
	
	@SerializedName(value = "Situation")
	private String descricaoSituacao;
	
	@SerializedName(value = "Classification")
	private String descricaoClassificacao;
	
	@SerializedName(value = "TotalOrder")
	private BigDecimal totalOrder;
	
	@SerializedName(value = "Cashback")
	private BigDecimal cashback;
	
	@SerializedName(value = "CashbackCustomer")
	private BigDecimal cashbackCustomer;
	
	@SerializedName(value = "CashbackFormat")
	private Integer cashbackFormat;
	
	@SerializedName(value = "CashbackFinal")
	private BigDecimal cashbackFinal;
	
	@SerializedName(value = "CustomerBalance")
	private BigDecimal customerBalance;
	
	@SerializedName(value = "PartnerName")
	private String partnerName;
	
	@SerializedName(value = "LastUpdate")
	private String dataUltimaAtualizacao;
	
	@SerializedName(value = "Payed")
	private Integer payed;
	
	@SerializedName(value = "DatePayed")
	private String DatePayed;

	public String getDatePayed() {
		return DatePayed;
	}

	public void setDatePayed(String datePayed) {
		DatePayed = datePayed;
	}

	public String getDataMovimentacaoInterna() {
		return dataMovimentacaoInterna;
	}

	public void setDataMovimentacaoInterna(String dataMovimentacaoInterna) {
		this.dataMovimentacaoInterna = dataMovimentacaoInterna;
	}

	public String getIdentificadorMovimentacaoInterna() {
		return identificadorMovimentacaoInterna;
	}

	public void setIdentificadorMovimentacaoInterna(String identificadorMovimentacaoInterna) {
		this.identificadorMovimentacaoInterna = identificadorMovimentacaoInterna;
	}

	public Integer getIdMad() {
		return idMad;
	}

	public void setIdMad(Integer idMad) {
		this.idMad = idMad;
	}

	public String getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(String advertiser) {
		this.advertiser = advertiser;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Integer getClientIdReceived() {
		return clientIdReceived;
	}

	public void setClientIdReceived(Integer clientIdReceived) {
		this.clientIdReceived = clientIdReceived;
	}

	public String getNomeCompanhia() {
		return nomeCompanhia;
	}

	public void setNomeCompanhia(String nomeCompanhia) {
		this.nomeCompanhia = nomeCompanhia;
	}

	public String getDescricaoSituacao() {
		return descricaoSituacao;
	}

	public void setDescricaoSituacao(String descricaoSituacao) {
		this.descricaoSituacao = descricaoSituacao;
	}

	public String getDescricaoClassificacao() {
		return descricaoClassificacao;
	}

	public void setDescricaoClassificacao(String descricaoClassificacao) {
		this.descricaoClassificacao = descricaoClassificacao;
	}

	public BigDecimal getTotalOrder() {
		return totalOrder;
	}

	public void setTotalOrder(BigDecimal totalOrder) {
		this.totalOrder = totalOrder;
	}

	public BigDecimal getCashback() {
		return cashback;
	}

	public void setCashback(BigDecimal cashback) {
		this.cashback = cashback;
	}

	public BigDecimal getCashbackCustomer() {
		return cashbackCustomer;
	}

	public void setCashbackCustomer(BigDecimal cashbackCustomer) {
		this.cashbackCustomer = cashbackCustomer;
	}

	public Integer getCashbackFormat() {
		return cashbackFormat;
	}

	public void setCashbackFormat(Integer cashbackFormat) {
		this.cashbackFormat = cashbackFormat;
	}

	public BigDecimal getCashbackFinal() {
		return cashbackFinal;
	}

	public void setCashbackFinal(BigDecimal cashbackFinal) {
		this.cashbackFinal = cashbackFinal;
	}

	public BigDecimal getCustomerBalance() {
		return customerBalance;
	}

	public void setCustomerBalance(BigDecimal customerBalance) {
		this.customerBalance = customerBalance;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}

	public void setDataUltimaAtualizacao(String dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}

	public Integer getPayed() {
		return payed;
	}

	public void setPayed(Integer payed) {
		this.payed = payed;
	}
}

