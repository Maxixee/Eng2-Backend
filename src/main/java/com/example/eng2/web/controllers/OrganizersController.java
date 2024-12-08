package com.example.eng2.web.controllers;

import com.example.eng2.domain.dtos.OrganizerCreateDto;
import com.example.eng2.domain.dtos.OrganizerResponseDto;
import com.example.eng2.domain.dtos.PageableDto;
import com.example.eng2.domain.dtos.mapper.OrganizerMapper;
import com.example.eng2.domain.dtos.mapper.PageableMapper;
import com.example.eng2.domain.entities.Organizer;
import com.example.eng2.domain.repository.projections.OrganizerProjection;
import com.example.eng2.domain.service.OrganizersService;
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
@RequestMapping("/organizers")
public class OrganizersController {

    @Autowired
    private OrganizersService service;

    @PostMapping("/save")
    public ResponseEntity<OrganizerResponseDto> save(@RequestBody OrganizerCreateDto dto){
        Organizer organizer = OrganizerMapper.toOrganizer(dto);
        this.service.save(organizer);

        return ResponseEntity.ok(OrganizerMapper.toDto(organizer));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrganizerResponseDto> update(@PathVariable Long id, @RequestBody OrganizerCreateDto dto) {
        Organizer updatedOrganizer = OrganizerMapper.toOrganizer(dto);
        Organizer organizer = this.service.update(id, updatedOrganizer);
        return ResponseEntity.ok(OrganizerMapper.toDto(organizer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizerResponseDto> findById(@PathVariable Long id) {
        Organizer organizer = this.service.findById(id);

        return ResponseEntity.ok(OrganizerMapper.toDto(organizer));
    }

    @GetMapping
    public ResponseEntity<PageableDto> getAll(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        Page<OrganizerProjection> organizer = this.service.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(organizer));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.ok("Organizer deleted");
    }
}
