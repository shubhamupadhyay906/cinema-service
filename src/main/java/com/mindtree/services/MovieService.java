package com.mindtree.services;

import com.mindtree.dto.MovieDto;
import com.mindtree.exception.ResourceNotFoundException;
import com.mindtree.model.Movie;
import com.mindtree.repository.MovieRepository;
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
public class MovieService {
    private final MovieRepository repository;

    public List<Movie> findAll() {
        log.info("Action : get ALL Movie");
        return repository.findAll();
    }

    public List<Movie> findByMovieTitle(String title) {
        log.info("Action : Find Movie by Title");
        log.info("Title : {}", title);
        return repository.findByMovieTitle(title);
    }

    public Movie saveMovie(MovieDto movie) throws Exception {
        log.info("Action : Save Movie");
        log.info("Movie : {}", ofNullable(movie).map(MovieDto::getMovie).map(Movie::toString));
        return ofNullable(movie).map(MovieDto::getMovie).map(repository::save).orElseThrow(Exception::new);
    }

    public List<Movie> saveMovies(Optional<List<MovieDto>> optionalMovieDtos) {
        if (!optionalMovieDtos.isPresent())
            throw new NullPointerException();
        List<Movie> domains = optionalMovieDtos.get().stream().map(MovieDto::getMovie).collect(Collectors.toList());
        return repository.saveAll(domains);
    }

    public Movie findById(long id) throws ResourceNotFoundException {
        log.info("Action : get Movie by id");
        log.info(ID_LOG, id);
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND));
    }

    public Movie updateMovie(long id, MovieDto movieDto) throws Exception {
        log.info("Action : Update Movie");
        log.info(ID_LOG, id);
        log.info("Movie : {}", ofNullable(movieDto).map(MovieDto::getMovie).map(Movie::toString));
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND));
        return ofNullable(movieDto).map(MovieDto::getMovie).map(repository::saveAndFlush).orElseThrow(Exception::new);
    }


    public void deleteMovie(long id) throws ResourceNotFoundException {
        log.info("Action : Delete Movie");
        log.info(ID_LOG, id);
        Movie movie = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND));
        repository.delete(movie);
    }

}
