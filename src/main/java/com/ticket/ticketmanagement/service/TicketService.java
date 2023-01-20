package com.ticket.ticketmanagement.service;

import com.ticket.ticketmanagement.dal.TicketRepository;
import com.ticket.ticketmanagement.exceptions.TicketAlreadyExistsException;
import com.ticket.ticketmanagement.exceptions.TicketNotFoundException;
import com.ticket.ticketmanagement.model.Ticket;
import com.ticket.ticketmanagement.model.TicketStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private static final Logger log =  LoggerFactory.getLogger(TicketService.class);


    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    //create ticket
    public Ticket createTicket(Ticket ticket) {
            if (ticketRepository.existsById(ticket.getId())) {
                log.info("ticket already exist with id: {}", ticket.getId());
                throw new TicketAlreadyExistsException(ticket.getId());
            }
            else{
                ticket.setCreatedAt(LocalDateTime.now());
                ticket.setUpdatedAt(LocalDateTime.now());
                ticket.setStatus(TicketStatus.open);
                log.info("Creating ticket: {}", ticket);
                return ticketRepository.save(ticket);
            }
        }


    public Ticket getTicket(String id) {
        // Log Request
        log.info("Getting ticket with id: {}", id);
        // Find Ticket in DB
        return ticketRepository.findById(id).orElseThrow(() -> new TicketNotFoundException(id));
    }
    public List<Ticket> getTickets(){
        return ticketRepository.findAll();
    }

    public Ticket updateTicket(String id, Ticket ticket) {
        // Log Request
        log.info("Updating ticket with id: {}", id);
        // Find and Update Ticket in DB
        return ticketRepository.findById(id)
                .map(ticketInDb -> {
                    ticketInDb.setTitle(ticket.getTitle());
                    ticketInDb.setSubject(ticket.getSubject());
                    ticketInDb.setStatus(ticket.getStatus());
                    ticketInDb.setUpdatedAt(LocalDateTime.now());
                    return ticketRepository.save(ticketInDb);
                })
                .orElseThrow(() -> new TicketNotFoundException(id));
    }
    public void deleteTicket(String id) {
        try {
            log.info("Deleting ticket with id: {}", id);
            ticketRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting ticket with id: {}. Ticket not found.", id);
            throw new TicketNotFoundException(id);
        }
    }


    // Get Tickets Created by User
    public List<Ticket> getUserTickets(String user) {
        log.info("Getting all tickets created by user: {}", user);
        return ticketRepository.findByCreatedBy(user);
    }

    // Get Open Tickets
    public List<Ticket> getOpenTickets() {
        log.info("Getting all open tickets");
        return ticketRepository.findByStatus(TicketStatus.open);
    }


}
