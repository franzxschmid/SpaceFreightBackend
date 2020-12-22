package de.hbt.planetexpressbackend.control;


import de.hbt.planetexpressbackend.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;


@Repository
public interface PartRepository extends JpaRepository<Part, Long> {


    @Query(value = "SELECT part FROM Part part WHERE part.visible = true")
    Collection<Part> getVisiblePart();

/*
    @Transactional
    @Modifying
    @Query(value = "update Part part SET part.visible = true  WHERE part.id =:id  ")
    void setPartUnVisible(@Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "update Part part SET part.visible = false WHERE part.id =:id  ")
    void setPartVisible(@Param("id") long id);


    @Transactional
    @Modifying
    @Query(value = "update Part part SET  part.quantity =:quantity WHERE part.id =:id  and part.visible = false  ")
    void updatePartBySetQuantity(@Param("id") long id, @Param("quantity") Integer quantity);

    @Transactional
    @Modifying
    @Query(value = "update Part part SET part.quantity = part.quantity - 1  WHERE part.id =:id  and part.visible = false ")
    void updatePartByDecrementQuantity(@Param("id") long id);*/

}
