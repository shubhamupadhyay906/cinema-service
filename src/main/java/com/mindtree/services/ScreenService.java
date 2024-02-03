package com.mindtree.services;

import com.mindtree.dto.ScreenDto;
import com.mindtree.exception.ResourceNotFoundException;
import com.mindtree.model.Screen;
import com.mindtree.repository.ScreenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mindtree.common.CommonConstant.ID_LOG;
import static com.mindtree.common.CommonConstant.RECORD_NOT_FOUND;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class ScreenService {

    private final ScreenRepository repository;

    public List<Screen> findAll() {
        log.info("Action : get ALL Movie");
        return repository.findAll();
    }

    public Screen saveScreen(ScreenDto screenDto) throws Exception {
        log.info("Action : Save Screen");
        log.info("Screen : {}", ofNullable(screenDto).map(ScreenDto::getScreen).map(Screen::toString));
        return ofNullable(screenDto).map(ScreenDto::getScreen).map(repository::save).orElseThrow(Exception::new);
    }

    public List<Screen> saveScreens(Optional<List<ScreenDto>> optionalScreenDto) {
        if (!optionalScreenDto.isPresent())
            throw new NullPointerException();
        List<Screen> domains = optionalScreenDto.get().stream().map(ScreenDto::getScreen).collect(Collectors.toList());
        return repository.saveAll(domains);
    }

    public Screen findById(long id) throws ResourceNotFoundException {
        log.info("Action : get Movie by id");
        log.info(ID_LOG, id);
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND));
    }

    public Screen updateScreen(long id, ScreenDto screenDto) throws Exception {
        log.info("Action : Update Movie");
        log.info(ID_LOG, id);
        log.info("Movie : {}", ofNullable(screenDto).map(ScreenDto::getScreen).map(Screen::toString));
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND));
        return ofNullable(screenDto).map(ScreenDto::getScreen).map(repository::saveAndFlush).orElseThrow(Exception::new);
    }


    public void deleteScreen(long id) throws ResourceNotFoundException {
        log.info("Action : Delete Movie");
        log.info(ID_LOG, id);
        Screen screen = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND));
        repository.delete(screen);
    }
}
