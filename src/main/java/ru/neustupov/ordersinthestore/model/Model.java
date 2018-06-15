package ru.neustupov.ordersinthestore.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "models")
public class Model extends AbstractNamedEntity{

    public Model(Integer id, String name) {
        super(id, name);
    }

    public Model() {
    }

    public Model(Model m){
        this(m.getId(), m.getName());
    }
}
