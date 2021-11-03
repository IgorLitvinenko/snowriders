package com.snowriders.service.impl;

import com.snowriders.exception.DuplicateEntryException;
import com.snowriders.mapper.UserMapper;
import com.snowriders.model.entities.AppUser;
import com.snowriders.model.entities.Role;
import com.snowriders.model.request.UserRequest;
import com.snowriders.model.response.UserResponse;
import com.snowriders.model.util.UserDetailsImpl;
import com.snowriders.repository.RoleRepository;
import com.snowriders.repository.UserRepository;
import com.snowriders.repository.UserRoleRepository;
import com.snowriders.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserRoleRepository userRoleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        AppUser user = userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Username %s not found", username))
        );
        return new UserDetailsImpl(user);
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        String email = request.getUsername();

        if (userRepository.findByEmail(email).isPresent()) {
            log.error("Email {}, already used", email);
            throw new DuplicateEntryException(String.format("Email %s, already used", email));
        }
        AppUser user = userMapper.toUser(request);
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

        //todo add mapper

        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void addRoleToUser(Long userId, String roleName) {
        AppUser user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("User with id {}, not found", userId);
            return new UsernameNotFoundException(String.format("User with id %s, not found", userId));
        });
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> {
            log.error("Role {} not found", roleName);
            return new UsernameNotFoundException(String.format("Role %s not found", roleName));
        });
        //todo mapper

        userRoleRepository.save(userMapper.createUserRole(user, role));
    }

    @Override
    public UserResponse getUser(Long id) {
        //todo remove try catch block

        AppUser user = userRepository.findById(id).orElseThrow(() -> {
            log.error("User with id {}, not found", id);
            return new EntityNotFoundException(String.format("User with id %s, not found", id));
        });
        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                //todo mapper

                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }
}
