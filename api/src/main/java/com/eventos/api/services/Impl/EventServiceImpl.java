package com.eventos.api.services.Impl;

import com.eventos.api.models.event.Event;
import com.eventos.api.repositories.EventRepository;
import com.eventos.api.services.EventService;
import com.eventos.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Event> findAll(Pageable pageable) {
        Page<Event> eventList = this.eventRepository.findAll(pageable);
        if (eventList.isEmpty()) {
            throw new ObjectNotFoundException("nothing event to find");
        }
        return eventList.toList();
    }

    @Override
    public Event findById(UUID id) {
        return this.eventRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Did not find event with id: " + id));
    }

    @Override
    public Event insert(Event event) {
        return this.eventRepository.save(event);
    }

    @Override
    public Event update(Event event) {
        this.findById(event.getId());
        this.validations(event);
        return this.eventRepository.save(event);
    }

    @Override
    public void delete(UUID id) {
        this.eventRepository.deleteById(id);
    }

    private void validations(Event event) {
        this.validTitle(event);
    }

    private void validTitle(Event event) {
        this.verifyIfTitleIsNullThrowException(event);
    }

    private void verifyIfTitleIsNullThrowException(Event event) {
        if(event.getTitle() == null || event.getTitle().isBlank()) {
            throw new ObjectNotFoundException("title is null or empty");
        }
    }

}
