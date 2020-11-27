package de.hbt.planetexpressbackend.boundary;

import de.hbt.planetexpressbackend.entity.Part;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.Assert.*;

public class PartControllerTest extends AbstractTest {

    private  String uri = "/parts";

    @Override
    @Before
    public void setUp() {

        super.setUp();
    }


    @Test
    public void getAllParts() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Part[] partList = super.mapFromJson(content, Part[].class);
        assertNotNull(partList);
    }

    @Test
    public void savePart() throws Exception {
        Part part = new Part(9L, "Beatmungsgerät", 7);
            // we'll remember the List of Part
        MvcResult mvcResultBeforeSavePart = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String contentBefore = mvcResultBeforeSavePart.getResponse().getContentAsString();
        Part[] partListBefore = super.mapFromJson(contentBefore, Part[].class);



        String inputJson = super.mapToJson(part);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);



        MvcResult mvcResultAfterSavePart = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String contentAfter = mvcResultAfterSavePart.getResponse().getContentAsString();

        Part[] partListAfter = super.mapFromJson(contentAfter, Part[].class);
        // the new Part added, also size has increased
        assertTrue(partListAfter.length > partListBefore.length);
    }

    @Test
    public void one() throws Exception {
        Part part = new Part(9L, "Beatmungsgeraet", 7);

        String inputJson = super.mapToJson(part);
         mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        MvcResult mvcResultAfterSavePart = mvc.perform(MockMvcRequestBuilders.get(uri + "/9")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String content = mvcResultAfterSavePart.getResponse().getContentAsString();
        Part partFrom = super.mapFromJson(content, Part.class);
        assertTrue(part.getId().equals(partFrom.getId()));
        assertTrue(part.getName().equals(partFrom.getName()));
    }


    @Test
    public void updatePart() throws Exception {
        Part part = new Part(9L, "Beatmungsgeraet", 7);

        String inputJson = super.mapToJson(part);
        mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        Part partUpdate = new Part();
        partUpdate.setName("Lemon");
        partUpdate.setQuantity(6776);

        String inputJsonUpdate = super.mapToJson(part);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri+ "/9/"+ partUpdate.getQuantity())
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonUpdate)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "{\"id\":9,\"name\":\"Beatmungsgeraet\",\"quantity\":6776}");
    }


    @Test
    public void deletePart() throws Exception {
        Part part = new Part(21L, "Banane", 21);
        // first post new Object in to DB
        String inputJson = super.mapToJson(part);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        //
        MvcResult mvcResultBeforeDELETEPart = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String contentBefore = mvcResultBeforeDELETEPart.getResponse().getContentAsString();
        Part[] partListBeforeDELETE = super.mapFromJson(contentBefore, Part[].class);


        MvcResult mvcResultDELETE = mvc.perform(MockMvcRequestBuilders.delete(uri + "/21")).andReturn();
        int statusDELETE = mvcResultDELETE.getResponse().getStatus();
        assertEquals(200, statusDELETE);

        MvcResult mvcResultAfterDELETEPart = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String contentAfter = mvcResultAfterDELETEPart.getResponse().getContentAsString();
        Part[] partListAfterDELETE = super.mapFromJson(contentAfter, Part[].class);
        assertTrue(partListBeforeDELETE.length > partListAfterDELETE.length);

    }

}
