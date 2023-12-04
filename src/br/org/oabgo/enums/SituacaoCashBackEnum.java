package br.org.oabgo.enums;

import java.util.Arrays;

public enum SituacaoCashBackEnum {
	COMPRA_REALIZADA(1L, "Compra Realizada"), COMPRA_CANCELADA(2L, "Compra Cancelada"),
	CREDITO_LIBERADO(3L, "Crédito Liberado"), CREDITO_LIBERADO_ENCODE(3L, "Cr\u00e9dito Liberado");

	private Long codigo;
	private String descricao;

	SituacaoCashBackEnum() {
	}

	SituacaoCashBackEnum(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	/*
	 * public static SituacaoCashBackEnum fromString(final String nome) {
	 * SituacaoCashBackEnum[] enumValues = SituacaoCashBackEnum.values();
	 * 
	 * return Arrays.stream(enumValues).filter(item ->
	 * item.getDescricao().equalsIgnoreCase(nome)).findFirst().orElse(null); }
	 */

	public static boolean isTipoPermitido(String descricaoSituacao) {
		if (SituacaoCashBackEnum.COMPRA_CANCELADA.getDescricao().equalsIgnoreCase(descricaoSituacao.trim())
				|| SituacaoCashBackEnum.COMPRA_REALIZADA.getDescricao().equalsIgnoreCase(descricaoSituacao.trim())
				|| SituacaoCashBackEnum.CREDITO_LIBERADO.getDescricao().equalsIgnoreCase(descricaoSituacao.trim())
				|| SituacaoCashBackEnum.CREDITO_LIBERADO_ENCODE.getDescricao()
						.equalsIgnoreCase(descricaoSituacao.trim())) {
			return true;
		}

		return false;
	}
}
