package com.eventos.api.services;

import com.eventos.api.models.user.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<User> findALl(Pageable pageable);

    User findById(UUID id);

    Optional<User> findByEmail(String email);

    User insert(User user);

    User update(User user);

    void delete(UUID id);
}
