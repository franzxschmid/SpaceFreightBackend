package de.hbt.planetexpressbackend.boundary;


import de.hbt.planetexpressbackend.control.PartRepository;
import de.hbt.planetexpressbackend.entity.Part;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
public class PartControllerTest {

    private final String uri = "/parts/";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PartRepository partRepository;

    @BeforeEach
    void setUp() {
        partRepository.deleteAll();
    }


    private Part createNewPart() {

        return  Part.createPart( "Lautsprecher", 7);

    }


    private List<Part> findAllParts() {
        ResponseEntity<List<Part>> response = testRestTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        return response.getBody();
    }


    // with....should...
    @Test
    public void shouldSaveNewPart() {
        ResponseEntity<Part> response = testRestTemplate.postForEntity(uri, createNewPart(), Part.class);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(findAllParts()).contains(response.getBody());
    }

    @Test
    public void withSavedPart_shouldGetSavedPartById() {

        ResponseEntity<Part> savePart = testRestTemplate.postForEntity(uri , createNewPart(), Part.class);
        ResponseEntity<Part> getPart = testRestTemplate.getForEntity(uri + Objects.requireNonNull(savePart.getBody()).getId(), Part.class);

        assertThat(getPart.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(findAllParts()).contains(savePart.getBody());
        assertThat(findAllParts()).contains(getPart.getBody());
        assertThat(savePart).isEqualTo(getPart);
    }

    @Test
    public void withSavedPart_shouldUpdateTheSavedPart() {
        ResponseEntity<Part> savePart = testRestTemplate.postForEntity(uri , createNewPart(), Part.class);
        ResponseEntity<Part> response = testRestTemplate.exchange(uri + Objects.requireNonNull(savePart.getBody()).getId() + "/89" , HttpMethod.PUT, null, Part.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void whenDeletingPart_shouldNoLongerReturnDeletedPart() {
        ResponseEntity<Part> savePart = testRestTemplate.postForEntity(uri , createNewPart(), Part.class);
        testRestTemplate.delete(uri + savePart.getBody().getId());
        assertThat(findAllParts())
                .extracting(Part::getId, Part::getName)
                .doesNotContain(Tuple.tuple(8L, "VideoRecorder"));

    }


}
