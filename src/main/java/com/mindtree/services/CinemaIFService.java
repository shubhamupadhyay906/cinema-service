package com.mindtree.services;

import com.mindtree.dto.CinemaIFDto;
import com.mindtree.dto.InnoxDto;
import com.mindtree.dto.PVRDto;
import com.mindtree.exception.ResourceNotFoundException;
import com.mindtree.model.CinemaIF;
import com.mindtree.model.Innox;
import com.mindtree.model.PVR;
import com.mindtree.repository.CinemaIFRepository;
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
public class CinemaIFService {
    private final CinemaIFRepository repository;

    public List<CinemaIF> findAll() {
        log.info("Action : Find all CinemaIF");
        return repository.findAll();
    }

    public List<CinemaIF> findByCinemaName(String cinemaName) {
        log.info("Action : Find all Cinema Name");
        return repository.findByCinemaName(cinemaName);
    }

    public CinemaIF saveCinema(CinemaIFDto cinemaIFDto) throws Exception {
        log.info("Action : Save CinemaIF");
        log.info("CinemaIF : {}", ofNullable(cinemaIFDto).map(CinemaIFDto::getCinemaIF).map(CinemaIF::toString));
        return ofNullable(cinemaIFDto).map(CinemaIFDto::getCinemaIF).map(repository::save).orElseThrow(Exception::new);
    }

    public List<CinemaIF> saveCinemas(Optional<List<CinemaIFDto>> cinemaIFList) {
        List<CinemaIF> domains = cinemaIFList.get().stream().map(CinemaIFDto::getCinemaIF).collect(Collectors.toList());
        return repository.saveAll(domains);

    }

    public CinemaIF findById(long id) throws ResourceNotFoundException {
        log.info("Action : get CinemaIF by id");
        log.info(ID_LOG, id);
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND));
    }

    public CinemaIF updateCinema(long id, CinemaIFDto cinemaIFDto) throws Exception {
        log.info("Action : Update Cinema");
        log.info(ID_LOG, id);
        log.info("CinemaIF : {}", ofNullable(cinemaIFDto).map(CinemaIFDto::getCinemaIF).map(CinemaIF::toString));
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND));
        return ofNullable(cinemaIFDto).map(CinemaIFDto::getCinemaIF).map(repository::saveAndFlush).orElseThrow(Exception::new);
    }

    public void deleteCinema(long id) throws ResourceNotFoundException {
        log.info("Action : Delete Cinema");
        log.info(ID_LOG, id);
        CinemaIF cinemaIF = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND));
        repository.delete(cinemaIF);
    }

    public PVR savePVRCinema(PVRDto pvrDto) throws Exception {
        log.info("Action : Save PVR");
        log.info("PVR : {}", ofNullable(pvrDto).map(PVRDto::getPvr).map(PVR::toString));
        return ofNullable(pvrDto).map(PVRDto::getPvr).map(repository::save).orElseThrow(Exception::new);
    }

    public Innox saveInnoxCinema(InnoxDto innoxDto) throws Exception {
        log.info("Action : Save INNOX");
        log.info("INNOX : {}", ofNullable(innoxDto).map(InnoxDto::getInnox).map(Innox::toString));
        return ofNullable(innoxDto).map(InnoxDto::getInnox).map(repository::save).orElseThrow(Exception::new);
    }

    public List<PVR> savePVRCinemas(List<PVR> pvrList) {
        return repository.saveAll(pvrList);
    }

    public List<Innox> saveInnoxCinemas(List<Innox> innoxList) {
        return repository.saveAll(innoxList);
    }

}
