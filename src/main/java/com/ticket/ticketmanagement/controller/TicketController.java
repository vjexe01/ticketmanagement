package com.ticket.ticketmanagement.controller;

import com.ticket.ticketmanagement.exceptions.TicketAlreadyExistsException;
import com.ticket.ticketmanagement.exceptions.TicketNotFoundException;
import com.ticket.ticketmanagement.model.Ticket;
import com.ticket.ticketmanagement.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping("/tickets")
    public List<Ticket> getTickets(){
        return this.ticketService.getTickets();
    }

    @PostMapping("/tickets")
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        try {
            Ticket createdTicket = this.ticketService.createTicket(ticket);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
        } catch (TicketAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/tickets/{ticketId}")
    public ResponseEntity<Ticket> getTicket(@PathVariable String ticketId){
        try {
            Ticket ticket = this.ticketService.getTicket(ticketId);
            return ResponseEntity.ok(ticket);
        }
        catch (TicketNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/tickets/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable String id, @RequestBody Ticket ticket) {
        try {
            Ticket updatedTicket = this.ticketService.updateTicket(id, ticket);
            return ResponseEntity.ok(updatedTicket);
        } catch (TicketNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/tickets/{id}")
    public ResponseEntity<HttpStatus> deleteTicket(@PathVariable String id) {
        try {
            this.ticketService.deleteTicket(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (TicketNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/tickets/user/{user}")
    public ResponseEntity<List<Ticket>> getUserTickets(@PathVariable String user) {
        List<Ticket> tickets = this.ticketService.getUserTickets(user);
        if (tickets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok(tickets);
        }
    }

    @GetMapping("tickets/open")
    public ResponseEntity<List<Ticket>> getOpenTickets() {
        List<Ticket> openTickets = this.ticketService.getOpenTickets();
        if (openTickets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok(openTickets);
        }
    }



}
