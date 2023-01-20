package com.ticket.ticketmanagement.dal;
import com.ticket.ticketmanagement.model.Ticket;
import com.ticket.ticketmanagement.model.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,String> {
    List<Ticket> findByCreatedBy(String createdBy);
    List<Ticket> findByStatus(TicketStatus ticketStatus);

}
