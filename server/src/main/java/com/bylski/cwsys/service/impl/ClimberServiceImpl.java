package com.bylski.cwsys.service.impl;

import com.bylski.cwsys.exception.ResourceAlreadyExistsException;
import com.bylski.cwsys.exception.ResourceNotFoundException;
import com.bylski.cwsys.model.Climber;
import com.bylski.cwsys.model.Pass;
import com.bylski.cwsys.model.dto.ClimberDTO;
import com.bylski.cwsys.model.payload.NewClimberPayload;
import com.bylski.cwsys.repository.ClimberRepository;
import com.bylski.cwsys.service.inf.ClimberService;
import com.bylski.cwsys.utilz.Patcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClimberServiceImpl implements ClimberService {
    private final ClimberRepository climberRepository;
    private final ObjectMapper objectMapper;

    public ClimberServiceImpl(ClimberRepository climberRepository, ObjectMapper objectMapper) {
        this.climberRepository = climberRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Page<ClimberDTO> getAllClimbers(Pageable pageable) {
        return climberRepository.
                findAll(pageable).
                map(o->objectMapper.convertValue(o, ClimberDTO.class));
    }


    @Override
    public ClimberDTO getClimberByCardNumber(String cardNumber) {
        Optional<Climber> result = climberRepository.findByCardNumber(cardNumber);
        if(result.isEmpty())
            throw new RuntimeException("Climber not found with cardNumber: " + cardNumber);
        return objectMapper.convertValue(result.get(), ClimberDTO.class);
    }

    @Override
    public void addNewClimber(NewClimberPayload payload) {
        if (climberRepository.existsByEmail(payload.email()))
            throw new ResourceAlreadyExistsException("Climber","email", payload.email());
        Climber climber = new Climber(
                payload.firstName(),
                payload.lastName(),
                payload.email(),
                payload.phoneNumber(),
                payload.dateOfBirth()
        );
        climberRepository.save(climber);
    }

    @Override
    public void deleteClimber(Long climberId) {
        climberRepository.deleteById(climberId);
    }

    @Override
    public void addNewPass(Long climberId, Pass pass) {
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(()->new ResourceNotFoundException("Climber", "id", climberId));

        for(Pass p: climber.getPasses()){
            if (p.getPassType().equals(pass.getPassType()))
                throw new ResourceAlreadyExistsException("climber entity with id: " + climberId + " already contains pass with this type");
        }

        climber.getPasses().add(pass);
        climberRepository.save(climber);
    }

    @Override
    public void updateClimberData(ClimberDTO payload) {
        Climber existing = climberRepository.findById(payload.id())
                .orElseThrow(()->new ResourceNotFoundException("Climber", "id", payload.id()));
        if (payload.email() != null  && climberRepository.existsByEmail(payload.email()))
            throw new ResourceAlreadyExistsException("Climber","email", payload.email());

        if(payload.cardNumber() != null && climberRepository.existsByCardNumber(payload.cardNumber()))
            throw new ResourceAlreadyExistsException("Climber","cardNumber", payload.cardNumber());

        Climber incomplete = objectMapper.convertValue(payload,Climber.class);

        try{
            Patcher.objectPatcher(existing,incomplete);
            climberRepository.save(
                    existing
            );
        }catch (IllegalAccessException e){
            e.getCause();
        }

    }

}
