package ru.crimea.beelife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.crimea.beelife.model.BeehiveWeight;

import java.util.List;

@Repository
public interface BeehiveWeightRepository   extends JpaRepository<BeehiveWeight, Long> {

    List<BeehiveWeight> getAllByBeehiveId(Long beehiveId);
}
