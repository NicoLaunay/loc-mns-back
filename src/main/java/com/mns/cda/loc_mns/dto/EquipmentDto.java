package com.mns.cda.loc_mns.dto;

import com.mns.cda.loc_mns.model.Loan;
import com.mns.cda.loc_mns.model.Location;
import com.mns.cda.loc_mns.model.Model;
import lombok.Builder;

import java.util.List;

@Builder
public record EquipmentDto(
        Integer id,
        String name,
        String condition,
        Model model,
        Location location,
        List<Loan> loans
) {
}
