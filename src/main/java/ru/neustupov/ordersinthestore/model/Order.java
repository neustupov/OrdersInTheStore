package ru.neustupov.ordersinthestore.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client client;

    @Column(name = "add_date_time", columnDefinition = "timestamp default current_timestamp", nullable = false)
    @NotNull
    private LocalDateTime addDateTime;

    @Column(name = "prepayment", nullable = false)
    @NotNull
    private int prepayment;

    @Column(name = "amount", nullable = false)
    @NotNull
    private int amount;

    @Column(name = "residue", nullable = false)
    @NotNull
    private int residue;

    @Column(name = "ready", nullable = false, columnDefinition = "bool default true")
    private int ready;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private Set<PriceRequest> priceRequests;

    public Order() {
    }

    public Order(Order o) {
        this(o.getClient(), o.getAddDateTime(), o.getPrepayment(), o.getAmount(), o.getResidue(), o.getReady()
                , o.getPriceRequests());
    }

    public Order(Client client, @NotNull LocalDateTime addDateTime, @NotNull int prepayment, @NotNull int amount,
                 @NotNull int residue, int ready, Set<PriceRequest> priceRequests) {
        this.client = client;
        this.addDateTime = addDateTime;
        this.prepayment = prepayment;
        this.amount = amount;
        this.residue = residue;
        this.ready = ready;
        this.priceRequests = priceRequests;
    }

    public Order(Integer id, Client client, @NotNull LocalDateTime addDateTime, @NotNull int prepayment,
                 @NotNull int amount, @NotNull int residue, int ready, Set<PriceRequest> priceRequests) {
        super(id);
        this.client = client;
        this.addDateTime = addDateTime;
        this.prepayment = prepayment;
        this.amount = amount;
        this.residue = residue;
        this.ready = ready;
        this.priceRequests = priceRequests;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getAddDateTime() {
        return addDateTime;
    }

    public void setAddDateTime(LocalDateTime addDateTime) {
        this.addDateTime = addDateTime;
    }

    public int getPrepayment() {
        return prepayment;
    }

    public void setPrepayment(int prepayment) {
        this.prepayment = prepayment;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getResidue() {
        return residue;
    }

    public void setResidue(int residue) {
        this.residue = residue;
    }

    public int getReady() {
        return ready;
    }

    public void setReady(int ready) {
        this.ready = ready;
    }

    public Set<PriceRequest> getPriceRequests() {
        return priceRequests;
    }

    public void setPriceRequests(Set<PriceRequest> priceRequests) {
        this.priceRequests = priceRequests;
    }
}
