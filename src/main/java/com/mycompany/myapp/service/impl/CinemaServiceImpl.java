package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Cinema;
import com.mycompany.myapp.repository.CinemaRepository;
import com.mycompany.myapp.service.CinemaService;
import com.mycompany.myapp.service.dto.CinemaDTO;
import com.mycompany.myapp.service.mapper.CinemaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Cinema}.
 */
@Service
@Transactional
public class CinemaServiceImpl implements CinemaService {

    private final Logger log = LoggerFactory.getLogger(CinemaServiceImpl.class);

    private final CinemaRepository cinemaRepository;

    private final CinemaMapper cinemaMapper;

    public CinemaServiceImpl(CinemaRepository cinemaRepository, CinemaMapper cinemaMapper) {
        this.cinemaRepository = cinemaRepository;
        this.cinemaMapper = cinemaMapper;
    }

    @Override
    public CinemaDTO save(CinemaDTO cinemaDTO) {
        log.debug("Request to save Cinema : {}", cinemaDTO);
        Cinema cinema = cinemaMapper.toEntity(cinemaDTO);
        cinema = cinemaRepository.save(cinema);
        return cinemaMapper.toDto(cinema);
    }

    @Override
    public CinemaDTO update(CinemaDTO cinemaDTO) {
        log.debug("Request to update Cinema : {}", cinemaDTO);
        Cinema cinema = cinemaMapper.toEntity(cinemaDTO);
        cinema = cinemaRepository.save(cinema);
        return cinemaMapper.toDto(cinema);
    }

    @Override
    public Optional<CinemaDTO> partialUpdate(CinemaDTO cinemaDTO) {
        log.debug("Request to partially update Cinema : {}", cinemaDTO);

        return cinemaRepository
            .findById(cinemaDTO.getId())
            .map(existingCinema -> {
                cinemaMapper.partialUpdate(existingCinema, cinemaDTO);

                return existingCinema;
            })
            .map(cinemaRepository::save)
            .map(cinemaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CinemaDTO> findAll() {
        log.debug("Request to get all Cinemas");
        return cinemaRepository.findAll().stream().map(cinemaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CinemaDTO> findOne(Long id) {
        log.debug("Request to get Cinema : {}", id);
        return cinemaRepository.findById(id).map(cinemaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cinema : {}", id);
        cinemaRepository.deleteById(id);
    }
}
