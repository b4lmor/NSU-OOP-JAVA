package ru.nsu.ccfit.lisitsin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.entity.Message;

import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
}
