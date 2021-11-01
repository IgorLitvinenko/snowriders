package com.snowriders.service.impl;

import com.snowriders.entities.AppUser;
import com.snowriders.entities.Role;
import com.snowriders.entities.UserRoles;
import com.snowriders.exceptions.DuplicateEntryException;
import com.snowriders.exceptions.EmailException;
import com.snowriders.model.response.UserResponse;
import com.snowriders.repositories.RoleRepository;
import com.snowriders.repositories.UserRepository;
import com.snowriders.repositories.UserRoleRepository;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserRoleRepository userRoleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return userRepository.findByEmail(username).orElseThrow(() ->
                new EmailException(String.format("Username %s not found", username))
        );
    }

    @Override
    public UserResponse saveUser(AppUser appUser) {
        String email = appUser.getEmail();

        if (validate(email)) {
            log.info("Trying to save user with wrong email {}", email);
            throw new EmailException("Wrong email");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            log.info("Email {}, already used", email);
            throw new DuplicateEntryException(String.format("Email %s, already used", email));
        }

        log.info("Saving new user to database");
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        userRepository.save(appUser);
        //todo add mapper
        return new UserResponse(appUser);
    }

    @Override
    public void addRoleToUser(Long userId, String roleName) {
        log.info("Add role {} to user with id {}", roleName, userId);
        AppUser user = userRepository.findById(userId).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with id %s, not found", userId)));
        Role role = roleRepository.findByName(roleName).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Role %s not found", roleName))
        );
        //todo mapper
        userRoleRepository.save(new UserRoles(user, role));
    }

    @Override
    public UserResponse getUser(Long id) {
        log.info("Fetching user {}", id);
        //todo remove try catch block
        try {
         return new UserResponse(userRepository.getById(id));
        } catch (EntityNotFoundException e) {
            log.info("User with id {}, not found", id);
            throw new EntityNotFoundException(String.format("User with id %s, not found", id));
        }
    }

    @Override
    public List<UserResponse> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                //todo mapper
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    private boolean validate(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }
}
