package de.hbt.planetexpressbackend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Part {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int quantity;

    public Part() {
    }

    public Part(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Part))
            return false;
        Part part = (Part) o;
        return Objects.equals(this.id, part.id) && Objects.equals(this.name, part.name) && Objects.equals(this.quantity, part.quantity);
    }


    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.quantity);
    }

    @Override
    public String toString() {
        return "ID: " + id + ",  NAME: " + name + ",  QUANTITY: " + quantity;
    }

}
