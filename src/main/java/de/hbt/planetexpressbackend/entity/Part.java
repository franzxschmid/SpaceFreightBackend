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
@Table
public class Part {

    @Id
    private Long id;


    private String name;

    @Column(nullable = false)
    private int quantity;




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
        return "{id=" + id + ", name=" + name + ", quantity=" + quantity + "}" ;
    }

}
