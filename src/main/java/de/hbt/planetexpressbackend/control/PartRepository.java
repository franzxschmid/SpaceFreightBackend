package de.hbt.planetexpressbackend.control;


import de.hbt.planetexpressbackend.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, Long> {

}
