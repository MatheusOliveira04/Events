package com.eventos.api.models.event;

import com.eventos.api.models.address.Address;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "event")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Event {

    @Id
    @GeneratedValue
    @Setter
    private UUID id;

    private String title;

    private String description;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "event_url")
    private String eventUrl;

    private Boolean remote;

    @Column(name = "event_date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "address")
    private Address address;
}
