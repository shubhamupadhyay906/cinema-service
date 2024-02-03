package com.mindtree.services;

import com.mindtree.dto.UserDto;
import com.mindtree.exception.ResourceNotFoundException;
import com.mindtree.model.User;
import com.mindtree.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mindtree.common.CommonConstant.ID_LOG;
import static com.mindtree.common.CommonConstant.RECORD_NOT_FOUND;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class UserService {

    private final UserRepository repository;

    public List<User> findAll() {
        log.info("Action : get ALL User");
        return repository.findAll();
    }

    public User findById(long id) throws ResourceNotFoundException {
        log.info("Action : get User by id");
        log.info(ID_LOG, id);
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND));
    }

    public User getByUserName(String userName) throws ResourceNotFoundException {
        log.info("Action : get User by user name");
        log.info("UserName : {}", userName);
        return repository.findByUserName(userName).orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND));
    }

    public User saveUser(UserDto userDto) throws Exception {
        log.info("Action : Save User");
        log.info("User : {}", ofNullable(userDto).map(UserDto::getUser).map(User::toString));
        return ofNullable(userDto).map(UserDto::getUser).map(repository::save).orElseThrow(Exception::new);
    }

    public User updateUser(long id, UserDto user) throws Exception {
        log.info("Action : Update User");
        log.info(ID_LOG, id);
        log.info("User : {}", ofNullable(user).map(UserDto::getUser).map(User::toString));
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND));
        return ofNullable(user).map(UserDto::getUser).map(repository::saveAndFlush).orElseThrow(Exception::new);
    }

    public void deleteUser(long id) throws ResourceNotFoundException {
        log.info("Action : Delete User");
        log.info(ID_LOG, id);
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND));
        repository.delete(user);
    }

}
