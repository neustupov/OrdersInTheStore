package ru.neustupov.ordersinthestore.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "products")
public class Product extends AbstractBaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_request_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PriceRequest priceRequest;

    @Column(name = "add_date", columnDefinition = "date default current_date", nullable = false)
    @NotNull
    private LocalDate addDate;

    @Column(name = "price", nullable = false)
    @NotNull
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Model model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Brand brand;

    public Product() {
    }

    public Product(Product p){
        this(p.getId(), p.getPriceRequest(), p.getAddDate(), p.getPrice(), p.getType(), p.getModel(), p.getBrand());
    }

    public Product(PriceRequest priceRequest, @NotNull LocalDate addDate, @NotNull int price, Type type, Model model,
                   Brand brand) {
        this.priceRequest = priceRequest;
        this.addDate = addDate;
        this.price = price;
        this.type = type;
        this.model = model;
        this.brand = brand;
    }

    public Product(Integer id, PriceRequest priceRequest, @NotNull LocalDate addDate, @NotNull int price, Type type,
                   Model model, Brand brand) {
        super(id);
        this.priceRequest = priceRequest;
        this.addDate = addDate;
        this.price = price;
        this.type = type;
        this.model = model;
        this.brand = brand;
    }

    public PriceRequest getPriceRequest() {
        return priceRequest;
    }

    public void setPriceRequest(PriceRequest priceRequest) {
        this.priceRequest = priceRequest;
    }

    public LocalDate getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDate addDate) {
        this.addDate = addDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
