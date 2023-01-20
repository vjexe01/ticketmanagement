package com.ticket.ticketmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    private String id;
    private String title;
    private String subject;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private TicketStatus status;

}
