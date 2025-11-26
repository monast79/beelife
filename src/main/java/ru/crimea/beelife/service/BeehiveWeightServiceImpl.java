package ru.crimea.beelife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.crimea.beelife.dto.BeehiveDto;
import ru.crimea.beelife.dto.BeehiveWeightDto;
import ru.crimea.beelife.mapper.BeehiveMapper;
import ru.crimea.beelife.mapper.BeehiveWeightMapper;
import ru.crimea.beelife.model.Beehive;
import ru.crimea.beelife.model.BeehiveWeight;
import ru.crimea.beelife.repository.BeehiveRepository;
import ru.crimea.beelife.repository.BeehiveWeightRepository;

import java.util.Collections;
import java.util.List;

@Service
public class BeehiveWeightServiceImpl implements BeehiveWeightService {

    @Autowired
    private BeehiveWeightRepository beehiveWeightRepository;

    @Autowired
    private BeehiveWeightMapper beehiveWeightMapper;

    @Override
    public Page<BeehiveWeightDto> getAllByBeehiveId(Long beehiveId, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

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

}
