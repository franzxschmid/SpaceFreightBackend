package de.hbt.planetexpressbackend.control;

import de.hbt.planetexpressbackend.entity.Freight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreightRepository extends JpaRepository<Freight, Long> {

}
