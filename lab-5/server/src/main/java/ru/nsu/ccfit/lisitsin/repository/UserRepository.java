package ru.nsu.ccfit.lisitsin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findFirstByNameOrderByLastActiveAtDesc(String name);

    List<User> findByIsActiveTrue();

}
