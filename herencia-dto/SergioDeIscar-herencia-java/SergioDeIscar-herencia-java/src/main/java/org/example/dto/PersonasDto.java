package org.example.dto;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "personas")
public class PersonasDto {
    public PersonasDto(List<PersonaDto> personas) {
        this.personas = personas;
    }
    @ElementList(name = "persona", inline = true)
    private List<PersonaDto> personas;

    public List<PersonaDto> getPersonas() {
        return personas;
    }
}