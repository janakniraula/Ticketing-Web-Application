package com.mytickets.ticketingApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket_listings")
public class TicketListing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double askingPrice;

    private String description;

    @NotNull
    private LocalDateTime listingDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private ListingStatus status = ListingStatus.ACTIVE;

    @OneToOne
    @JoinColumn(name = "ticket_id", unique = true)
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    // This method enforces your "no markup" rule
    @PrePersist
    @PreUpdate
    public void validatePricing() {
        if (ticket != null && askingPrice > ticket.getOriginalPrice()) {
            throw new IllegalStateException("Listing price cannot be higher than the original ticket price");
        }
    }

    public Long getId() {
        return id;
    }

    public Double getAskingPrice() {
        return askingPrice;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getListingDate() {
        return listingDate;
    }

    public ListingStatus getStatus() {
        return status;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public User getSeller() {
        return seller;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAskingPrice(Double askingPrice) {
        this.askingPrice = askingPrice;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setListingDate(LocalDateTime listingDate) {
        this.listingDate = listingDate;
    }

    public void setStatus(ListingStatus status) {
        this.status = status;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
}