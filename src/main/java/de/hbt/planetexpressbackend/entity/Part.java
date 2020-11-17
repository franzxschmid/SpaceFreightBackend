package de.hbt.planetexpressbackend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Getter
@Setter
@Table(name = "part")
public class Part {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column(nullable = false)
    private int quantity;

    public Part( String name, int quantity) {

        this.name = name;
        this.quantity = quantity;
    }


    public Long getID() {
        return id;
    }

    public void setID(Long id) {
        this.id = id;
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
        return "id: " + id + ",  name: " + name + ",  quantity: " + quantity;
    }
}
