package ru.crimea.beelife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.crimea.beelife.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findByEnabled(boolean enabled);

    List<User> findByUsernameContainingIgnoreCaseAndEnabled(String username, boolean enabled);
}
