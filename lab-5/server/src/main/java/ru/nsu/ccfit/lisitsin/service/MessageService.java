package ru.nsu.ccfit.lisitsin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.nsu.ccfit.lisitsin.dto.request.MessageRequest;
import ru.nsu.ccfit.lisitsin.entity.Message;
import ru.nsu.ccfit.lisitsin.repository.MessageRepository;
import ru.nsu.ccfit.lisitsin.repository.UserRepository;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageService {

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    public void create(MessageRequest messageRequest) {
        userRepository.findById(messageRequest.getAuthorId()).ifPresent(user -> {
            Message message = new Message();
            message.setUser(user);
            message.setText(messageRequest.getMessage());
            message.setUser(user);
            messageRepository.save(message);
        });
    }

    public Page<Message> findMessagesSortedByCreatedAt(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        return messageRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

}
