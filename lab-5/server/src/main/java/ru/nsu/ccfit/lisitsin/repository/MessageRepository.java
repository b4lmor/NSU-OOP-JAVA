package ru.nsu.ccfit.lisitsin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findAllByOrderByCreatedAtDesc(Pageable pageable);

}
