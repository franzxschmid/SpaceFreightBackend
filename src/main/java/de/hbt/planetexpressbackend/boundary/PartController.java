package de.hbt.planetexpressbackend.boundary;

import de.hbt.planetexpressbackend.control.PartRepository;
import de.hbt.planetexpressbackend.entity.Part;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PartController {
    private final PartRepository partRepository;

    public PartController(PartRepository repository) {
        this.partRepository = repository;
    }


    @GetMapping(value = "/parts", produces = MediaType.APPLICATION_JSON_VALUE)
    Collection<Part> getAll() {
        return partRepository.getVisiblePart();
    }


    @PostMapping(value = "/parts", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Part> newPart(@RequestBody Part part) {
        if (!StringUtils.isEmpty(part.getName())) {
            if (partRepository.existsById(part.getId())) {
                Part existPart = partRepository.findById(part.getId()).get();
                if (!existPart.isVisible()) {
                    partRepository.findById(part.getId()).get().setVisible(true);
                }
                return ResponseEntity.ok(existPart);
            } else {
                return ResponseEntity.ok(partRepository.save(part));
            }
        } else {
            return ResponseEntity.badRequest().build();
        }

    }


    @GetMapping(value = {"/parts/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Part> getPart(@PathVariable Long id) {

        return ResponseEntity.ok(partRepository.findById(id).filter(p -> p.isVisible())
                .orElseThrow(() -> new RuntimeException("the part was deleted or it doesn't exist")));
    }

    @PatchMapping(value = {"/parts/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Part> resetPart(@PathVariable Long id) {
        partRepository.findById(id).map(part -> {
            part.setVisible(true);
            return ResponseEntity.ok(partRepository.save(part));
        });

        return ResponseEntity.ok(partRepository.findById(id).orElseThrow(() -> new RuntimeException("the part doesn't exist")));
    }




    @DeleteMapping("/parts/{id}")
    void deletePart(@PathVariable Long id) {
        partRepository.deleteById(id);
    }

}


