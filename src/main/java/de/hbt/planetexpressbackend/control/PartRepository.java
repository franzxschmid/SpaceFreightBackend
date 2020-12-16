package de.hbt.planetexpressbackend.control;


import de.hbt.planetexpressbackend.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;


// Interface mit Methoden zum: Lesen, Löschen und Erstellen gegen einen Backend-Speicher
@Repository
public interface PartRepository extends JpaRepository<Part, Long> {


    @Query(value = "SELECT * FROM Part part WHERE part.deleted = true", nativeQuery = true)
    Collection<Part> getDeletedPart();

}
