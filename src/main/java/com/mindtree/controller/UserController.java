package com.mindtree.controller;

import com.mindtree.dto.UserDto;
import com.mindtree.exception.ResourceNotFoundException;
import com.mindtree.model.User;
import com.mindtree.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mindtree.common.CommonConstant.*;
import static org.apache.logging.log4j.util.Strings.EMPTY;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> find(@RequestParam("fName") final Optional<String> name,
                           @RequestParam("lName") final Optional<String> lastName) {

        log.info("Param ->FirstName : {}", name.isPresent() ? name.get() : EMPTY);
        log.info("Param ->LastName : {}", lastName);
        try {
            List<User> userDtoList = userService.findAll();
            userDtoList.sort(Comparator.comparing(User::getId));
            if (name.isPresent() && lastName.isPresent())
                return userDtoList.stream().filter(userDto -> userDto.getFirstName().equals(name.get()) && userDto.getLastName().equals(lastName.get())).collect(Collectors.toList());
            return name.map(s -> userDtoList.stream()
                            .filter(userDto -> userDto.getFirstName().equals(s))
                            .collect(Collectors.toList()))
                    .orElseGet(() -> lastName.map(s -> userDtoList.stream().filter(userDto -> userDto.getLastName().equals(s)).collect(Collectors.toList())).orElse(userDtoList));

        } catch (Exception e) {
            log.info(EXCEPTION, e.getLocalizedMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable("id") long id) {
        try {
            return userService.findById(id);
        } catch (ResourceNotFoundException e) {
            log.info(RECORD_NOT_FOUND_EXCEPTION, e.getLocalizedMessage());
            throw e;
        } catch (Exception e) {
            log.info(EXCEPTION, e.getLocalizedMessage());
            throw e;
        }
    }

    @PostMapping
    public User addUser(@RequestBody UserDto user) throws Exception {
        try {
            return userService.saveUser(user);
        } catch (Exception e) {
            log.info(EXCEPTION, e.getLocalizedMessage());
            throw e;
        }
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") long id, @RequestBody UserDto user) throws Exception {
        try {
            return userService.updateUser(id, user);
        } catch (ResourceNotFoundException e) {
            log.info(RECORD_NOT_FOUND_EXCEPTION, e.getLocalizedMessage());
            throw e;
        } catch (Exception e) {
            log.info(EXCEPTION, e.getLocalizedMessage());
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        try {
            userService.deleteUser(id);
            return SUCCESS_DELETED;
        } catch (ResourceNotFoundException e) {
            log.info(RECORD_NOT_FOUND_EXCEPTION, e.getLocalizedMessage());
            throw e;
        } catch (Exception e) {
            log.info(EXCEPTION, e.getLocalizedMessage());
            throw e;
        }
    }
}
