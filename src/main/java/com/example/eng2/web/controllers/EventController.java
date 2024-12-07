package com.example.eng2.web.controllers;

import com.example.eng2.domain.dtos.EventCreateDto;
import com.example.eng2.domain.dtos.EventResponseDto;
import com.example.eng2.domain.dtos.PageableDto;
import com.example.eng2.domain.dtos.mapper.EventMapper;
import com.example.eng2.domain.dtos.mapper.PageableMapper;
import com.example.eng2.domain.entities.Event;
import com.example.eng2.domain.repository.projections.EventProjection;
import com.example.eng2.domain.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService service;

    @PostMapping("/save")
    public ResponseEntity<EventResponseDto> save(@RequestBody EventCreateDto dto) {
        Event event = EventMapper.toEvent(dto);
        this.service.save(event);

        return ResponseEntity.ok(EventMapper.toDto(event));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EventResponseDto> update(@PathVariable Long id, @RequestBody EventCreateDto dto) {
        Event updatedEvent = EventMapper.toEvent(dto);
        Event event = this.service.update(id, updatedEvent);
        return ResponseEntity.ok(EventMapper.toDto(event));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> findById(@PathVariable Long id) {
        Event event = this.service.findById(id);
        return ResponseEntity.ok(EventMapper.toDto(event));
    }

    @GetMapping
    public ResponseEntity<PageableDto> getAll(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        Page<EventProjection> events = this.service.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(events));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.ok("Event deleted");
    }
}

