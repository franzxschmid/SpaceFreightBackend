package de.hbt.planetexpressbackend.control;

import de.hbt.planetexpressbackend.entity.Freight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreightRepository extends JpaRepository<Freight, Long> {

}
