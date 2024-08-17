package media.tinderbox.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import media.tinderbox.service.scraper.ScrapingService;

@Path("/scrape")
public class ScrapeController {

    final private ScrapingService scrapingService;

    public ScrapeController(ScrapingService scrapingService) {
        this.scrapingService = scrapingService;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String scrape() {
        return "Scrape with POST...";
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String scrape(Object object) {
        return scrapingService.scrapeContent();
    }

}
