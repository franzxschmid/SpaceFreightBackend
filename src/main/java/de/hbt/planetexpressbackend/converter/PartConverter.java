package de.hbt.planetexpressbackend.converter;

import de.hbt.planetexpressbackend.entity.Part;

import javax.persistence.AttributeConverter;

public class PartConverter implements AttributeConverter<Part, String> {

    @Override
    public Part convertToEntityAttribute(String part) {
        if (part == null || part.isEmpty()) {
            return null;
        }
        String[] pieces = part.split(", ");
        if (pieces.length == 0) {
            return null;
        }
        Part partConverted = new Part();
        String partId = !pieces[0].isEmpty() ? pieces[0] : null;
        if (partId != null) {
            partConverted.setId(Long.valueOf(partId));
        }
        if (pieces.length >= 2 && pieces[1] != null && !pieces[1].isEmpty()) {

            partConverted.setName(pieces[1]);
        }
        if (pieces.length >= 3 && pieces[2] != null && !pieces[2].isEmpty()) {

            partConverted.setQuantity(Integer.parseInt(pieces[2]));
        }
        if (pieces.length >= 4 && pieces[3] != null && !pieces[3].isEmpty()) {

            partConverted.setVisible(pieces[3].equals("true"));
        }
        return partConverted;
    }


    @Override
    public String convertToDatabaseColumn(Part part) {
        if (part == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (part.getId() != null && !part.getName().isEmpty()) {
            sb.append(part.getId());
            sb.append(part.getName());
            sb.append(part.getQuantity());
            sb.append(part.isVisible());
        }
        return sb.toString();
    }


}
