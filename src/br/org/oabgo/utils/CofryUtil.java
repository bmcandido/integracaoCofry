package br.org.oabgo.utils;

import java.util.HashMap;
import java.util.Map;

import br.org.oabgo.dto.CofryCargaVendaDTO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CofryUtil {
	
	private String urlApiCofry;
	private String tokenApiCofry;
	
	public CofryUtil(String urlApiCofry, String tokenApiCofry) {
		this.urlApiCofry = urlApiCofry;
		this.tokenApiCofry = tokenApiCofry;
	}

	/**
	 * @since metodo responsável por buscar lista de cargas cashback venda e seus status
	 * @param dataInicio - YYYY-MM-DD
	 * @param dataFinal - YYYY-MM-DD
	 * @return 
	 * @return List<CofryCargaVendaDTO>
	 */
	public CofryCargaVendaDTO buscarCargaVendasEStatus(String dataInicio, String dataFinal) throws Exception {
		try {
						
			System.out.println("dataInicio:" + dataInicio);
			System.out.println("dataFinal:" + dataFinal);
			System.out.println("tokenApiCofry:" + tokenApiCofry);
			System.out.println("urlApiCofry:" + urlApiCofry);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			//String Json = "";
			
			// Busca novo Token
			Map<String, String> headers = new HashMap<>();
		    headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
			
            HttpPostMultipart multipart = new HttpPostMultipart( urlApiCofry , "utf-8", headers);
            multipart.addFormField("startDate", dataInicio);
            multipart.addFormField("endDate", dataFinal);
            multipart.addFormField("token", tokenApiCofry);
            
            System.out.println("*************************************");
            System.out.println("O problema pode estár por aqui");
            System.out.println("*************************************");
            
            
            String response = multipart.finish();  
            
            System.out.println("*************************************");
            System.out.println("response COFRY : " + response.toString() );
            System.out.println("*************************************");
            
			return gson.fromJson( response , CofryCargaVendaDTO.class);

		} catch (Exception e) {
			e.printStackTrace();
			return new CofryCargaVendaDTO();
			//return " ";
		}
	}

}

