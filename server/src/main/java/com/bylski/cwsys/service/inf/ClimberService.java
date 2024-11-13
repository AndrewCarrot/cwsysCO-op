package com.bylski.cwsys.service.inf;

import com.bylski.cwsys.model.Pass;
import com.bylski.cwsys.model.dto.ClimberDTO;
import com.bylski.cwsys.model.payload.ClimberPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClimberService {
    Page<ClimberDTO> getAllClimbers(Pageable pageable);
    ClimberDTO getClimberByCardNumber(String cardNumber);
    void addNewClimber(ClimberPayload payload);
    void deleteClimber(Long climberId);
    void addNewPass(Long climberId, Pass pass);
}
