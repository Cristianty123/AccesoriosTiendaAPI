package com.tienda.accesorios.accesoriostiendaapi;

import com.tienda.accesorios.accesoriostiendaapi.dto.AditionalExpenseRequest;
import com.tienda.accesorios.accesoriostiendaapi.repository.AdditionalExpenseRepository;
import com.tienda.accesorios.accesoriostiendaapi.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
public class AdditionalExpenseIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AdditionalExpenseRepository repository;

    @Test
    void testCrearGastosAdicionales() {
        crearYGarantizar("Transporte", 12.00, "Costo de transporte del accesorio");
        crearYGarantizar("Importación", 18.25, "Impuestos por productos importados");
        crearYGarantizar("Instalación", 25.00, "Costo de instalación del accesorio en el vehículo");
    }

    void crearYGarantizar(String name, double expense, String description) {

        repository.findByNameAndExpense(name, expense)
                .ifPresent(repository::delete);

        AditionalExpenseRequest request = new AditionalExpenseRequest();
        request.setName(name);
        request.setExpense(expense);
        request.setDescription(description);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AditionalExpenseRequest> entity = new HttpEntity<>(request, headers);

        String baseUrl = "http://localhost:" + port + "/additionalExpenses/add";

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(repository.findByNameAndExpense(name, expense)).isPresent();
    }
}