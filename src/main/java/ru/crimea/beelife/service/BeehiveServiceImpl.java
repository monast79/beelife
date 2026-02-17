package ru.crimea.beelife.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import ru.crimea.beelife.dto.BeehiveDto;
import ru.crimea.beelife.exception.PermissionDeniedException;
import ru.crimea.beelife.mapper.BeehiveMapper;
import ru.crimea.beelife.model.Apiary;
import ru.crimea.beelife.model.Beehive;
import ru.crimea.beelife.model.HiveType;
import ru.crimea.beelife.model.User;
import ru.crimea.beelife.repository.ApiaryRepository;
import ru.crimea.beelife.repository.BeehiveRepository;

@Service
public class BeehiveServiceImpl extends BaseServiceImpl<Beehive, BeehiveDto> implements BeehiveService {

    @Autowired
    private BeehiveRepository beehiveRepository;
    @Autowired
    private BeehiveMapper beehiveMapper;
    @Autowired
    private ApiaryRepository apiaryRepository;

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
    public void deleteById(Long beehiveId) throws PermissionDeniedException {
        checkUserPermission(getUserFromObject(beehiveId));
        beehiveRepository.deleteById(beehiveId);
    }

    @Override
    public BeehiveDto findById(Long beehiveId) throws PermissionDeniedException {
        Beehive beehive = beehiveRepository.findBeehiveById(beehiveId);
        checkUserPermission(beehive.getApiary().getUser());
        return beehiveMapper.toDto(beehive);
    }

    @Override
    public User getUserFromObject(Long beehiveId) {
        Beehive beehive = beehiveRepository.findBeehiveById(beehiveId);
        return beehive.getApiary().getUser();
    }
}
