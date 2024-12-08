package com.example.eng2.domain.dtos.mapper;

import com.example.eng2.domain.dtos.ParticipantCreateDto;
import com.example.eng2.domain.dtos.ParticipantResponseDto;
import com.example.eng2.domain.entities.Participant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParticipantMapper {

    public static Participant toParticipant(ParticipantCreateDto dto) {
        return new ModelMapper().map(dto, Participant.class);
    }

    public static ParticipantResponseDto toDto(Participant participant) {
        return new ModelMapper().map(participant, ParticipantResponseDto.class);
    }
}
