package ru.crimea.beelife.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.crimea.beelife.aop.DataAccess;
import ru.crimea.beelife.dto.BeehiveDto;
import ru.crimea.beelife.exception.PermissionDeniedException;
import ru.crimea.beelife.mapper.BeehiveMapper;
import ru.crimea.beelife.model.Apiary;
import ru.crimea.beelife.model.Beehive;
import ru.crimea.beelife.model.HiveType;
import ru.crimea.beelife.model.User;
import ru.crimea.beelife.repository.ApiaryRepository;
import ru.crimea.beelife.repository.BeehiveRepository;

import java.util.Collections;
import java.util.List;

@Service
public class BeehiveServiceImpl extends BaseServiceImpl<Beehive, BeehiveDto> implements BeehiveService {

    @Autowired
    private BeehiveRepository beehiveRepository;
    @Autowired
    private BeehiveMapper beehiveMapper;
    @Autowired
    private ApiaryRepository apiaryRepository;

    @Override
    @DataAccess(value = "apiaryId", isParent = true)
    @Cacheable("apiary_beehives")
    public Page<BeehiveDto> getBeehivesByApiaryId(Long apiaryId, Pageable pageable, String beehiveName)  throws PermissionDeniedException {
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
    @CacheEvict(value = "apiary_beehives", allEntries = true)
    public BeehiveDto saveBeehive(BeehiveDto beehiveDto) {
        Beehive beehive = beehiveRepository.findBeehiveByNameAndApiaryId(beehiveDto.getName(), beehiveDto.getApiaryId());
        if (beehive != null) {
            beehive.setDescription(beehiveDto.getDescription());
            beehive.setName(beehiveDto.getName());
            beehive.setHiveType(HiveType.valueOf(beehiveDto.getType()));
        } else {
            beehive = beehiveMapper.toModel(beehiveDto);
        }

        beehiveRepository.save(beehive);
        return beehiveMapper.toDto(beehive);
    }

    @Override
    @DataAccess
    @CacheEvict(value = "apiary_beehives", allEntries = true)
    public void deleteById(Long beehiveId) {
        beehiveRepository.deleteById(beehiveId);
    }

    @Override
    public BeehiveDto findDtoById(Long beehiveId) {
        Beehive beehive = beehiveRepository.findBeehiveById(beehiveId);
        return beehiveMapper.toDto(beehive);
    }

    @Override
    public User getUserFromObjectId(Long beehiveId) {
        Beehive beehive = beehiveRepository.findBeehiveById(beehiveId);
        return beehive.getApiary().getUser();
    }

    @Override
    public User getUserFromParentObjectId(Long apiaryId) {
        Apiary apiary = apiaryRepository.findApiaryById(apiaryId);
        return apiary.getUser();
    }

    @Override
    public Beehive findById(Long id) {
        return beehiveRepository.findBeehiveById(id);
    }
}
