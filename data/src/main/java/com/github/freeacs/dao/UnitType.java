package com.github.freeacs.dao;

import com.github.freeacs.shared.Protocol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

import java.io.Serializable;

@Data
@Builder
@Wither
@NoArgsConstructor
@AllArgsConstructor
public class UnitType implements Serializable {
    private Long id;
    private String name;
    private String vendor;
    private String description;
    private Protocol protocol;
}
