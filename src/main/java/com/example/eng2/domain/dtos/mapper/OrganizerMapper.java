package com.example.eng2.domain.dtos.mapper;

import com.example.eng2.domain.dtos.OrganizerCreateDto;
import com.example.eng2.domain.dtos.OrganizerResponseDto;
import com.example.eng2.domain.entities.Organizer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrganizerMapper {

    public static Organizer toOrganizer(OrganizerCreateDto dto) {
        return new ModelMapper().map(dto, Organizer.class);
    }

    public static OrganizerResponseDto toDto(Organizer organizer) {
        return new ModelMapper().map(organizer, OrganizerResponseDto.class);
    }
}
