package ru.crimea.beelife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.crimea.beelife.dto.BeehiveDto;
import ru.crimea.beelife.dto.BeehiveWeightDto;
import ru.crimea.beelife.exception.PermissionDeniedException;
import ru.crimea.beelife.mapper.BeehiveMapper;
import ru.crimea.beelife.mapper.BeehiveWeightMapper;
import ru.crimea.beelife.model.Beehive;
import ru.crimea.beelife.model.BeehiveWeight;
import ru.crimea.beelife.model.User;
import ru.crimea.beelife.repository.BeehiveRepository;
import ru.crimea.beelife.repository.BeehiveWeightRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BeehiveWeightServiceImpl extends BaseServiceImpl<BeehiveWeight, BeehiveWeightDto> implements BeehiveWeightService {

    @Autowired
    private BeehiveWeightRepository beehiveWeightRepository;

    @Autowired
    private BeehiveRepository beehiveRepository;

    @Autowired
    private BeehiveWeightMapper beehiveWeightMapper;

    @Override
    public Page<BeehiveWeightDto> getAllByBeehiveId(Long beehiveId, Pageable pageable) throws PermissionDeniedException {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        Beehive beehive = beehiveRepository.findBeehiveById(beehiveId);
        checkUserPermission(beehive.getApiary().getUser());

        List<BeehiveWeight> beehiveWeights = beehiveWeightRepository.getAllByBeehiveId(beehiveId);
        List<BeehiveWeightDto> beehiveWeightDtos = beehiveWeightMapper.toDtoList(beehiveWeights);
        List<BeehiveWeightDto> list;
        if (beehiveWeightDtos.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, beehiveWeightDtos.size());
            list = beehiveWeightDtos.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), beehiveWeightDtos.size());
    }

    @Override
    public User getUserFromObject(Long beehiveId) {
        return null;
    }

    @Override
    public BeehiveWeightDto findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
