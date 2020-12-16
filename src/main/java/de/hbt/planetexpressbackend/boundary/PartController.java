package de.hbt.planetexpressbackend.boundary;

import de.hbt.planetexpressbackend.control.PartRepository;
import de.hbt.planetexpressbackend.entity.Part;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PartController {
    private final PartRepository partRepository;

    public PartController(PartRepository repository) {

        this.partRepository = repository;
    }

    @GetMapping(value = "/parts", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Part> getAll() {
        return partRepository.findAll().stream().filter(el -> !el.isDeleted()).collect(Collectors.toList());

    }

    @PostMapping(value = "/parts", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Part> newPart(@RequestBody Part part) {
        if (partRepository.findAll().stream().filter(p -> p.getName().equals("") || p.getName().equals(part.getName())).count() > 0) {
            return null;
        } else {
            return ResponseEntity.ok(partRepository.save(part));
        }

    }

    @GetMapping(value = {"/parts/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Part> getPart(@PathVariable Long id) {

        return ResponseEntity.ok(partRepository.findById(id).filter(el -> !el.isDeleted())
                .orElseThrow(() -> new RuntimeException("the part was deleted or it doesn't exist")));

    }

    @GetMapping(value = {"/parts/{id}/{reset}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Part> resetPart(@PathVariable Long id, @PathVariable String reset) {
        if (reset.equals("reset")) {
            partRepository.getDeletedPart().forEach(part -> {
                if(part.getId().equals(id)) part.unremove();
                ResponseEntity.ok(partRepository.save(part));
            });
            return ResponseEntity.ok(partRepository.findById(id).filter(el -> !el.isDeleted())
                    .orElseThrow(() -> new RuntimeException("the part  doesn't exist")));
        }
        return null;
    }


    @PutMapping(value = "/parts/{id}/{value}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Part> updatePart(@PathVariable Long id, @PathVariable String value) {
        if (value.equals("decr")) {
            return partRepository.findById(id).map(part -> {
                part.setQuantity(part.getQuantity() > 0 ? part.getQuantity() - 1 : 0);
                return ResponseEntity.ok(partRepository.save(part));
            }).orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            int intValue = Integer.valueOf(value);
            if (intValue >= 0) {
                return partRepository.findById(id).map(part -> {
                    part.setQuantity(intValue);
                    return ResponseEntity.ok(partRepository.save(part));
                }).orElseGet(() -> null);
            } else {
                return partRepository.findById(id).map(part -> {
                    part.setQuantity(intValue * -1);
                    return ResponseEntity.ok(partRepository.save(part));
                }).orElseGet(() -> null);
            }
        }
    }


    @DeleteMapping("/parts/{id}")
    void deletePart(@PathVariable Long id) {
        partRepository.findById(id).map(part -> {
            part.remove();
            return ResponseEntity.ok(partRepository.save(part));
        });

    }

}
