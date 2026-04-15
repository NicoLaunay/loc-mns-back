package com.mns.cda.loc_mns.mapper;

import com.mns.cda.loc_mns.dto.ModelDto;
import com.mns.cda.loc_mns.dto.ModelLightDto;
import com.mns.cda.loc_mns.model.Documentation;
import com.mns.cda.loc_mns.model.Model;
import com.mns.cda.loc_mns.model.Type;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModelMapper {
    public ModelDto toDto(Model model) {
        System.out.println("toDto" + model.getName());
        if (model == null) {
            return null;
        }

        return ModelDto.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .isComponent(model.getIsComponent())
                .type(model.getType())
                .documentations(model.getDocumentations())
                .components(toLightDtoList(model.getComponents()))
                .parents(toLightDtoList(model.getParents()))
                .build();
    }

    public ModelLightDto toLightDto(Model model) {
        System.out.println("toLightDto");
        if (model == null) {
            return null;
        }

        return ModelLightDto.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .isComponent(model.getIsComponent())
                .type(model.getType())
                .documentations(model.getDocumentations())
                .build();
    }

    public List<ModelLightDto> toLightDtoList(List<Model> models) {
        if (models == null) {
            return List.of();
        }

        return models.stream()
                .map(this::toLightDto)
                .toList();
    }
}
