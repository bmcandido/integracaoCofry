package br.org.oabgo;

import java.math.BigDecimal;
import java.sql.Date;
//import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;

import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.util.FinderWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.org.oabgo.dto.CofryCargaVendaDTO;
import br.org.oabgo.dto.CofryStoreCargaVendaDTO;
import br.org.oabgo.enums.SituacaoCashBackEnum;
import br.org.oabgo.utils.CofryUtil;

public class BuscaCompras implements ScheduledAction, AcaoRotinaJava {
	NativeSql query = null;
	NativeSql query2 = null;

	JapeSession.SessionHandle hnd = null;
	JdbcWrapper jdbc = null;

	String urlCofry = "";

	@Override
	public void onTime(ScheduledActionContext arg0) {

		buscar();

	}

	@Override
	public void doAction(ContextoAcao arg0) throws Exception {

		buscar();

	}

	public void buscar() {
		String token;
		EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();

		System.out.println("------------------------------------------------------");
		System.out.println("Começou a executar a rotina de busca de informações BuscaCompras COFRY");
		System.out.println("------------------------------------------------------");

		try {

			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			this.query = new NativeSql(jdbc);

			this.query.appendSql(
					"SELECT EMP.CODEMP, EMP.TOKEN, EMP.URLCOFRY, ITE.DTINICIO DATAINI, ITE.DTFIM, EMP.INTERVALODEMESESPESQUISA\r\n"
							+ "FROM AD_ADCOFRYCONFIG EMP\r\n"
							+ "         INNER JOIN AD_ADCOFRYCONFIGITENS ITE ON ITE.CODEMP = EMP.CODEMP\r\n"
							+ "WHERE ITE.ANOREF = (SELECT max(DD.ANOREF) FROM AD_ADCOFRYCONFIGITENS DD WHERE DD.ATIVO = 'S')\r\n"
							+ "");

			ResultSet rsEmp = query.executeQuery();

			while (rsEmp.next()) {

				System.out.println("------------------------------------------------------");
				System.out.println("rsEmp.next()");
				System.out.println("------------------------------------------------------");

				urlCofry = rsEmp.getString("URLCOFRY");
				token = rsEmp.getString("TOKEN");

				Timestamp dataInicioFixa = rsEmp.getTimestamp("DATAINI");
				Timestamp dataFimFixa = rsEmp.getTimestamp("DTFIM");
				int intervaloMesesFixo = rsEmp.getInt("INTERVALODEMESESPESQUISA");

				Timestamp dataInicio = null;
				Timestamp dataFim = null;

				while (dataInicio == null || (dataInicio.before(dataFimFixa) || dataInicio.equals(dataFimFixa))) {
					if (dataInicio == null) {
						// Atribui os valores fixos de teste para dataInicio e dataFim na primeira
						// iteração
						dataInicio = dataInicioFixa;

						Calendar calendar = Calendar.getInstance();
						calendar.setTime(dataInicio);
						calendar.add(Calendar.MONTH, intervaloMesesFixo); // Adiciona o número de meses fixo
						calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)); // Último
																												// dia
																												// do
																												// mês
						dataFim = new Timestamp(calendar.getTimeInMillis());

					}

					System.out.println("Data de INICIO no LOOP é : " + dataInicio);
					System.out.println("Data de FIM no LOOP é : " + dataFim);

					// Chama o método sendPost com as datas
					CofryCargaVendaDTO cargaVendaDto = sendPost(urlCofry, token, dataInicio.toString(),
							dataFim.toString());

					if (cargaVendaDto != null && cargaVendaDto.getStore() != null) {

						for (CofryStoreCargaVendaDTO store : cargaVendaDto.getStore()) {
							try {

								if (SituacaoCashBackEnum.isTipoPermitido(store.getDescricaoSituacao())) {

									Collection existe = dwfFacade.findByDynamicFinder(new FinderWrapper("AD_COFRY",
											"UUIDMVRI = '" + store.getIdentificadorMovimentacaoInterna() + "'"));

									if (!existe.isEmpty()) {
										// System.out.println(" Encontrou dados ");
										// System.out.println(" store.getPayed(): " + store.getPayed());
										if (store.getPayed() != 0
												&& (store.getDescricaoSituacao().equals("Crédito Liberado") || store
														.getDescricaoSituacao().equals("Cr\\u00e9dito Liberado"))
										// SituacaoCashBackEnum.fromString(store.getDescricaoSituacao()).getCodigo().equals(SituacaoCashBackEnum.CREDITO_LIBERADO.getCodigo())
										// ||
										// SituacaoCashBackEnum.fromString(store.getDescricaoSituacao()).getCodigo().equals(SituacaoCashBackEnum.CREDITO_LIBERADO_ENCODE.getCodigo())
										) {
											System.out.println("Atualizando registro "
													+ store.getIdentificadorMovimentacaoInterna());
											// cofryService.atualizar(dadoVenda, store);

											PersistentLocalEntity prePersist = dwfFacade.findEntityByPrimaryKey(
													"AD_COFRY", store.getIdentificadorMovimentacaoInterna());
											EntityVO entityVO = prePersist.getValueObject();
											DynamicVO cofryVO = (DynamicVO) entityVO;

											cofryVO.setProperty("DTMVRI",
													Timestamp.valueOf(store.getDataMovimentacaoInterna()));
											cofryVO.setProperty("IDMAD", new BigDecimal(store.getIdMad()));
											cofryVO.setProperty("ADVERTISER", store.getAdvertiser());
											cofryVO.setProperty("DOCUMENT", store.getDocumento());

											if (store.getClientIdReceived() != null
													|| !store.getDatePayed().equals("null")) {
												cofryVO.setProperty("CLIENTIDRECEIVED", store.getClientIdReceived());

											}

											cofryVO.setProperty("NAMECOMPANY", store.getNomeCompanhia());
											cofryVO.setProperty("SITUATION", store.getDescricaoSituacao());
											cofryVO.setProperty("CLASSIFICATION", store.getDescricaoClassificacao());
											cofryVO.setProperty("TOTALORDER", store.getTotalOrder());
											cofryVO.setProperty("CASHBACK", store.getCashback());
											cofryVO.setProperty("CASHBACKCUSTOMER", store.getCashbackCustomer());
											cofryVO.setProperty("CASHBACKFORMAT",
													new BigDecimal(store.getCashbackFormat()));
											cofryVO.setProperty("CASHBACKFINAL", store.getCashbackFinal());
											cofryVO.setProperty("CUSTOMERBALANCE", store.getCustomerBalance());
											cofryVO.setProperty("PARTNERNAME", store.getPartnerName());

											if (store.getDataUltimaAtualizacao() != null
													|| !store.getDataUltimaAtualizacao().equals("null")) {

												cofryVO.setProperty("LASTUPDATE",
														Timestamp.valueOf(store.getDataUltimaAtualizacao()));

											}

											cofryVO.setProperty("PAYED", new BigDecimal(store.getPayed()));

											try {

												if (!store.getDatePayed().equals("null")
														|| store.getDatePayed() != null) {

													cofryVO.setProperty("DATEPAYED",
															Timestamp.valueOf(store.getDatePayed()));

												}

											} catch (NullPointerException e) {

											}
											prePersist.setValueObject(entityVO);

										}
									} else {

										EntityVO entityVO = dwfFacade.getDefaultValueObjectInstance("AD_COFRY");

										DynamicVO cofryVO = (DynamicVO) entityVO;

										cofryVO.setProperty("UUIDMVRI", store.getIdentificadorMovimentacaoInterna());
										cofryVO.setProperty("DTMVRI",
												Timestamp.valueOf(store.getDataMovimentacaoInterna()));
										cofryVO.setProperty("IDMAD", new BigDecimal(store.getIdMad()));
										cofryVO.setProperty("ADVERTISER", store.getAdvertiser());
										cofryVO.setProperty("DOCUMENT", store.getDocumento());
										if (store.getClientIdReceived() != null
												|| !store.getDatePayed().equals("null")) {
											cofryVO.setProperty("CLIENTIDRECEIVED", store.getClientIdReceived());
										}
										cofryVO.setProperty("NAMECOMPANY", store.getNomeCompanhia());
										cofryVO.setProperty("SITUATION", store.getDescricaoSituacao());
										cofryVO.setProperty("CLASSIFICATION", store.getDescricaoClassificacao());
										cofryVO.setProperty("TOTALORDER", store.getTotalOrder());
										cofryVO.setProperty("CASHBACK", store.getCashback());
										cofryVO.setProperty("CASHBACKCUSTOMER", store.getCashbackCustomer());
										cofryVO.setProperty("CASHBACKFORMAT",
												new BigDecimal(store.getCashbackFormat()));
										cofryVO.setProperty("CASHBACKFINAL", store.getCashbackFinal());
										cofryVO.setProperty("CUSTOMERBALANCE", store.getCustomerBalance());
										cofryVO.setProperty("PARTNERNAME", store.getPartnerName());
										
										if (store.getDataUltimaAtualizacao() != null
												|| !store.getDataUltimaAtualizacao().equals("null")) {
										cofryVO.setProperty("LASTUPDATE",
												Timestamp.valueOf(store.getDataUltimaAtualizacao()));
										}
										cofryVO.setProperty("PAYED", new BigDecimal(store.getPayed()));
										try {

											if (!store.getDatePayed().equals("null") || store.getDatePayed() != null) {

												cofryVO.setProperty("DATEPAYED",
														Timestamp.valueOf(store.getDatePayed()));

											}

										} catch (NullPointerException e) {

										}
										/*
										 * if (!store.getDatePayed().equals("null")) {
										 * 
										 * cofryVO.setProperty("DATEPAYED", Timestamp.valueOf( store.getDatePayed()));
										 * 
										 * }
										 */
										dwfFacade.createEntity("AD_COFRY", entityVO);

									}
								}
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					}

					dataInicio = dataFim;

					Calendar calendar = Calendar.getInstance();
					calendar.setTime(dataInicio);
					calendar.add(Calendar.MONTH, intervaloMesesFixo); // Adiciona o número de meses fixo
					calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)); // Último dia
																											// do mês
					dataFim = new Timestamp(calendar.getTimeInMillis());
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
	}

	public static CofryCargaVendaDTO sendPost(String newURL, String newApiKey, String newDataIni, String newDataFin)
			throws Exception {

		System.out.println("------------------------------------------------------");
		System.out.println("Entrou no objeto sendPost() ");
		System.out.println("------------------------------------------------------");

		CofryUtil obj = new CofryUtil(newURL, newApiKey);

		return obj.buscarCargaVendasEStatus(newDataIni, newDataFin);

	}

}
