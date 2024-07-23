package com.eventos.api.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class StandardError {

    private LocalDateTime dateTime;
    private Integer status;
    private String message;
    private String url;
}
