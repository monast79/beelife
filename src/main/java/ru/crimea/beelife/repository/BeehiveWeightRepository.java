package ru.crimea.beelife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ru.crimea.beelife.model.BeehiveWeight;

import java.sql.Timestamp;
import java.util.List;

public interface BeehiveWeightRepository extends JpaRepository<BeehiveWeight, Long> {

    List<BeehiveWeight> getBeehiveDetailsFromDate(@Param("beehiveId") Long beehiveId, @Param("fromDate")Timestamp fromDate);
}
