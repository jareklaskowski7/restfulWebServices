package com.in28minutes.restfulWebservices.controllers;

import com.in28minutes.restfulWebservices.exceptions.UserNotFoundException;
import com.in28minutes.restfulWebservices.models.Post;
import com.in28minutes.restfulWebservices.models.User;
import com.in28minutes.restfulWebservices.services.PostService;
import com.in28minutes.restfulWebservices.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class PostController {
    private PostService postService;
    private UserService userService;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Object> savePost(@PathVariable Long id, @RequestBody Post post) {
        Optional<User> user = userService.findUserById(id);
        if (!user.isPresent())
            throw new UserNotFoundException("id-" + id);
        post.setUser(user.get());
        postService.savePost(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }
}
