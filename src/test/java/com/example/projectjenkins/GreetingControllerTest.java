package com.example.projectjenkins;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GreetingControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGreetEndpoint() {
        // Test GET /api/greet avec un paramètre "name"
        ResponseEntity<String> response = restTemplate.getForEntity("/api/greet?name=John", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Hello, John!");
    }

    @Test
    void testAddUserEndpoint() {
        // Test POST /api/users
        Map<String, Object> user = new HashMap<>();
        user.put("id", 1);
        user.put("name", "Alice");
        user.put("email", "alice@example.com");

        ResponseEntity<Map> response = restTemplate.postForEntity("/api/users", user, Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsEntry("name", "Alice");
    }

    @Test
    void testGetAllUsersEndpoint() {
        // Test GET /api/users
        ResponseEntity<Object[]> response = restTemplate.getForEntity("/api/users", Object[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testGetUserByIdEndpoint() {
        // Ajouter un utilisateur
        Map<String, Object> user = new HashMap<>();
        user.put("id", 2);
        user.put("name", "Bob");
        user.put("email", "bob@example.com");
        restTemplate.postForEntity("/api/users", user, Map.class);

        // Récupérer l'utilisateur par ID
        ResponseEntity<Map> response = restTemplate.getForEntity("/api/users/2", Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsEntry("name", "Bob");
    }



    @Test
    void testUpdateUserEndpoint() {
        // Ajouter un utilisateur
        Map<String, Object> user = new HashMap<>();
        user.put("id", 4);
        user.put("name", "David");
        user.put("email", "david@example.com");
        restTemplate.postForEntity("/api/users", user, Map.class);

        // Mettre à jour l'utilisateur
        Map<String, Object> updatedUser = new HashMap<>();
        updatedUser.put("id", 4);
        updatedUser.put("name", "David Updated");
        updatedUser.put("email", "david_updated@example.com");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(updatedUser, headers);

        ResponseEntity<String> response = restTemplate.exchange("/api/users/4", HttpMethod.PUT, requestEntity, String.class);
        assertThat(response.getBody()).isEqualTo("User updated successfully.");
    }
}
