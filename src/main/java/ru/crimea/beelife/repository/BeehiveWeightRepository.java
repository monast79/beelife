package ru.crimea.beelife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.crimea.beelife.model.BeehiveWeight;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface BeehiveWeightRepository extends JpaRepository<BeehiveWeight, Long> {

    List<BeehiveWeight> getBeehiveDetailsFromDate(@Param("beehiveId") Long beehiveId, @Param("fromDate")Timestamp fromDate);
}
