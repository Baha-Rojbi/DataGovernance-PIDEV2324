package tn.esprit.gouvernance_donnees_backend.Configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GrantedAuthorityDeserializer extends StdDeserializer<GrantedAuthority> {

    public GrantedAuthorityDeserializer() {
        super(GrantedAuthority.class);
    }

    @Override
    public GrantedAuthority deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String authority = node.asText();
        return new SimpleGrantedAuthority(authority);
    }
}
