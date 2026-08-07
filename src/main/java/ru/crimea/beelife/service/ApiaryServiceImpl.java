package ru.crimea.beelife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.crimea.beelife.aop.DataAccess;
import ru.crimea.beelife.dto.ApiaryDto;
import ru.crimea.beelife.mapper.ApiaryMapper;
import ru.crimea.beelife.model.Apiary;
import ru.crimea.beelife.model.User;
import ru.crimea.beelife.repository.ApiaryRepository;
import ru.crimea.beelife.repository.UserRepository;

import java.util.List;

@Service
public class ApiaryServiceImpl extends BaseServiceImpl<Apiary, ApiaryDto> implements ApiaryService {

    @Autowired
    private ApiaryRepository apiaryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApiaryMapper apiaryMapper;

    @Override
    @DataAccess(value = "userId", isParent = true)
    public List<ApiaryDto> getApiariesByUserId(Long userId) {
        User user = userRepository.getReferenceById(userId);
        List<Apiary> apiaries = apiaryRepository.getApiariesByUser(user);

        return apiaryMapper.toDtoList(apiaries);
    }

    public boolean saveApiary(ApiaryDto apiaryDto) {
        User user = userRepository.getReferenceById(apiaryDto.getUserId());

        Apiary apiaryDb = apiaryRepository.findApiaryByNameAndUserId(apiaryDto.getName(), apiaryDto.getUserId());

        if (apiaryDb != null) {
            return false;
        }
        Apiary apiary = apiaryMapper.toModel(apiaryDto);
        apiary.setUser(user);
        apiaryRepository.save(apiary);
        return true;
    }

    @Override
    public ApiaryDto findDtoById(Long apiaryId) {
        Apiary apiary = apiaryRepository.findApiaryById(apiaryId);
        return apiaryMapper.toDto(apiary);
    }

    @Override
    public Apiary findById(Long apiaryId) {
        return  apiaryRepository.findApiaryById(apiaryId);
    }

    @Override
    @DataAccess
    public void deleteById(Long apiaryId) {
        apiaryRepository.deleteById(apiaryId);
    }

    @Override
    public User getUserFromObjectId(Long apiaryId) {
        Apiary apiary = apiaryRepository.findApiaryById(apiaryId);
        return apiary.getUser();
    }

    @Override
    public User getUserFromParentObjectId(Long userId) {
        return userRepository.getReferenceById(userId);
    }

}
