package de.hbt.planetexpressbackend.boundary;

import de.hbt.planetexpressbackend.control.PartRepository;
import de.hbt.planetexpressbackend.entity.Part;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PartController {

    // Repository wird injekted, soll im Controller in einen Web-Layer verpackt werden
    private final PartRepository partRepository;

    public PartController(PartRepository repository) {
        this.partRepository = repository;
    }

    // Routen zu den HTTP-Aufrufen: GET, POST, PUT und DELETE
    @GetMapping("/parts")
    List<Part> getAllParts() {
        return partRepository.findAll();
    }

    @PostMapping
    Part addPart(@RequestBody Part part) {
        return partRepository.save(part);
    }

    @GetMapping("/parts/{id}")
    Part getPartById(@PathVariable Long id) {
        return partRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }

    @PutMapping("/parts/{id}")
    Part replacePart(@RequestBody Part newPart, @PathVariable Long id) {

        return partRepository.findById(id)
                .map(part -> {
                    part.setName(newPart.getName());
                    part.setQuantity(newPart.getQuantity());
                    return partRepository.save(part);
                })
                .orElseGet(() -> {
                    newPart.setId(id);
                    return partRepository.save(newPart);
                });
    }

    @DeleteMapping("/parts/{id}")
    void deletePart(@PathVariable Long id) {
        partRepository.deleteById(id);
    }

}
