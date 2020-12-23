package de.hbt.planetexpressbackend.boundary;

import de.hbt.planetexpressbackend.control.FreighterRepository;
import de.hbt.planetexpressbackend.entity.Component;
import de.hbt.planetexpressbackend.entity.Freight;
import de.hbt.planetexpressbackend.entity.Part;
import org.assertj.core.util.DateUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
public class FreighterController {
    private final FreighterRepository repository;

    public FreighterController(FreighterRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/freights", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Freight> getAll() {
        return repository.findAll();
    }

    @PostMapping(value = "/freights", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Freight> saveFreighter(@RequestBody Freight freight) {
        if (StringUtils.isEmpty(freight.getName())) {
            return ResponseEntity.badRequest().build();
        }
        if (repository.existsById(freight.getId())) {
            Freight existPart;
            existPart = repository.findById(freight.getId()).get();
            if (!existPart.isVisible()) {
                repository.findById(freight.getId()).get().setVisible(true);
            }
            return ResponseEntity.ok(existPart);
        } else {
            return ResponseEntity.ok(repository.save(freight));
        }

    }

    @GetMapping(value = "/freights/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Freight> getFreighter(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).filter(fr -> fr.isVisible())
                .orElseThrow(() -> new RuntimeException("the FREIGHT was deleted or it doesn't exist !!! ")));
    }


    @PatchMapping(value = {"/freights/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Freight> resetFreighter(@PathVariable Long id) {
        repository.findById(id).map(freight -> {
            freight.setVisible(true);
            return ResponseEntity.ok(repository.save(freight));
        });
        return ResponseEntity.ok(repository.findById(id).orElseThrow(() -> new RuntimeException("the freight doesn't exist")));
    }



    @PutMapping(value = "freights/{id}/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Freight> updateFreighterDate(@PathVariable Long id, @PathVariable String date) throws ParseException {
        if (StringUtils.isEmpty(date)) {
            return ResponseEntity.badRequest().build();
        } else {
            if (date.length() >= 8) {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Date changedDate = formatter.parse(date);
                System.out.println("hier kommt das Datum: " + changedDate);
                repository.findById(id).map(freight -> {

                    freight.setDate(changedDate);
                    return freight;
                });
            } else {
                repository.findById(id).map(freighter -> {
                    freighter.setName(date);
                    return freighter;
                });
            }
            return ResponseEntity.ok(repository.findById(id).orElseThrow(() -> new RuntimeException("it doesnt update")));
        }
    }

    @PostMapping(value = "freights/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Freight> addPart(@RequestBody Part part, @PathVariable Long id) {
        repository.findById(id).map(freighter -> {
            freighter.addComponent(new Component(part, part.getQuantity()));
            return repository.save(freighter);
        });
        return ResponseEntity.ok(repository.findById(id).orElseThrow(() -> new RuntimeException("it doesnt update")));
    }



    @DeleteMapping("/freights/{id}")
    void deleteFreighter(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
