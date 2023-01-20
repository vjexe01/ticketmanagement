package com.ticket.ticketmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Ticket Already Exists")
public class TicketAlreadyExistsException extends RuntimeException{
    public TicketAlreadyExistsException(String id) {
        super("Ticket with id " + id + " already exists.");
    }
}
