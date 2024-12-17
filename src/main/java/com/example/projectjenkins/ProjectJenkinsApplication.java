package com.example.projectjenkins;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class ProjectJenkinsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectJenkinsApplication.class, args);
    }
}

@RestController
@RequestMapping("/api")
class GreetingController {

    private final List<User> users = new ArrayList<>();

    @GetMapping("/greet")
    public String greet(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        users.add(user);
        return user;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return users;
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst();
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable int id) {
        boolean removed = users.removeIf(user -> user.getId() == id);
        return removed ? "User removed successfully." : "User not found.";
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.set(i, updatedUser);
                return "User updated successfully.";
            }
        }
        return "User not found.";
    }
}

class User {
    private int id;
    private String name;
    private String email;

    public User() {
    }

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
