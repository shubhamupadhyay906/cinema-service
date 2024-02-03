package com.mindtree.controller;

import com.mindtree.dto.PVRDto;
import com.mindtree.model.CinemaIF;
import com.mindtree.model.PVR;
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
public class PVRController {

    private final CinemaIFService cinemaIFService;

    @PostMapping("/cinemas/pvr")
    public List<PVR> addPVRCinemas(@RequestBody List<PVR> pvrList) {
        try {
            return cinemaIFService.savePVRCinemas(pvrList);
        } catch (Exception e) {
            log.info(EXCEPTION, e.getLocalizedMessage());
            throw e;
        }
    }

    @PostMapping("/cinema/pvr")
    public CinemaIF addPVRCinema(@RequestBody PVRDto pvrDto) throws Exception {
        try {
            return cinemaIFService.savePVRCinema(pvrDto);
        } catch (Exception e) {
            log.info(EXCEPTION, e.getLocalizedMessage());
            throw e;
        }
    }

}
