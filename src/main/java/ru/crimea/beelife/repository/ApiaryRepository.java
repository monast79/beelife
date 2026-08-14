package ru.crimea.beelife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.crimea.beelife.model.Apiary;
import ru.crimea.beelife.model.User;

import java.util.List;

public interface ApiaryRepository extends JpaRepository<Apiary, Long> {
    List<Apiary> getApiariesByUser(User user);

    Apiary findApiaryByNameAndUserId(String apiaryName, Long userId);

    Apiary findApiaryById(Long apiaryId);
}
