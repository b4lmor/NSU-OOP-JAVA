package ru.nsu.ccfit.lisitsin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.ccfit.lisitsin.dto.request.DisconnectRequest;
import ru.nsu.ccfit.lisitsin.dto.request.LoginRequest;
import ru.nsu.ccfit.lisitsin.entity.User;
import ru.nsu.ccfit.lisitsin.repository.UserRepository;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;

    public void create(LoginRequest loginRequest) {
        User user = new User();
        user.setId(loginRequest.getAuthorId());
        user.setName(loginRequest.getName());
        user.setIsActive(true);
        userRepository.save(user);
    }

    public void disconnect(DisconnectRequest disconnectRequest) {
        userRepository.findByName(disconnectRequest.getName()).ifPresent(user -> {
            user.setIsActive(false);
            userRepository.save(user);
        });
    }

}
