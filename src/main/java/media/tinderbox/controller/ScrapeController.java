package media.tinderbox.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import media.tinderbox.service.authentication.AuthKeyGenerator;

@Path("/scrape")
public class ScrapeController {

    private final AuthKeyGenerator authKeyGenerator;

    public ScrapeController(AuthKeyGenerator authKeyGenerator) {
        this.authKeyGenerator = authKeyGenerator;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String scrape() {
        String authKey = authKeyGenerator.getAuthKey();
        return "Hello from Quarkus REST. With authKey: " + authKey;
    }

}
