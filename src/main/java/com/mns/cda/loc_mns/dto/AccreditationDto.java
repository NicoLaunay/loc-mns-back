package com.mns.cda.loc_mns.dto;

import com.mns.cda.loc_mns.model.Type;

import java.util.List;

public record AccreditationDto(Integer id, List<Type> borrowedTypes) {}
