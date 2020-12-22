package de.hbt.planetexpressbackend.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.*;




@NoArgsConstructor
@Data
@Getter
@Table
@Entity
public class Freighter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String name;
    @Column
    private Date  date ;
    @Column
    private boolean visible ;
    @ElementCollection
    private List<Component> components;

    public Freighter( String name) {
        this.name = name;
        this.date = new Date();
        this.visible = true;
        this.components = new ArrayList<>();
        setDate(date);
        setVisible(visible);
    }
    public static Freighter createFreighter(String name){
        return new Freighter(name);
    }

    public void setName(String name){
        this.name = name;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setPartInComponent(Part part) {
       components.add(new Component(part, part.getQuantity()));
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Freighter that = (Freighter) o;
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
