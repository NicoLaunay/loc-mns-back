package com.mns.cda.loc_mns.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.Loan;
import com.mns.cda.loc_mns.model.Location;
import com.mns.cda.loc_mns.model.Model;
import com.mns.cda.loc_mns.view.EquipmentView;
import lombok.Builder;

import java.util.List;

@Builder
public record EquipmentDto(
        @JsonView(EquipmentView.class) Integer id,
        @JsonView(EquipmentView.class) String name,
        @JsonView(EquipmentView.class) String condition,
        @JsonView(EquipmentView.class) Model model,
        @JsonView(EquipmentView.class) Location location,
        @JsonView(EquipmentView.class) List<Loan> loans
) {
}
