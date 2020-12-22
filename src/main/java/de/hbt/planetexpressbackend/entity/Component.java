package de.hbt.planetexpressbackend.entity;


import lombok.*;

import javax.persistence.Embeddable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Component {
    private  Part part;
    private Integer quantity;

}
