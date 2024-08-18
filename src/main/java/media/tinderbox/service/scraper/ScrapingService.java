package media.tinderbox.service.scraper;

import media.tinderbox.data.SearchBody;

public sealed interface ScrapingService permits ScrapingServiceImpl {

    public String scrapeContent(SearchBody searchBody);

}
