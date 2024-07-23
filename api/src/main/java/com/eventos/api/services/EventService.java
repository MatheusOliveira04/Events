package com.eventos.api.services;

import com.eventos.api.models.event.Event;
import com.eventos.api.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface EventService {

    List<Event> findAll(Pageable pageable);

    Event findById(UUID id);

    Event insert(Event event);

    Event update(Event event);

    void delete(UUID id);
}
