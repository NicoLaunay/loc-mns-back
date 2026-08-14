package com.mns.cda.loc_mns.mapper;

import com.mns.cda.loc_mns.dto.EquipmentDto;
import com.mns.cda.loc_mns.dto.EquipmentNoLoansDto;
import com.mns.cda.loc_mns.model.Equipment;
import org.springframework.stereotype.Component;

@Component
public class EquipmentMapper {
    public EquipmentDto toDto(Equipment equipment) {
        if (equipment == null) {
            return null;
        }

        return EquipmentDto.builder()
                .id(equipment.getId())
                .name(equipment.getName())
                .condition(equipment.getCondition())
                .model(equipment.getModel())
                .location(equipment.getLocation())
                .loans(equipment.getLoans())
                .build();
    }

    public EquipmentNoLoansDto toNoLoansDto(Equipment equipment) {
        if (equipment == null) {
            return null;
        }

        return EquipmentNoLoansDto.builder()
                .id(equipment.getId())
                .name(equipment.getName())
                .condition(equipment.getCondition())
                .model(equipment.getModel())
                .location(equipment.getLocation())
                .build();
    }

}
