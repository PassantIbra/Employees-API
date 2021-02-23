package com.employees.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.employees.challenge.dto.AddEmployeeReqDto;
import com.employees.challenge.dto.UpdateEmployeReqDto;
import com.employees.challenge.model.Employee.State;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChallengeApplicationTests {
	@LocalServerPort
	int randomServerPort;
	/**
	 * Testing adding new employee and updating his state.
	 *
	 * @throws URISyntaxException
	 * @throws InterruptedException
	 */
	@Test
	@Order(1)
	public void testA() throws URISyntaxException, InterruptedException {
		RestTemplate restTemplate = new RestTemplate();
		String baseUrl = "http://localhost:" + randomServerPort + "/employees/";
		URI uri = new URI(baseUrl);

		AddEmployeeReqDto employeeDto = new AddEmployeeReqDto();
		employeeDto.setName("passant1");
		employeeDto.setAdress("alex");
		employeeDto.setAge(32);
		employeeDto.setContractInfo("permenent contract");
		employeeDto.setEmail("passantEbra@gmail.com");
		employeeDto.setPhone("01281687779");

		HttpEntity<AddEmployeeReqDto> request = new HttpEntity<AddEmployeeReqDto>(employeeDto);

		ResponseEntity<AddEmployeeReqDto> result = restTemplate.postForEntity(uri, request, AddEmployeeReqDto.class);
		assertEquals(HttpStatus.CREATED, result.getStatusCode());

		TimeUnit.SECONDS.sleep(5);

		baseUrl = "http://localhost:" + randomServerPort + "/employees/";
		uri = new URI(baseUrl);

		UpdateEmployeReqDto updateEmployeeDto = new UpdateEmployeReqDto();
		updateEmployeeDto.setId(1);
		updateEmployeeDto.setState(State.ACTIVE);

		HttpEntity<UpdateEmployeReqDto> updaterequest = new HttpEntity<UpdateEmployeReqDto>(updateEmployeeDto);
		ResponseEntity upateResult = restTemplate.exchange(uri, HttpMethod.PUT, updaterequest, Void.class);

		assertEquals(200, upateResult.getStatusCodeValue());

	}

	/**
	 * Testing adding employee and adding another employee with the same data to
	 * test duplicate email validation.
	 * 
	 * @throws URISyntaxException
	 * @throws InterruptedException
	 */
	@Test
	@Order(2)
	public void testB() throws URISyntaxException, InterruptedException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:" + randomServerPort + "/employees/";
		URI uri = new URI(baseUrl);

		AddEmployeeReqDto employeeDto = new AddEmployeeReqDto();
		employeeDto.setName("passant1");
		employeeDto.setAdress("alex");
		employeeDto.setAge(32);
		employeeDto.setContractInfo("permenant contract");
		employeeDto.setEmail("passantE@gmail.com");
		employeeDto.setPhone("01281687779");

		HttpEntity<AddEmployeeReqDto> request = new HttpEntity<AddEmployeeReqDto>(employeeDto);

		ResponseEntity<AddEmployeeReqDto> result = restTemplate.postForEntity(uri, request, AddEmployeeReqDto.class);
		assertEquals(HttpStatus.CREATED, result.getStatusCode());

		TimeUnit.SECONDS.sleep(5);

		employeeDto = new AddEmployeeReqDto();
		employeeDto.setName("passant");
		employeeDto.setAdress("alex");
		employeeDto.setAge(32);
		employeeDto.setContractInfo("permenent contract");
		employeeDto.setEmail("passantE@gmail.com");
		employeeDto.setPhone("01281687779");

		request = new HttpEntity<AddEmployeeReqDto>(employeeDto);
		try {
			restTemplate.postForEntity(uri, request, AddEmployeeReqDto.class);
		} catch (HttpClientErrorException ex) {
			assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
			assertEquals(true, ex.getResponseBodyAsString().contains("email already exists"));
		}

	}


	/**
	 * Testing adding employee with blank email.
	 * 
	 * @throws URISyntaxException
	 */
	@Test
	@Order(3)
	public void testC() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:" + randomServerPort + "/employees/";
		URI uri = new URI(baseUrl);

		// request with blank email
		AddEmployeeReqDto employeeDto = new AddEmployeeReqDto();
		employeeDto.setName("passant");
		employeeDto.setAdress("alex");
		employeeDto.setAge(32);
		employeeDto.setContractInfo("permenent contract");
		employeeDto.setEmail("");
		employeeDto.setPhone("01281687779");

		HttpEntity<AddEmployeeReqDto> request = new HttpEntity<AddEmployeeReqDto>(employeeDto);
		try {
			restTemplate.postForEntity(uri, request, AddEmployeeReqDto.class);
		} catch (HttpClientErrorException ex) {
			assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
			assertEquals(true, ex.getResponseBodyAsString().contains("Validation Failed"));
		}

	}

	/**
	 * Testing adding employee with invalid phone.
	 * 
	 * @throws URISyntaxException
	 */
	@Test
	@Order(4)
	public void testD() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:" + randomServerPort + "/employees/";
		URI uri = new URI(baseUrl);

		AddEmployeeReqDto employeeDto = new AddEmployeeReqDto();
		employeeDto.setName("passant");
		employeeDto.setAdress("alex");
		employeeDto.setAge(32);
		employeeDto.setContractInfo("permenent contract");
		employeeDto.setEmail("pE@gmail.com");
		employeeDto.setPhone("0281687779");

		HttpEntity<AddEmployeeReqDto> request = new HttpEntity<AddEmployeeReqDto>(employeeDto);
		try {
			restTemplate.postForEntity(uri, request, AddEmployeeReqDto.class);
		} catch (HttpClientErrorException ex) {
			assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
			assertEquals(true, ex.getResponseBodyAsString()
					.contains("a valid 11 digit Egyption cell phone number is mandatory EX:01234567890"));
		}
	}

	/**
	 * Testing adding employee without name.
	 * 
	 * @throws URISyntaxException
	 */
	@Test
	@Order(5)
	public void testE() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:" + randomServerPort + "/employees/";
		URI uri = new URI(baseUrl);

		AddEmployeeReqDto employeeDto = new AddEmployeeReqDto();
		employeeDto.setAdress("alex");
		employeeDto.setAge(32);
		employeeDto.setContractInfo("permenent contract");
		employeeDto.setEmail("pEl@gmail.com");
		employeeDto.setPhone("01281687779");

		HttpEntity<AddEmployeeReqDto> request = new HttpEntity<AddEmployeeReqDto>(employeeDto);
		try {
			restTemplate.postForEntity(uri, request, AddEmployeeReqDto.class);
		} catch (HttpClientErrorException ex) {
			assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
			assertEquals(true, ex.getResponseBodyAsString().contains("please specify employee name"));
		}

	}

	/**
	 * testing updating employee with inexistent id.
	 * 
	 * @throws URISyntaxException
	 */

	@Test
	@Order(7)

	public void testG() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:" + randomServerPort + "/employees/";
		URI uri = new URI(baseUrl);

		UpdateEmployeReqDto employeeDto = new UpdateEmployeReqDto();
		employeeDto.setId(9);
		employeeDto.setState(State.ACTIVE);

		HttpEntity<UpdateEmployeReqDto> request = new HttpEntity<UpdateEmployeReqDto>(employeeDto);
		try {
			restTemplate.exchange(uri, HttpMethod.PUT, request, Void.class);
		} catch (HttpClientErrorException ex) {
			assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
			assertEquals(true, ex.getResponseBodyAsString().contains("Employee not found for this id :"));
		}

	}

	/**
	 * testing updating employee request without state.
	 * 
	 * @throws URISyntaxException
	 */
	@Test
	@Order(8)
	public void testH() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:" + randomServerPort + "/employees/";
		URI uri = new URI(baseUrl);

		UpdateEmployeReqDto employeeDto = new UpdateEmployeReqDto();
		employeeDto.setId(2);

		HttpEntity<UpdateEmployeReqDto> request = new HttpEntity<UpdateEmployeReqDto>(employeeDto);
		try {
			restTemplate.exchange(uri, HttpMethod.PUT, request, Void.class);
		} catch (HttpClientErrorException ex) {
			assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
			assertEquals(true, ex.getResponseBodyAsString().contains("please specify employee state"));
		}

	}

}
