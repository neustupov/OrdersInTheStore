package ru.neustupov.ordersinthestore.model;

import org.hibernate.validator.constraints.SafeHtml;
import ru.neustupov.ordersinthestore.View;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client extends AbstractNamedEntity {

    @Column(name = "last_name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    @SafeHtml(groups = {View.Web.class})
    private String lastName;

    @Column(name = "phone_number", nullable = false)
    @NotNull
    private int phoneNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private Set<PriceRequest> priceRequests;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private Set<Order> orders;

    public Client() {
    }

    public Client(Client c) {
        this(c.getLastName(), c.getPhoneNumber(), c.getPriceRequests(), c.getOrders());
    }

    public Client(@NotBlank @Size(min = 2, max = 120) @SafeHtml(groups = {View.Web.class}) String lastName,
                  @NotNull int phoneNumber, Set<PriceRequest> priceRequests, Set<Order> orders) {
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.priceRequests = priceRequests;
        this.orders = orders;
    }

    public Client(Integer id, String name, @NotBlank @Size(min = 2, max = 120)
    @SafeHtml(groups = {View.Web.class}) String lastName, @NotNull int phoneNumber, Set<PriceRequest> priceRequests,
                  Set<Order> orders) {
        super(id, name);
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.priceRequests = priceRequests;
        this.orders = orders;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<PriceRequest> getPriceRequests() {
        return priceRequests;
    }

    public void setPriceRequests(Set<PriceRequest> priceRequests) {
        this.priceRequests = priceRequests;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
