package de.hbt.planetexpressbackend.boundary;

import de.hbt.planetexpressbackend.control.FreighterRepository;
import de.hbt.planetexpressbackend.entity.Freighter;
import de.hbt.planetexpressbackend.entity.Part;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class FreighterController {
    private final FreighterRepository repository;

    public FreighterController(FreighterRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/freighters", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Freighter> getAll() {
        return repository.findAll();
    }

    @PostMapping(value = "/freighters", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Freighter> saveFreighter(@RequestBody Freighter freighter) {
        if (StringUtils.isEmpty(freighter.getName())) {
            return ResponseEntity.badRequest().build();
        }
        if (repository.existsById(freighter.getId())) {
            Freighter existPart;
            existPart = repository.findById(freighter.getId()).get();
            if (!existPart.isVisible()) {
                repository.findById(freighter.getId()).get().setVisible(true);
            }
            return ResponseEntity.ok(existPart);
        } else {
            return ResponseEntity.ok(repository.save(freighter));
        }

    }

    @GetMapping(value = "/freighters/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Freighter> getFreighter(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).orElseThrow(() -> new RuntimeException("the FREIGHTER was deleted or it doesn't exist !!! ")));
    }


    @PatchMapping(value = {"/freighters/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Freighter> resetFreighter(@PathVariable Long id) {
        repository.findById(id).map(freighter -> {
            freighter.setVisible(true);
            return ResponseEntity.ok(repository.save(freighter));
        });
        return ResponseEntity.ok(repository.findById(id).orElseThrow(() -> new RuntimeException("the freighter doesn't exist")));
    }

    @DeleteMapping("/freighters/{id}")
    void deleteFreighter(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping(value = "freighters/{id}/{value}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Freighter> updateFreighter(@PathVariable Long id, @PathVariable String value) throws ParseException {
        if (StringUtils.isEmpty(value)) {
            return ResponseEntity.badRequest().build();
        } else {
            if (Integer.parseInt(value) > 0 && value.length() >= 10) {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(value);
                repository.findById(id).map(freighter -> {
                    freighter.setDate(date);
                    return freighter;
                });
            } else {
                repository.findById(id).map(freighter -> {
                    freighter.setName(value);
                    return freighter;
                });
            }
            return ResponseEntity.ok(repository.findById(id).orElseThrow(() -> new RuntimeException("it doesnt update")));
        }
    }

    @PostMapping(value = "freighters/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Freighter> addPart(@RequestBody Part part, @PathVariable Long id) {
        repository.findById(id).map(freighter -> {
            freighter.setPartInComponent(part);
            return repository.save(freighter);
        });
        return ResponseEntity.ok(repository.findById(id).orElseThrow(() -> new RuntimeException("it doesnt update")));
    }


}
