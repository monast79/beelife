package ru.crimea.beelife.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.crimea.beelife.aop.DataAccess;
import ru.crimea.beelife.dto.BeehiveWeightDto;
import ru.crimea.beelife.exception.PermissionDeniedException;
import ru.crimea.beelife.mapper.BeehiveWeightMapper;
import ru.crimea.beelife.model.Beehive;
import ru.crimea.beelife.model.BeehiveWeight;
import ru.crimea.beelife.model.User;
import ru.crimea.beelife.repository.BeehiveRepository;
import ru.crimea.beelife.repository.BeehiveWeightRepository;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class BeehiveWeightServiceImpl extends BaseServiceImpl<BeehiveWeight, BeehiveWeightDto> implements BeehiveWeightService {

    @Autowired
    private BeehiveWeightRepository beehiveWeightRepository;

    @Autowired
    private BeehiveRepository beehiveRepository;

    @Autowired
    private BeehiveWeightMapper beehiveWeightMapper;

    @DataAccess(value = "beehiveId", isParent = true)
    @Override
    public List<BeehiveWeightDto> getAllByBeehiveId(Long beehiveId, Date fromDate) throws PermissionDeniedException {

        List<BeehiveWeight> beehiveWeights = beehiveWeightRepository.getBeehiveDetailsFromDate(beehiveId, new java.sql.Timestamp(fromDate.getTime()));
        List<BeehiveWeightDto> beehiveWeightDtos = beehiveWeightMapper.toDtoList(beehiveWeights);

        return beehiveWeightDtos;
    }

    @Override
    public User getUserFromObjectId(Long beehiveWeightId) {
        return null;
    }

    @Override
    public User getUserFromParentObjectId(Long beehiveId) {
        Beehive beehive = beehiveRepository.findBeehiveById(beehiveId);
        return beehive.getApiary().getUser();
    }

    @Override
    public BeehiveWeightDto findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
