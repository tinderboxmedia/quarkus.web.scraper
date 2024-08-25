package media.tinderbox.system;

import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;

@Startup
@Singleton
@IfBuildProfile("prod")
public class FileSystem {

    /**
     * Needed for Native Builds as otherwise Playwright can't load the drivers.
     */
    public FileSystem() throws URISyntaxException, IOException {
        Log.info("Creating Resource FileSystem...");
        FileSystems.newFileSystem(
                new URI("resource:/"),
                createResourceSettings());
    }

    private Map<String, String> createResourceSettings() {
        Map<String, String> resourceSettings = new HashMap<>();
        resourceSettings.put("create", "true");
        return resourceSettings;
    }
}
