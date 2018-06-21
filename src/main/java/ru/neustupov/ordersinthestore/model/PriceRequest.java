package ru.neustupov.ordersinthestore.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "price_requests")
public class PriceRequest extends AbstractBaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @Column(name = "add_date_time", columnDefinition = "timestamp default current_timestamp",  nullable = false)
    @NotNull
    private LocalDateTime addDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client client;

    @Column(name = "ready", nullable = false, columnDefinition = "bool default true")
    private boolean ready;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "priceRequest")
    private Set<Product> products;

    public PriceRequest() {
    }

    public PriceRequest(PriceRequest p){
        this(p.getId(), p.getUser(), p.getAddDateTime(), p.getOrder(), p.getClient(), p.isReady());
    }

    public PriceRequest(@NotNull User user, @NotNull LocalDateTime addDateTime, Order order, Client client, boolean ready) {
        this.user = user;
        this.addDateTime = addDateTime;
        this.order = order;
        this.client = client;
        this.ready = ready;
    }

    public PriceRequest(Integer id, @NotNull User user, @NotNull LocalDateTime addDateTime, Order order, Client client, boolean ready) {
        super(id);
        this.user = user;
        this.addDateTime = addDateTime;
        this.order = order;
        this.client = client;
        this.ready = ready;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getAddDateTime() {
        return addDateTime;
    }

    public void setAddDateTime(LocalDateTime addDateTime) {
        this.addDateTime = addDateTime;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
