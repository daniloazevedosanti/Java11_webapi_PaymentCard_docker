package com.paymentcard.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.stereotype.Service;

@Service
public class ProgramServices {

	//PA should receive amount + card data and return response’s ID, result_code and description
	public String requestPA(String amount, String number, String expireMonth, String expireYear, String cardHolder,
			String brandCard, String currency, String cvv) throws IOException {

		URL url = new URL("https://test.oppwa.com/v1/payments");

		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
	        conn.setRequestProperty("Authorization", "Bearer OGFjN2E0Y2E2ZGI5N2VmMTAxNmRiZTkwZTMxZDBhYTh8WENkRUs0NHJubQ==");
		conn.setDoInput(true);
		conn.setDoOutput(true);

		String data = ""
			+ "entityId=8ac7a4ca6db97ef1016dbe9214e70aac"
			+ "&amount="+amount
			+ "&currency="+currency
			+ "&paymentBrand="+brandCard
			+ "&paymentType=PA"
			+ "&card.number="+number
			+ "&card.holder="+cardHolder
			+ "&card.expiryMonth="+expireMonth
			+ "&card.expiryYear="+expireYear
			+ "&card.cvv="+cvv
			+ "&recurringType=INITIAL"
			+ "&createRegistration=true";

		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(data);
		wr.flush();
		wr.close();
		int responseCode = conn.getResponseCode();
		InputStream is;

		if (responseCode >= 400) is = conn.getErrorStream();
		else is = conn.getInputStream();

		 String result = new BufferedReader(new
		 InputStreamReader(is)).lines().collect(Collectors.joining("\n"));
		 return result.toString();

		//return IOUtils.toString(is);
	}
	
	
	//CP should receive amount + id and return response’s ID, result_code and description
	//RF should receive amount + id and return response’s id, result_code and description
	public String requestCPorRF(String id, String amount, String type) throws IOException {
		URL url = new URL("https://test.oppwa.com/v1/payments/{id}");

		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
	        conn.setRequestProperty("Authorization", "Bearer OGFjN2E0Y2E2ZGI5N2VmMTAxNmRiZTkwZTMxZDBhYTh8WENkRUs0NHJubQ==");
		conn.setDoInput(true);
		conn.setDoOutput(true);

		String data = ""
			+ "entityId="+id
			+ "&amount="+amount
			+ "&currency=BRL"
			+ "&paymentType="+type;

		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(data);
		wr.flush();
		wr.close();
		int responseCode = conn.getResponseCode();
		InputStream is;

		if (responseCode >= 400) is = conn.getErrorStream();
		else is = conn.getInputStream();
		
		String result = new BufferedReader(new
				 InputStreamReader(is)).lines().collect(Collectors.joining("\n"));
		return result.toString();
		//return IOUtils.toString(is);
	}

	
}
