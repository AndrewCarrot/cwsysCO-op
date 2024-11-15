package com.bylski.cwsys.model;

import com.bylski.cwsys.model.enums.ClassFrequency;
import com.bylski.cwsys.model.enums.PassType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Embeddable
@Data
public class Pass extends DateAudit{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PassType passType;
    private boolean discount;
    private int punches;
    private LocalDate validFrom;
    private LocalDate validTill;
    private ClassFrequency classFrequency;

    public Pass(){}

    public Pass(PassType passType, boolean discount, int punches, LocalDate validFrom, LocalDate validTill, ClassFrequency classFrequency) {
        this.passType = passType;
        this.discount = discount;
        this.punches = punches;
        this.validFrom = validFrom;
        this.validTill = validTill;
        this.classFrequency = classFrequency;
    }
}
