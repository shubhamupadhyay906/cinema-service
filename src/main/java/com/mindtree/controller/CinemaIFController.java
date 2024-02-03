package com.mindtree.controller;

import com.mindtree.dto.CinemaIFDto;
import com.mindtree.exception.ResourceNotFoundException;
import com.mindtree.model.CinemaIF;
import com.mindtree.services.CinemaIFService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mindtree.common.CommonConstant.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class CinemaIFController {
    private final CinemaIFService cinemaIFService;

    @GetMapping("/cinemas")
    public List<CinemaIF> findAll(@RequestParam("bookingDate") @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") final Optional<Date> opBookingDate,
                                  @RequestParam("branchName") final Optional<String> opBranchName) throws Exception {

        log.info("Controller : get ALL Cinema");
        try {
            List<CinemaIF> cinemaIFList = cinemaIFService.findAll();
            log.info("cinemaIFList : {}", cinemaIFList);
            if (opBookingDate.isPresent() && opBranchName.isPresent()) {
                return cinemaIFList.stream()
                        .filter(cinemaIF -> cinemaIF.getBranchName().equals(opBranchName.get()) && cinemaIF.getBookingDate().equals(opBookingDate.get()))
                        .collect(Collectors.toList());
            }
            return opBookingDate.map(date ->
                            cinemaIFList.stream()
                                    .filter(cinemaIF -> cinemaIF.getBookingDate().equals(date))
                                    .collect(Collectors.toList()))
                    .orElseGet(() ->
                            opBranchName.map(s ->
                                            cinemaIFList.stream()
                                                    .filter(cinemaIF -> cinemaIF.getBranchName().equals(s))
                                                    .collect(Collectors.toList()))
                                    .orElse(cinemaIFList));

        } catch (Exception e) {
            log.info(EXCEPTION, e.getLocalizedMessage());
            throw e;
        }
    }

    @GetMapping("/cinemas/{cinemaName}")
    public List<CinemaIF> findByCinemaName(@PathVariable("cinemaName") final String cinemaName) throws Exception {
        log.info("Controller : find By Cinema Name");
        try {
            return cinemaIFService.findByCinemaName(cinemaName);
        } catch (Exception e) {
            log.info(EXCEPTION, e.getLocalizedMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public CinemaIF findById(@PathVariable("id") long id) {
        log.info("Controller : find By Id");
        log.info(ID_LOG, id);
        try {
            return cinemaIFService.findById(id);
        } catch (ResourceNotFoundException e) {
            log.info(RECORD_NOT_FOUND_EXCEPTION, e.getLocalizedMessage());
            throw e;
        } catch (Exception e) {
            log.info(EXCEPTION, e.getLocalizedMessage());
            throw e;
        }
    }

    @PostMapping("/cinema")
    public CinemaIF addCinema(@RequestBody CinemaIFDto cinemaIFDto) throws Exception {
        log.info("Controller : add Cinema");
        log.info("CinemaIFDto : {}", cinemaIFDto.toString());
        try {
            return cinemaIFService.saveCinema(cinemaIFDto);
        } catch (Exception e) {
            log.info(EXCEPTION, e.getLocalizedMessage());
            throw e;
        }
    }

    @PostMapping("/cinemas")
    public List<CinemaIF> addCinemas(@RequestBody Optional<List<CinemaIFDto>> opCinemaIFList) {
        log.info("Controller : add Cinemas");
        log.info("CinemaIFDto : {}", opCinemaIFList);
        try {
            if (!opCinemaIFList.isPresent())
                throw new NullPointerException();
            return cinemaIFService.saveCinemas(opCinemaIFList);
        } catch (Exception e) {
            log.info(EXCEPTION, e.getLocalizedMessage());
            throw e;
        }
    }

    @PutMapping("/{id}")
    public CinemaIF updateCinema(@PathVariable("id") long id, @RequestBody CinemaIFDto cinemaIFDto) throws Exception {
        log.info("Controller : Update Cinemas");
        log.info(ID_LOG, id);
        try {
            return cinemaIFService.updateCinema(id, cinemaIFDto);
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
            cinemaIFService.deleteCinema(id);
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
