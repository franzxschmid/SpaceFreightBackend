package de.hbt.planetexpressbackend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "PARTS_DB")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Part {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "QUANTITY")
    private int quantity;

    public Part(String name, int quantity) {
        this.name = name;
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
