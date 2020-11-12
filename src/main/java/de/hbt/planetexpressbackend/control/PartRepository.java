package de.hbt.planetexpressbackend.control;


import de.hbt.planetexpressbackend.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Interface mit Methoden zum: Lesen, Löschen und Erstellen gegen einen Backend-Speicher
@Repository
public interface PartRepository extends JpaRepository<Part, Long> {

}
