package ru.neustupov.ordersinthestore.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "types")
public class Type extends AbstractNamedEntity{

    public Type() {
    }

    public Type(Integer id, String name) {
        super(id, name);
    }

    public Type(Type t){
        this(t.getId(), t.getName());
    }
}
