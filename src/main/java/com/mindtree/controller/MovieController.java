package com.mindtree.controller;

import com.mindtree.dto.MovieDto;
import com.mindtree.exception.ResourceNotFoundException;
import com.mindtree.model.Movie;
import com.mindtree.services.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mindtree.common.CommonConstant.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/movies")
    public List<Movie> find(@RequestParam("releaseDate") final Optional<LocalDateTime> releaseDate,
                            @RequestParam("showCycle") final Optional<Boolean> showCycle) {
        List<Movie> movieDtoList = movieService.findAll();
        movieDtoList.sort(Comparator.comparing(Movie::getId));
        return releaseDate.map(
                        localDateTime -> movieDtoList.stream().filter(movieDto -> movieDto.getReleaseDate().equals(localDateTime)).collect(Collectors.toList()))
                .orElseGet(() -> showCycle.map(aBoolean -> movieDtoList.stream().filter(movieDto -> movieDto.isShowCycle() == aBoolean).collect(Collectors.toList()))
                        .orElse(movieDtoList));
    }

    @GetMapping("/movies/title")
    public List<Movie> find(@RequestParam("title") final String title) {
        return movieService.findByMovieTitle(title);
    }

    @PostMapping("/movie")
    public Movie addMovie(@RequestBody MovieDto movieDto) throws Exception {
        log.info(movieDto.toString());
        return movieService.saveMovie(movieDto);
    }

    @PostMapping(value = "/movies", produces = "application/json")
    public List<Movie> addMovies(@RequestBody Optional<List<MovieDto>> movieDtos) throws ResourceNotFoundException {
        return movieService.saveMovies(movieDtos);
    }

    @GetMapping("/movie/{id}")
    public Movie findById(@PathVariable("id") long id) {
        try {
            return movieService.findById(id);
        } catch (ResourceNotFoundException e) {
            log.info(RECORD_NOT_FOUND_EXCEPTION, e.getLocalizedMessage());
            throw e;
        } catch (Exception e) {
            log.info(EXCEPTION, e.getLocalizedMessage());
            throw e;
        }
    }

    @PutMapping("/movie/{id}")
    public Movie updateMovie(@PathVariable("id") long id, @RequestBody MovieDto movieDto) throws Exception {
        try {
            return movieService.updateMovie(id, movieDto);
        } catch (ResourceNotFoundException e) {
            log.info(RECORD_NOT_FOUND_EXCEPTION, e.getLocalizedMessage());
            throw e;
        } catch (Exception e) {
            log.info(EXCEPTION, e.getLocalizedMessage());
            throw e;
        }
    }

    @DeleteMapping("/movie/{id}")
    public String deleteMovie(@PathVariable("id") long id) {
        try {
            movieService.deleteMovie(id);
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
