package de.hbt.planetexpressbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
@SQLDelete(sql = "UPDATE freightone SET visible=false WHERE id=? ")
public class FreightOne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Date date = new Date();

    @Column
    @OneToMany
    private List<PartOne> partOneList = new ArrayList<>();

    @Column
    private boolean visible = true;
}
