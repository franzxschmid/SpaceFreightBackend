package de.hbt.planetexpressbackend.config;

import de.hbt.planetexpressbackend.control.FreighterRepository;
import de.hbt.planetexpressbackend.control.PartRepository;
import de.hbt.planetexpressbackend.entity.Freight;
import de.hbt.planetexpressbackend.entity.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// Preload-Klasse: Spring Boot führt alle CommandLinerRunner-Beans aus wenn Applikationskontext geladen wird
@Configuration
@EnableJpaAuditing
class LoadDatabase {



    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    // Erstellt Part-Entitäten und speichert diese
    @Bean
    CommandLineRunner initDatabase(PartRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Part("Weltraumdachziegel",12)));
            log.info("Preloading " + repository.save(new Part("Sauerstoffflaschen",13)));
            log.info("Preloading " + repository.save(new Part("Astronautennahrung",22)));
            log.info("Preloading " + repository.save(new Part("Raumanzüge",32)));
            log.info("Preloading " + repository.save( Part.createPart("Headset",4)) );
        };
    }

    @Bean
    CommandLineRunner initDatabaseFreighter(FreighterRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(Freight.createFreight("Construction1")));
            log.info("Preloading " + repository.save(Freight.createFreight("Construction2")));
            log.info("Preloading " + repository.save(Freight.createFreight("Construction3")));
            log.info("Preloading " + repository.save(Freight.createFreight("Construction4")));
            log.info("Preloading " + repository.save(Freight.createFreight("Construction5")));

        };
    }
}
