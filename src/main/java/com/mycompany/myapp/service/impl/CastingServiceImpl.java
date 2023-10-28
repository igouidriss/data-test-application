package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Casting;
import com.mycompany.myapp.repository.CastingRepository;
import com.mycompany.myapp.service.CastingService;
import com.mycompany.myapp.service.dto.CastingDTO;
import com.mycompany.myapp.service.mapper.CastingMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Casting}.
 */
@Service
@Transactional
public class CastingServiceImpl implements CastingService {

    private final Logger log = LoggerFactory.getLogger(CastingServiceImpl.class);

    private final CastingRepository castingRepository;

    private final CastingMapper castingMapper;

    public CastingServiceImpl(CastingRepository castingRepository, CastingMapper castingMapper) {
        this.castingRepository = castingRepository;
        this.castingMapper = castingMapper;
    }

    @Override
    public CastingDTO save(CastingDTO castingDTO) {
        log.debug("Request to save Casting : {}", castingDTO);
        Casting casting = castingMapper.toEntity(castingDTO);
        casting = castingRepository.save(casting);
        return castingMapper.toDto(casting);
    }

    @Override
    public CastingDTO update(CastingDTO castingDTO) {
        log.debug("Request to update Casting : {}", castingDTO);
        Casting casting = castingMapper.toEntity(castingDTO);
        casting = castingRepository.save(casting);
        return castingMapper.toDto(casting);
    }

    @Override
    public Optional<CastingDTO> partialUpdate(CastingDTO castingDTO) {
        log.debug("Request to partially update Casting : {}", castingDTO);

        return castingRepository
            .findById(castingDTO.getId())
            .map(existingCasting -> {
                castingMapper.partialUpdate(existingCasting, castingDTO);

                return existingCasting;
            })
            .map(castingRepository::save)
            .map(castingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CastingDTO> findAll() {
        log.debug("Request to get all Castings");
        return castingRepository.findAll().stream().map(castingMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<CastingDTO> findAllWithEagerRelationships(Pageable pageable) {
        return castingRepository.findAllWithEagerRelationships(pageable).map(castingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CastingDTO> findOne(Long id) {
        log.debug("Request to get Casting : {}", id);
        return castingRepository.findOneWithEagerRelationships(id).map(castingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Casting : {}", id);
        castingRepository.deleteById(id);
    }
}
