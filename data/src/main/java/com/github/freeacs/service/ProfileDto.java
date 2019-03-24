package com.github.freeacs.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private Long id;
    private String name;
    private UnitTypeDto unitType;
}
