package com.mns.cda.loc_mns.dto;

import com.mns.cda.loc_mns.model.Documentation;
import com.mns.cda.loc_mns.model.Type;
import lombok.Builder;

import java.util.List;

@Builder
public record ModelLightDto(
        Integer id,
        String name,
        String description,
        Boolean isComponent,
        Type type,
        List<Documentation> documentations
) {}

