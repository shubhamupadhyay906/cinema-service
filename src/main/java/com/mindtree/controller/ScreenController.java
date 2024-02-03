package com.mindtree.controller;

import com.mindtree.dto.ScreenDto;
import com.mindtree.exception.ResourceNotFoundException;
import com.mindtree.model.Screen;
import com.mindtree.services.ScreenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.mindtree.common.CommonConstant.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class ScreenController {
    private final ScreenService screenService;

    @GetMapping("/screens")
    public List<Screen> findAll(@RequestParam("releaseDate") final Optional<LocalDateTime> releaseDate,
                                @RequestParam("showCycle") final Optional<Boolean> showCycle) {
        List<Screen> screenList = screenService.findAll();
        screenList.sort(Comparator.comparing(Screen::getId));
        return screenList;
    }

    @PostMapping("/screen")
    public Screen addScreen(@RequestBody ScreenDto screenDto) throws Exception {
        log.info(screenDto.toString());
        return screenService.saveScreen(screenDto);
    }

    @PostMapping(value = "/screens", produces = "application/json")
    public List<Screen> addScreens(@RequestBody Optional<List<ScreenDto>> screenDtoList) {
        return screenService.saveScreens(screenDtoList);
    }

    @GetMapping("/screen/{id}")
    public Screen findById(@PathVariable("id") long id) {
        try {
            return screenService.findById(id);
        } catch (ResourceNotFoundException e) {
            log.info(RECORD_NOT_FOUND_EXCEPTION, e.getLocalizedMessage());
            throw e;
        } catch (Exception e) {
            log.info(EXCEPTION, e.getLocalizedMessage());
            throw e;
        }
    }

    @PutMapping("/screen/{id}")
    public Screen updateScreen(@PathVariable("id") long id, @RequestBody ScreenDto screenDto) throws Exception {
        try {
            return screenService.updateScreen(id, screenDto);
        } catch (ResourceNotFoundException e) {
            log.info(RECORD_NOT_FOUND_EXCEPTION, e.getLocalizedMessage());
            throw e;
        } catch (Exception e) {
            log.info(EXCEPTION, e.getLocalizedMessage());
            throw e;
        }
    }

    @DeleteMapping("/screen/{id}")
    public String deleteScreen(@PathVariable("id") long id) {
        try {
            screenService.deleteScreen(id);
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
