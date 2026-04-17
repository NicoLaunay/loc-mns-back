package com.mns.cda.loc_mns.mapper;

import com.mns.cda.loc_mns.dto.ModelDto;
import com.mns.cda.loc_mns.dto.ModelLightDto;
import com.mns.cda.loc_mns.model.Composition;
import com.mns.cda.loc_mns.model.Documentation;
import com.mns.cda.loc_mns.model.Model;
import com.mns.cda.loc_mns.model.Type;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

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
                .components(componentsToLightDtoList(model.getComponents()))
                .parents(parentsToLightDtoList(model.getParents()))
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

    public List<ModelLightDto> parentsToLightDtoList(List<Composition> compositions) {
        return mapToLightDtoList(compositions, Composition::getParent);
    }

    public List<ModelLightDto> componentsToLightDtoList(List<Composition> compositions) {
        return mapToLightDtoList(compositions, Composition::getComponent);
    }

    // method applies the given function to every entry of the given compositions list before turning resulting model into a light Dto
    private List<ModelLightDto> mapToLightDtoList(List<Composition> compositions, Function<Composition, Model> function) {
        if (compositions == null) {
            return List.of();
        }
        return compositions.stream()
                .map(function)
                .map(c -> toLightDto(c))
                .toList();
    }
}
