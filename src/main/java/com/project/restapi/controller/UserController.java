package com.project.restapi.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.restapi.exception.ResourceNotFoundException;
import com.project.restapi.model.User;
import com.project.restapi.repository.UserRepository;

@RestController
@RequestMapping("api/v1")
public class UserController {
    @Autowired
    private UserRepository userRepository;


    /*
     * Get all users
     * Return a list of users
     */
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /*
     * Get user by id
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) throws ResourceNotFoundException {
        final Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new ResourceNotFoundException("use could not be found");
        }
        return ResponseEntity.ok().body(user.get());
    }

    /*
     * Create a new User
     */
    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    /*
     * Update the user by id
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User userDetails, @PathVariable UUID id) throws ResourceNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new ResourceNotFoundException("use could not be found");
        }

        User user = userOptional.get();
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setCreatedAt(new Date());

        User userSaved = userRepository.save(user);
        return ResponseEntity.ok(userSaved);
    }

    /*
     * Delete a user by id
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) throws ResourceNotFoundException {
        Optional<User> userToDelete = userRepository.findById(id);
        if (!userToDelete.isPresent()) {
            throw new ResourceNotFoundException("User not found : " + id);
        }
        userRepository.delete(userToDelete.get());
        return ResponseEntity.noContent().build();
    }

}
