package com.payment.payment;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.payment.payment.model.AccountDetails;
import com.payment.payment.model.CreatePayment;
import com.payment.payment.model.PaymentAttributes;
import net.minidev.json.JSONArray;
import org.hibernate.annotations.processing.SQL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.net.URI;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
//TODO
// Happy path e failed path. Where it fails prove it!
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentApplicationTests {
	@Autowired
	TestRestTemplate restTemplate;

	@BeforeEach
	void setupRestTemplate() {
		restTemplate = restTemplate.withBasicAuth("sarah1", "abc123");
	}

	@Test
	void getPaymentNotFound() {
		ResponseEntity<?> response = restTemplate.getForEntity("/v1/payments/11111111-1111-1111-1111-111111111111", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	@Sql(statements = "DELETE FROM payments", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void getPayment_exists() {
		ResponseEntity<String> response = restTemplate
//				.withBasicAuth("sarah1", "abc123")
				.getForEntity("/v1/payments/123e4567-e89b-12d3-a456-426614174000", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		System.out.println(response.getBody());

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		String id = documentContext.read("$.id");
		System.out.println(id);
		assertThat(id).isEqualTo("123e4567-e89b-12d3-a456-426614174000");

		String amount = documentContext.read("$.attributes.amount");
		System.out.println(amount);
		assertThat(amount).isEqualTo("100.50");
	}

	@Sql(statements = "DELETE FROM payments", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	@Test
	void createPayment() {
		UUID uuid = UUID.randomUUID();
		AccountDetails creditor = new AccountDetails();
		creditor.setAccountNumber("DE1234567890");

		AccountDetails debtor = new AccountDetails();
		debtor.setAccountNumber("US0987654321");

		PaymentAttributes attributes = new PaymentAttributes();
		attributes.setCurrency("USD");
		attributes.setAmount("1000.00");
		attributes.setDebtor(debtor);
		attributes.setCreditor(creditor);

		CreatePayment createPayment = new CreatePayment();
		createPayment.setId(uuid);
		createPayment.setAttributes(attributes);

		ResponseEntity<Void> createResponse = restTemplate.postForEntity("/v1/payments", createPayment, Void.class);

		System.out.println(createResponse.getBody());

		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI location = createResponse.getHeaders().getLocation();
		ResponseEntity<String> getResponse = restTemplate.getForEntity(location, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		System.out.println(getResponse.getBody());

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		String id = documentContext.read("$.id");
		assertThat(id).isEqualTo(uuid.toString());

		String type = documentContext.read("$.type");
		assertThat(type).isEqualTo("payments");
	}

	@Sql(statements = "DELETE FROM payments", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts = "/test-list-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void getFindAllPayments() {
		ResponseEntity<String> response = restTemplate.getForEntity("/v1/payments", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		System.out.println("Here");
		System.out.println(response);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		int lengthList = documentContext.read("$.length()");
		assertThat(lengthList).isEqualTo(3);
		JSONArray ids =  documentContext.read("$..id");
		assertThat(ids).containsExactlyInAnyOrder(
				"123e4567-e89b-12d3-a456-426614174000",
				"12345678-e89b-12d3-a456-426614174000",
				"87654321-e89b-12d3-a456-426614174000"
				);
	}

	@Sql(statements = "DELETE FROM payments", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts = "/test-list-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void shouldReturnAPageOfPayments() {
		ResponseEntity<String> response = restTemplate.getForEntity("/v1/payments?page=0&size=2", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		System.out.println(response.getBody());

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray page = documentContext.read("$.data[*]");
		System.out.println("JUMP");
		System.out.println(page);
		assertThat(page.size()).isEqualTo(2);

	}

//	@Test
//	void contextLoads() {
//	}

}
