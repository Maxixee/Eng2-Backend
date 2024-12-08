package com.example.eng2.web.controllers;

import com.example.eng2.domain.dtos.*;
import com.example.eng2.domain.dtos.mapper.OrganizerMapper;
import com.example.eng2.domain.dtos.mapper.PageableMapper;
import com.example.eng2.domain.dtos.mapper.ParticipantMapper;
import com.example.eng2.domain.entities.Organizer;
import com.example.eng2.domain.entities.Participant;
import com.example.eng2.domain.repository.projections.OrganizerProjection;
import com.example.eng2.domain.repository.projections.ParticipantProjection;
import com.example.eng2.domain.service.ParticipantService;
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
@RequestMapping("/participants")
public class ParticipantController {

    @Autowired
    private ParticipantService service;

    @PostMapping("/save")
    public ResponseEntity<ParticipantResponseDto> save(@RequestBody ParticipantCreateDto dto) {
        Participant participant = ParticipantMapper.toParticipant(dto);
        this.service.save(participant);

        return ResponseEntity.ok(ParticipantMapper.toDto(participant));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ParticipantResponseDto> update(@PathVariable Long id, @RequestBody ParticipantCreateDto dto) {
        Participant updatedParticipant = ParticipantMapper.toParticipant(dto);
        Participant participant = this.service.update(id, updatedParticipant);
        return ResponseEntity.ok(ParticipantMapper.toDto(participant));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantResponseDto> findById(@PathVariable Long id) {
        Participant participant = this.service.findById(id);

        return ResponseEntity.ok(ParticipantMapper.toDto(participant));
    }

    @GetMapping
    public ResponseEntity<PageableDto> getAll(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        Page<ParticipantProjection> participants = this.service.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(participants));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.ok("Participant deleted");
    }

}
