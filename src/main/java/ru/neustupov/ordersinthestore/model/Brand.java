package ru.neustupov.ordersinthestore.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "brands")
public class Brand extends AbstractNamedEntity {

    public Brand() {
    }

    public Brand(Integer id, String name) {
        super(id, name);
    }

    public Brand(Brand b){
        this(b.getId(), b.getName());
    }
}
