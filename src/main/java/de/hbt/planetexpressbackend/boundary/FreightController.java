package de.hbt.planetexpressbackend.boundary;

import de.hbt.planetexpressbackend.control.FreightRepository;
import de.hbt.planetexpressbackend.entity.Component;
import de.hbt.planetexpressbackend.entity.Freight;
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
import java.util.Locale;

@RestController
public class FreightController {
    private final FreightRepository repository;

    public FreightController(FreightRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/freights", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Freight> getAll() {
        return repository.findAll();
    }

    @PostMapping(value = "/freights", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Freight> saveFreight(@RequestBody Freight freight) {
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
    ResponseEntity<Freight> getFreight(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).filter(fr -> fr.isVisible())
                .orElseThrow(() -> new RuntimeException("the FREIGHT was deleted or it doesn't exist !!! ")));
    }


    @PatchMapping(value = {"/freights/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Freight> resetFreight(@PathVariable Long id) {
        repository.findById(id).map(freight -> {
            freight.setVisible(true);
            return ResponseEntity.ok(repository.save(freight));
        });
        return ResponseEntity.ok(repository.findById(id).orElseThrow(() -> new RuntimeException("the freight doesn't exist")));
    }



    @PutMapping(value = "freights/{id}/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Freight> updateFreightDate(@PathVariable Long id, @PathVariable String date) throws ParseException {
        if (StringUtils.isEmpty(date)) {
            return ResponseEntity.badRequest().build();
        } else {
            if (date.length() >= 8) {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Date changedDate = formatter.parse(date);
                repository.findById(id).map(freight -> {

                    freight.setDate(changedDate);
                    return repository.save(freight);
                });
            } else {
                repository.findById(id).map(freight -> {
                    freight.setName(date);
                    return freight;
                });
            }
            return ResponseEntity.ok(repository.findById(id).orElseThrow(() -> new RuntimeException("it doesnt update")));
        }
    }

    @PostMapping(value = "freights/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Freight> addPart(@RequestBody Component component, @PathVariable Long id) {

        repository.findById(id).map(freight -> {
            freight.addComponent(component);
            return repository.save(freight);
        });
        return ResponseEntity.ok(repository.findById(id).orElseThrow(() -> new RuntimeException("it doesnt update")));
    }



    @DeleteMapping("/freights/{id}")
    void deleteFreight(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @DeleteMapping("freights/{id}/{partId}")
    void  deletePart(@PathVariable Long id, @PathVariable String partId) {
        repository.findById(id).map(freight -> {
            freight.deleteComponent(Integer.valueOf(partId));
           return repository.save(freight);
        });
    }

}
