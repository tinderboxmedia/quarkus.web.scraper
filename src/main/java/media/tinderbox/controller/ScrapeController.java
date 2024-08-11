package media.tinderbox.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/scrape")
public class ScrapeController {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String scrape() {
        return "Scrape with POST...";
    }

}
