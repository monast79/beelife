package ru.crimea.beelife.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.crimea.beelife.config.MyPasswordEncoder;
import ru.crimea.beelife.dto.UserDto;
import ru.crimea.beelife.mapper.UserMapper;
import ru.crimea.beelife.model.Role;
import ru.crimea.beelife.model.User;
import ru.crimea.beelife.repository.RoleRepository;
import ru.crimea.beelife.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class UserService implements UserDetailsService {


    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    MyPasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public Page<UserDto> allActiveUsers(Pageable pageable, String username) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<User> users;
        if (username == null) {
            users = userRepository.findByEnabled(Boolean.TRUE);
        } else {
            users = userRepository.findByUsernameContainingIgnoreCaseAndEnabled(username, Boolean.TRUE);
        }

        List<UserDto> userDtos = userMapper.toDtoList(users);
        List<UserDto> list;
        if (userDtos.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, userDtos.size());
            list = userDtos.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), userDtos.size());
    }

    public boolean saveUser(UserDto userDto) {
        User userFromDB = userRepository.findByUsername(userDto.getUsername());

        if (userFromDB != null) {
            return false;
        }
        Role role = roleRepository.getRoleByName("USER");
        if (role == null) {
            return false;
        }
        User user = userMapper.toModel(userDto);

        user.setRoles(Collections.singleton(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        return true;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public UserDto getUser(Long userId) {
        User user = userRepository.getReferenceById(userId);
        return userMapper.toDto(user);
    }

    public void updateUser(UserDto userDto) {
        User userFromDB = userRepository.findByUsername(userDto.getUsername());

        if (userFromDB == null) {
            throw new UsernameNotFoundException("User not found");
        } else {
            userFromDB.setFirstName(userDto.getFirstName());
            userFromDB.setLastName(userDto.getFirstName());
        }
        userRepository.save(userFromDB);
    }
}
