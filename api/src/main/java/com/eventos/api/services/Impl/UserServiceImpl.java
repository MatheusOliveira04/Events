package com.eventos.api.services.Impl;

import com.eventos.api.models.user.User;
import com.eventos.api.repositories.UserRepository;
import com.eventos.api.services.UserService;
import com.eventos.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findALl(Pageable pageable) {
        Page<User> userList = this.userRepository.findAll(pageable);
        if(userList.isEmpty()) {
            throw new ObjectNotFoundException("Nothing user found");
        }
        return userList.toList();
    }

    @Override
    public User findById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Did not find user with id: " + id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);
        if(user.isPresent()) {
            return user;
        }
        throw new ObjectNotFoundException("Did not find user with email: " + email);
    }

    @Override
    public User insert(User user) {
        this.validationsOfUserFields(user);
    return this.userRepository.save(user);
    }

    @Override
    public User update(User user) {
        this.validationsOfUserFields(user);
        return this.userRepository.save(user);
    }

    @Override
    public void delete(UUID id) {
        this.userRepository.delete(this.findById(id));
    }

    private void validationsOfUserFields(User user) {
        this.validEmailField(user);
    }

    private void validEmailField(User user) {
        this.verifyIfEmailIsEmptyThrowException(user);
    }

    private void verifyIfEmailIsEmptyThrowException(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ObjectNotFoundException("Email is required");
        }
    }
}
