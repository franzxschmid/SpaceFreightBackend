package de.hbt.planetexpressbackend.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.*;


@NoArgsConstructor
@Data
@Getter
@Table
@Entity
@SQLDelete(sql = "UPDATE freight SET visible=false WHERE id=? ")
public class Freight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private Date constructionDate = new Date();
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
        this.constructionDate.setTime(date.getTime());
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void addComponent(Component component) {
        for (Component comp : components){
             if (comp.getPart().getId().equals(component.getPart().getId())){
                comp.setQuantity(component.getQuantity());
                comp.getPart().setName(component.getPart().getName());

                return;
            }
        }
        if (!components.contains(component)){
            components.add(component);
        }

    }
    public void deleteComponent(Integer index) {
        if (components.size() >= index-1){
            components.remove(index-1);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Freight that = (Freight) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(constructionDate, that.constructionDate) &&
                Objects.equals(components, that.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, constructionDate, components);
    }

    @Override
    public String toString() {
        return "Freight{" +
                "id=" + id +
                ", name= '" + name + '\'' +
                ", constructionDate= " + constructionDate +
                ", components= " + components +
                '}';
    }


}
