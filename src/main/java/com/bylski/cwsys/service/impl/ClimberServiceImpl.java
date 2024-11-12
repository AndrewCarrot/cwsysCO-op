package com.bylski.cwsys.service.impl;

import com.bylski.cwsys.exception.ResourceAlreadyExistsException;
import com.bylski.cwsys.exception.ResourceNotFoundException;
import com.bylski.cwsys.model.Climber;
import com.bylski.cwsys.model.Pass;
import com.bylski.cwsys.model.dto.ClimberDTO;
import com.bylski.cwsys.model.dto.ClimberDTOMapper;
import com.bylski.cwsys.model.payload.ClimberPayload;
import com.bylski.cwsys.repository.ClimberRepository;
import com.bylski.cwsys.service.inf.ClimberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClimberServiceImpl implements ClimberService {
    private final ClimberRepository climberRepository;
    private final ClimberDTOMapper mapper;
    public ClimberServiceImpl(ClimberRepository climberRepository, ClimberDTOMapper mapper) {
        this.climberRepository = climberRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<ClimberDTO> getAllClimbers(Pageable pageable) {
        return new PageImpl<>(climberRepository.findAll(pageable).stream().map(mapper).toList());
    }


    @Override
    public ClimberDTO getClimberByCardNumber(String cardNumber) {
        Optional<Climber> result = climberRepository.findByCardNumber(cardNumber);
        if(result.isEmpty())
            throw new ResourceNotFoundException("Climber","card number", cardNumber);
        return result.stream().map(mapper).findFirst().get();
    }

    @Override
    public void addNewClimber(ClimberPayload payload) {
        if (climberRepository.existsByCardNumber(payload.cardNumber()))
            throw new ResourceAlreadyExistsException("Climber","card number", payload.cardNumber());
        Climber climber = new Climber(
                payload.cardNumber(),
                payload.firstName(),
                payload.lastName()
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
}
