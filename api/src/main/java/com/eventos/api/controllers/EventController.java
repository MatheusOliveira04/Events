package com.eventos.api.controllers;

import com.eventos.api.models.address.Address;
import com.eventos.api.models.event.Event;
import com.eventos.api.models.event.EventDTO;
import com.eventos.api.services.Impl.AddressServiceImpl;
import com.eventos.api.services.Impl.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventServiceImpl eventService;

    @Autowired
    private AddressServiceImpl addressService;

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> findById(@PathVariable UUID id){
        Event event = eventService.findById(id);
        return ResponseEntity.ok(new EventDTO(event.getId(), event.getTitle(), event.getDescription(), event.getImgUrl(), event.getEventUrl(), event.getRemote(),
                                    event.getDate(), event.getAddress().getId(), event.getAddress().getCity(), event.getAddress().getUf()));
    }

    @Secured({"ROLE_USER"})
    @Cacheable(value = "/event/findAll")
    @GetMapping()
    public ResponseEntity<List<EventDTO>> findAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(this.eventService.findAll(pageable)
                            .stream()
                            .map((event) -> new EventDTO(event.getId(), event.getTitle(), event.getDescription(), event.getImgUrl(), event.getEventUrl(), event.getRemote(),
                                    event.getDate(), event.getAddress().getId(), event.getAddress().getCity(), event.getAddress().getUf()))
                            .toList());
    }

    @Secured({"ROLE_ADMIN"})
    @CacheEvict(value = "/event/findAll", allEntries = true)
    @PostMapping
    public ResponseEntity<EventDTO> insert(@RequestBody EventDTO eventDTO, UriComponentsBuilder uriBuilder) {
        Address address = this.addressService.findById(eventDTO.addressId());
        this.eventService.insert(new Event(null, eventDTO.title(), eventDTO.description(), eventDTO.imgUrl(), eventDTO.eventUrl(),
                eventDTO.remote(), eventDTO.date(), address));
        URI uri = uriBuilder.path("/event/{id}").buildAndExpand(eventDTO.id()).toUri();
        return ResponseEntity.created(uri).body(eventDTO);
    }

    @Secured({"ROLE_ADMIN"})
    @CacheEvict(value = "/event/findAll", allEntries = true)
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> update(@RequestBody EventDTO eventDTO, @PathVariable UUID id){
        Address address = this.addressService.findById(eventDTO.addressId());
        this.eventService.update(new Event(id, eventDTO.title(), eventDTO.description(), eventDTO.imgUrl(), eventDTO.eventUrl(),
                eventDTO.remote(), eventDTO.date(), address));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(id).toUri();
        return ResponseEntity.ok().location(uri).body(eventDTO);
    }

    @Secured({"ROLE_ADMIN"})
    @CacheEvict(value = "/event/findAll", allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        this.eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
