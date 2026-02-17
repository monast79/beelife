package ru.crimea.beelife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.crimea.beelife.dto.ApiaryDto;
import ru.crimea.beelife.dto.BeehiveDto;
import ru.crimea.beelife.exception.PermissionDeniedException;
import ru.crimea.beelife.mapper.ApiaryMapper;
import ru.crimea.beelife.mapper.BeehiveMapper;
import ru.crimea.beelife.model.Apiary;
import ru.crimea.beelife.model.Beehive;
import ru.crimea.beelife.model.User;
import ru.crimea.beelife.repository.ApiaryRepository;
import ru.crimea.beelife.repository.BeehiveRepository;
import ru.crimea.beelife.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@Service
public class ApiaryServiceImpl extends BaseServiceImpl<Apiary, ApiaryDto> implements ApiaryService {

    @Autowired
    private ApiaryRepository apiaryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApiaryMapper apiaryMapper;
    @Autowired
    private BeehiveMapper beehiveMapper;

    @Autowired
    private BeehiveRepository beehiveRepository;

    @Override
    public List<ApiaryDto> getApiariesByUserId(Long userId) throws PermissionDeniedException {
        User user = userRepository.getReferenceById(userId);
        checkUserPermission(user);
        List<Apiary> apiaries = apiaryRepository.getApiariesByUser(user);

        return apiaryMapper.toDtoList(apiaries);
    }

    public boolean saveApiary(ApiaryDto apiaryDto) throws PermissionDeniedException {
        User user = userRepository.getReferenceById(apiaryDto.getUserId());
        checkUserPermission(user);

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
    public ApiaryDto findById(Long apiaryId) throws PermissionDeniedException {
        Apiary apiary = apiaryRepository.findApiaryById(apiaryId);
        checkUserPermission(apiary.getUser());
        return apiaryMapper.toDto(apiary);
    }

    @Override
    public void deleteById(Long apiaryId) throws PermissionDeniedException {
        checkUserPermission(getUserFromObject(apiaryId));
        apiaryRepository.deleteById(apiaryId);
    }

    @Override
    public Page<BeehiveDto> getBeehivesByApiaryId(Long apiaryId, Pageable pageable, String beehiveName) throws PermissionDeniedException {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        checkUserPermission(getUserFromObject(apiaryId));

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
    public User getUserFromObject(Long apiaryId) {
        Apiary apiary = apiaryRepository.findApiaryById(apiaryId);
        return apiary.getUser();
    }

}
