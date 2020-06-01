package com.paymentcard.resources;

import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.paymentcard.services.ProgramServices;

@RestController
@RequestMapping(value = "/app")
public class ProgramResource {

	@Autowired
	private ProgramServices service;
	
	//PA
	@PostMapping(value="/pa")
	public ResponseEntity<String> insertPA(@RequestBody String number, String amount, String expireMonth,
			String expireYear, String cardHolder, String brandCard, String currency, String cvv, String CPF) throws IOException {
		
		String obj = service.requestPA(number, amount, expireMonth, expireYear, cardHolder, brandCard, currency, cvv);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("").buildAndExpand(obj).toUri();
		return ResponseEntity.created(uri).body(obj);
	}

	//CP
	@PostMapping(value = "/cp/{id}")

	public ResponseEntity<String> insertCP(@RequestBody String id, String amount) throws IOException {
		String obj = service.requestCPorRF(id, amount, "CP");
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj).toUri();
		return ResponseEntity.created(uri).body(obj);
	}

	//RF
	@PostMapping(value = "/rf/{id}")
	public ResponseEntity<String> insertRF(@RequestBody String id, String amount) throws IOException {
		String obj = service.requestCPorRF(id, amount, "RF");
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj).toUri();
		return ResponseEntity.created(uri).body(obj);
	}

}
