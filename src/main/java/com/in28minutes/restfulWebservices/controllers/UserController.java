package com.in28minutes.restfulWebservices.controllers;

import com.in28minutes.restfulWebservices.exceptions.UserNotFoundException;
import com.in28minutes.restfulWebservices.models.Post;
import com.in28minutes.restfulWebservices.models.User;
import com.in28minutes.restfulWebservices.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findUsers() {
        return userService.findUsers();
    }

    @GetMapping("/users/{id}")
    public EntityModel<Optional<User>> findUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        if (!user.isPresent())
            throw new UserNotFoundException("id-" + id);

        EntityModel<Optional<User>> model = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findUsers());
        model.add(linkTo.withRel("/all-users"));

        return model;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> saveUser(@Valid @RequestBody User user) {
        User savedUser = userService.saveUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> findUserPosts(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        if (!user.isPresent())
            throw new UserNotFoundException("id-" + id);
        List<Post> posts = user.get().getPosts();

        return posts;
    }
}
