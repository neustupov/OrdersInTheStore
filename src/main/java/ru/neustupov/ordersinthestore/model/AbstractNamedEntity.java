package ru.neustupov.ordersinthestore.model;

import org.hibernate.validator.constraints.SafeHtml;
import ru.neustupov.ordersinthestore.View;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class AbstractNamedEntity extends AbstractBaseEntity{

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    @SafeHtml(groups = {View.Web.class})
    protected String name;

    public AbstractNamedEntity() {
    }

    protected AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format("Entity %s (%s, '%s')", getClass().getName(), id, name);
    }
}
