package de.hbt.planetexpressbackend.control;


import de.hbt.planetexpressbackend.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface mit Methoden zum: Lesen, Löschen und Erstellen gegen einen Backend-Speicher
public interface PartRepository extends JpaRepository<Part, Long> {

}
