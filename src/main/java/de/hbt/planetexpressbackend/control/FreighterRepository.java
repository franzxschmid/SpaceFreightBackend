package de.hbt.planetexpressbackend.control;

import de.hbt.planetexpressbackend.entity.Freighter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreighterRepository extends JpaRepository<Freighter, Long> {

}
