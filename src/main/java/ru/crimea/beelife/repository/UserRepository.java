package ru.crimea.beelife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.crimea.beelife.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
