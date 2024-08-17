package media.tinderbox.service.scraper;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped

public final class ScrapingServiceImpl implements ScrapingService {

    @Override
    public String scrapeContent() {
        return "stuff";
    }

}