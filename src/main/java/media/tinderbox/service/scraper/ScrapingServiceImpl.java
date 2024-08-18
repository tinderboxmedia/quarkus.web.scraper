package media.tinderbox.service.scraper;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import media.tinderbox.data.SearchBody;

@ApplicationScoped
public final class ScrapingServiceImpl implements ScrapingService {

    private String url;
    private String selector;
    private Integer extraWait;

    @Override
    public String scrapeContent(SearchBody searchBody) {
        // Store
        this.url = searchBody.url();
        this.selector = searchBody.selector();
        this.extraWait = searchBody.extraWait();
        // Create
        try (Playwright playwright = Playwright.create()) {
            try (Browser browser = playwright.chromium().launch()) {
                return scrapeContent(browser);
            }
        }
    }

    private String scrapeContent(Browser browser) {
        // Create
        Page page = browser.newPage();
        page.setDefaultTimeout(10 * 1000);
        Log.info("Navigating to: " + url);
        page.navigate(url);
        // Wait
        synchronized(page)
        {
            if (selector != null) {
                Log.info("Waiting for: " + selector);
                Locator locator = page.locator(selector);
                locator.isVisible();
            }
            try {
                if (extraWait != null && extraWait != 0) {
                    Log.info("Extra wait for: " + extraWait);
                    page.wait(extraWait);
                }
            } catch (InterruptedException error) {
                Log.warn("Issue with getting content: " + error.getMessage());
            }
        }
        // Return
        return page.content();
    }
}
