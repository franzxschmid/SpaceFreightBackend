package de.hbt.planetexpressbackend.entity;


import lombok.*;

import javax.persistence.Embeddable;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Component {
    private  Part part;
    private Integer quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Component component = (Component) o;
        return Objects.equals(part, component.part) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(part);
    }
}
