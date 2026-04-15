package com.mns.cda.loc_mns.dto;

import com.mns.cda.loc_mns.model.Documentation;
import com.mns.cda.loc_mns.model.Type;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
public record ModelDto (
        Integer id,
        String name,
        String description,
        Boolean isComponent,
        Type type,
        List<Documentation> documentations,
        List<ModelLightDto> components,
        List<ModelLightDto> parents
) {}


