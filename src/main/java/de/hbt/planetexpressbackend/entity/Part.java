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


    @Column
    private String name;


    @Column
    private boolean visible = true;


    public Part(String name) {
        this.name = name;

    }

    public Part(Long id, String name) {
        this.name = name;
        this.id = id;
    }


    public static Part createPart(String name ) {
        return new Part(name);

    }


    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Part))
            return false;
        Part part = (Part) o;
        return Objects.equals(this.id, part.id)
                && Objects.equals(this.name, part.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    @Override
    public String toString() {
        return "{id=" + id + ", name=" + name + "}";
    }

}
