package ru.nsu.ccfit.lisitsin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.ccfit.lisitsin.dto.request.DisconnectRequest;
import ru.nsu.ccfit.lisitsin.dto.request.LoginRequest;
import ru.nsu.ccfit.lisitsin.entity.User;
import ru.nsu.ccfit.lisitsin.repository.UserRepository;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void create(LoginRequest loginRequest) {
        User user = new User();
        user.setId(loginRequest.getAuthorId());
        user.setName(loginRequest.getName());
        user.setIsActive(true);
        user.setLastActiveAt(new Date());
        userRepository.save(user);
    }

    @Transactional
    public void update(UUID id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setLastActiveAt(new Date());
            userRepository.save(user);
        });
    }

    public boolean isLoginTaken(LoginRequest loginRequest) {
        var user = userRepository.findFirstByNameOrderByLastActiveAtDesc(loginRequest.getName());
        return user.isPresent() && user.get().getIsActive();
    }

    @Transactional
    public void disconnect(DisconnectRequest disconnectRequest) {
        userRepository.findFirstByNameOrderByLastActiveAtDesc(disconnectRequest.getName()).ifPresent(user -> {
            user.setIsActive(false);
            userRepository.save(user);
        });
    }

    @Transactional
    public void disconnect(UUID id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setIsActive(false);
            userRepository.save(user);
        });
    }

}
