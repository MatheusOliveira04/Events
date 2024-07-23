package com.eventos.api.controllers;

import com.eventos.api.models.user.User;
import com.eventos.api.models.user.UserDTO;
import com.eventos.api.services.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable UUID id) {
        User user = this.userService.findById(id);
        return ResponseEntity.ok(new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRoles()));
    }

    @Secured({"ROLE_USER"})
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(this.userService.findALl(pageable)
                    .stream()
                    .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRoles()))
                    .toList());
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> findByEmail(@PathVariable String email) {
      User user = this.userService.findByEmail(email).get();
      return ResponseEntity.ok(new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRoles()));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<UserDTO> insert(@RequestBody UserDTO userDTO, UriComponentsBuilder uriBuilder) {
        this.userService.insert(new User(null, userDTO.name(), userDTO.email(), userDTO.password(), userDTO.roles()));
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(userDTO.id()).toUri();
        return ResponseEntity.created(uri).body(userDTO);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    private ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO, @PathVariable UUID id) {
        this.userService.update(new User(id, userDTO.name(), userDTO.email(), userDTO.password(), userDTO.roles()));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(id).toUri();
        return ResponseEntity.ok().location(uri).body(userDTO);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
