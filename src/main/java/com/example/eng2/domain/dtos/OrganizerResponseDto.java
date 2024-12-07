package com.example.eng2.domain.dtos;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizerResponseDto {

    private String name;
    private String email;
    private String phone;
    private String organization;
}
