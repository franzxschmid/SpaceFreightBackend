package de.hbt.planetexpressbackend.boundary;

import de.hbt.planetexpressbackend.control.PartRepository;
import de.hbt.planetexpressbackend.entity.Part;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PartController {
    private final PartRepository partRepository;

    public PartController(PartRepository repository) {
        this.partRepository = repository;
    }

    @GetMapping("/parts")
    List<Part> getAll() {
        return partRepository.findAll();

    }

    @PostMapping("/parts")
    Part newPart(@RequestBody Part part, BindingResult bindingResult) throws ValidationException {
        PartRepository pr = partRepository;
        if (pr.findAll().stream().filter(p -> p.getId().equals(part.getId()) || p.getName().equals(part.getName())).count() > 0) {
            return null;
        } else {
            return partRepository.save(part);
        }

    }

    @GetMapping("/parts/{id}")
    Part one(@PathVariable Long id) {

        return partRepository.findById(id).orElseThrow(() -> new RuntimeException("there is no Part with the ID you entered"));
    }

    @PutMapping("/parts/{id}/{value}")
    Part updatePart(@PathVariable Long id, @PathVariable String value) {
        if (value.equals("decr")) {
            return partRepository.findById(id).map(part -> {
                part.setQuantity(part.getQuantity() - 1);
                return partRepository.save(part);
            }).orElseGet(() -> null);
        } else {
            int intvalue = Integer.valueOf(value);
            if (intvalue > 0) {
                return partRepository.findById(id).map(part -> {
                    part.setQuantity(intvalue);
                    return partRepository.save(part);
                }).orElseGet(() -> null);
            } else {
                return partRepository.findById(id).map(part -> {
                    part.setQuantity(intvalue * -1);
                    return partRepository.save(part);
                }).orElseGet(() -> null);
            }
        }
    }


    @DeleteMapping("/parts/{id}")
    void deletePart(@PathVariable Long id) {
        partRepository.deleteById(id);

    }

}
