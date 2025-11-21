package ru.crimea.beelife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.crimea.beelife.dto.BeehiveDto;
import ru.crimea.beelife.mapper.BeehiveMapper;
import ru.crimea.beelife.model.Apiary;
import ru.crimea.beelife.model.Beehive;
import ru.crimea.beelife.model.HiveType;
import ru.crimea.beelife.repository.ApiaryRepository;
import ru.crimea.beelife.repository.BeehiveRepository;

import java.util.Collections;
import java.util.List;

@Service
public class BeehiveServiceImpl implements BeehiveService {

    @Autowired
    private BeehiveRepository beehiveRepository;
    @Autowired
    private BeehiveMapper beehiveMapper;
    @Autowired
    private ApiaryRepository apiaryRepository;

    @Override
    public Page<BeehiveDto> getBeehiveByApiaryId(Long apiaryId, Pageable pageable, String beehiveName) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Beehive> beehives = beehiveRepository.getBeehivesByApiaryId(apiaryId);
        List<BeehiveDto> beehiveDtos = beehiveMapper.toDtoList(beehives);
        List<BeehiveDto> list;
        if (beehiveDtos.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, beehiveDtos.size());
            list = beehiveDtos.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), beehiveDtos.size());

    }

    @Override
    public BeehiveDto saveBeehive(BeehiveDto beehiveDto) {
        Beehive beehive = beehiveRepository.findBeehiveByNameAndApiaryId(beehiveDto.getName(), beehiveDto.getApiaryId());
        if (beehive != null) {
            beehive.setDescription(beehiveDto.getDescription());
            beehive.setName(beehiveDto.getName());
            beehive.setHiveType(HiveType.valueOf(beehiveDto.getType()));
        } else {
            Apiary apiary = apiaryRepository.findApiaryById(beehiveDto.getApiaryId());
            beehive = beehiveMapper.toModel(beehiveDto);
            beehive.setApiary(apiary);
        }

        beehiveRepository.save(beehive);
        return beehiveMapper.toDto(beehive);
    }

    @Override
    public void deleteById(Long beehiveId) {
        beehiveRepository.deleteById(beehiveId);
    }

    @Override
    public BeehiveDto findById(Long beehiveId) {
        Beehive beehive = beehiveRepository.findBeehiveById(beehiveId);
        return beehiveMapper.toDto(beehive);
    }
}
