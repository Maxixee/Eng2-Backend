package com.example.eng2.domain.dtos.mapper;

import com.example.eng2.domain.dtos.EventCreateDto;
import com.example.eng2.domain.dtos.EventResponseDto;
import com.example.eng2.domain.dtos.OrganizerCreateDto;
import com.example.eng2.domain.dtos.OrganizerResponseDto;
import com.example.eng2.domain.entities.Event;
import com.example.eng2.domain.entities.Organizer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {

    public static Event toEvent(EventCreateDto dto) {
        return new ModelMapper().map(dto, Event.class);
    }

    public static EventResponseDto toDto(Event event) {
        return new ModelMapper().map(event, EventResponseDto.class);
    }
}
