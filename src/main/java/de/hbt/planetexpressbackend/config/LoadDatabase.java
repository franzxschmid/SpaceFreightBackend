package de.hbt.planetexpressbackend.config;

import de.hbt.planetexpressbackend.control.PartRepository;
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
            log.info("Preloading " + repository.save(Part.createPart("Weltraumdachziegel", 12)));
            log.info("Preloading " + repository.save( Part.createPart("Sauerstoffflaschen", 11)));
            log.info("Preloading " + repository.save( Part.createPart("Astronautennahrung", 15)));
            log.info("Preloading " + repository.save( Part.createPart("Raumanzüge", 14)));
            log.info("Preloading" + repository.save(Part.createPartWithEmptyQuantity("Headset")) );
        };
    }
}
