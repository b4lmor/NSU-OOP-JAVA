package ru.nsu.ccfit.lisitsin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.ccfit.lisitsin.dto.request.MessagePageRequest;
import ru.nsu.ccfit.lisitsin.dto.request.MessageRequest;
import ru.nsu.ccfit.lisitsin.entity.Message;
import ru.nsu.ccfit.lisitsin.repository.MessageRepository;
import ru.nsu.ccfit.lisitsin.repository.UserRepository;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageService {

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    @Transactional
    public void create(MessageRequest messageRequest) {
        userRepository.findById(messageRequest.getAuthorId()).ifPresent(user -> {
            Message message = new Message();
            message.setUser(user);
            message.setText(messageRequest.getMessage());
            message.setId(UUID.randomUUID());
            message.setCreatedAt(new Date());
            messageRepository.save(message);
        });
    }

    public Page<Message> findMessagesSortedByCreatedAt(MessagePageRequest messagePageRequest) {
        Pageable pageable = PageRequest.of(
                messagePageRequest.getPageNumber(),
                messagePageRequest.getPageSize(),
                Sort.by("createdAt").descending()
        );
        return messageRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

}
