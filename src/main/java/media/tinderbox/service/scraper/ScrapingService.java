package media.tinderbox.service.scraper;

public sealed interface ScrapingService permits ScrapingServiceImpl {

    public String scrapeContent();

}
