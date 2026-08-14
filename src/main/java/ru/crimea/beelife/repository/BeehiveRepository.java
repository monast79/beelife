package ru.crimea.beelife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.crimea.beelife.model.Beehive;

import java.util.List;

public interface BeehiveRepository extends JpaRepository<Beehive, Long> {
    List<Beehive> getBeehivesByApiaryId(Long apiaryId);

    Beehive findBeehiveByNameAndApiaryId(String name, Long apiaryId);

    Beehive findBeehiveById(Long id);
}
