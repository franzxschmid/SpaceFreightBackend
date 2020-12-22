package de.hbt.planetexpressbackend.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Getter
@Setter
@Table
@SQLDelete(sql = "UPDATE part SET visible=false WHERE id=? ")
public class Part implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(length = 100)
    private String name;

    @Column
    private int quantity;

    @Column
    private boolean visible = true;


    public Part(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;

    }

    public Part(Long id, String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
        this.id = id;
    }


    public static Part createPart(String name, Integer quantity) {
        return new Part(name, quantity);

    }


    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Part))
            return false;
        Part part = (Part) o;
        return Objects.equals(this.id, part.id)
                && Objects.equals(this.name, part.name)
                && Objects.equals(this.quantity, part.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.quantity);
    }

    @Override
    public String toString() {
        return "{id=" + id + ", name=" + name + ", quantity=" + quantity + "}";
    }

}
