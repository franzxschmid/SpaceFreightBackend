package de.hbt.planetexpressbackend.boundary;

import de.hbt.planetexpressbackend.control.PartRepository;
import de.hbt.planetexpressbackend.entity.Part;
import de.hbt.planetexpressbackend.exception.PartNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
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

    @PostMapping
    Part newPart(@RequestBody Part part, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()){
            throw new ValidationException("Part can not added in the PartsList;");
        }
        return partRepository.save(part);
    }

    @GetMapping("/parts/{id}")
    Part one(@PathVariable Long id) {

        return partRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }

    @PutMapping("/parts/{id}")
    Part replacePart(@RequestBody Part newPart, @PathVariable Long id) {
         return partRepository.findById(id).map(part -> {
             part.setName(newPart.getName());
             part.setQuantity(newPart.getQuantity());
             return partRepository.save(part);
         }).orElseGet(()->{
             newPart.setID(id);
             return partRepository.save(newPart);
         });


    }

    @DeleteMapping("/parts/{id}")
    void deletePart(@PathVariable Long id) {
        partRepository.deleteById(id);
    }

}
