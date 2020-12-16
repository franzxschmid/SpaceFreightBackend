package de.hbt.planetexpressbackend.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Data
@Getter
@Setter
@Table
@Where(clause = "deleted='false' ")
@SQLDelete(sql = "UPDATE part SET deleted=true WHERE id=? ")
public class Part implements Serializable {


    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 100)
    private String name;

    @Column
    private int quantity;

    @Column
    private boolean deleted;


    private Part(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
        this.deleted = false;
    }

    public void unremove(){
        this.setDeleted(false);
    }

    public void remove(){
        this.setDeleted(true);
    }

    public static Part createPart(String name, Integer quantity) {
        return new Part(name, quantity);
    }

    public static Part createPartWithEmptyQuantity(String name) {
        return new Part(name, 0);
    }


    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Part))
            return false;
        Part part = (Part) o;
        return Objects.equals(this.id, part.id) && Objects.equals(this.name, part.name) && Objects.equals(this.quantity, part.quantity)
                && Objects.equals(this.deleted, part.deleted);
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
