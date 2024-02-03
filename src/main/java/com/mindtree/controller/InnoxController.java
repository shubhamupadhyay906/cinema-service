package com.mindtree.controller;

import com.mindtree.dto.InnoxDto;
import com.mindtree.model.CinemaIF;
import com.mindtree.model.Innox;
import com.mindtree.services.CinemaIFService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.mindtree.common.CommonConstant.EXCEPTION;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class InnoxController {
    private final CinemaIFService cinemaIFService;

    @PostMapping("/cinema/innox")
    public CinemaIF addPVRCinema(@RequestBody InnoxDto innoxDto) throws Exception {
        try {
            return cinemaIFService.saveInnoxCinema(innoxDto);
        } catch (Exception e) {
            log.info(EXCEPTION, e.getLocalizedMessage());
            throw e;
        }
    }

    @PostMapping("/cinemas/innox")
    public List<Innox> addInnoxCinemas(@RequestBody List<Innox> innoxList) {
        return cinemaIFService.saveInnoxCinemas(innoxList);
    }
}
