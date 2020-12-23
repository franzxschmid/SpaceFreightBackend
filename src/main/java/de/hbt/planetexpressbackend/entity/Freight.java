package de.hbt.planetexpressbackend.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@NoArgsConstructor
@Data
@Getter
@Table
@Entity
@SQLDelete(sql = "UPDATE freighter SET visible=false WHERE id=? ")
public class Freight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String name;
    @Column
    private Date date = new Date();
    @Column
    private boolean visible =  true;
    @ElementCollection
    private List<Component> components = new ArrayList<>();

    public Freight(String name) {
        this.name = name;

    }

    public static Freight createFreight(String name) {
        return new Freight(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void addComponent(Component component) {
        if (components.isEmpty() || !components.contains(component)) {
            components.add(component);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Freight that = (Freight) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(date, that.date) &&
                Objects.equals(components, that.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, components);
    }

    @Override
    public String toString() {
        return "FreightPlan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", components=" + components +
                '}';
    }


}
