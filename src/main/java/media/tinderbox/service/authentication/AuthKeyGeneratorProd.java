package media.tinderbox.service.authentication;

import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;
import jakarta.inject.Singleton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Startup
@Singleton
@IfBuildProfile("prod")
public final class AuthKeyGeneratorProd implements AuthKeyGenerator {

    private String authKey;

    public AuthKeyGeneratorProd() {
        // Setup
        String currentDir = System.getProperty("user.dir");
        File authSecret = new File(currentDir, "auth" + File.separator + "authSecret.txt");

        // Folder
        if (authSecret.getParentFile().mkdir()) {
            Log.info("Authentication folder has been created.");
        } else {
            Log.info("Authentication folder is already present.");
        }

        // File
        try {
            if (authSecret.createNewFile()) {
                Log.info("Authentication file has been created.");
            } else {
                Log.info("Authentication file is already present.");
            }
        } catch (IOException exception) {
            Log.warn("Issue with creating authentication file.");
        }

        // Content
        if (authSecret.length() == 0) {
            try (FileWriter writer = new FileWriter(authSecret.getAbsolutePath())) {
                String randomAuthKey = "RandomKey123";
                writer.write(randomAuthKey);
                this.authKey = randomAuthKey;
                Log.info("Random authKey written to authentication file.");
            } catch (IOException e) {
                Log.warn("Issue with writing to authentication file.");
            }
        } else {
            try (Scanner scanner = new Scanner(authSecret)) {
                this.authKey = scanner.nextLine();
                Log.info("Existing authKey read from authentication file.");
                scanner.nextLine();
            } catch (NoSuchElementException | FileNotFoundException exception) {
                Log.warn("Issue with reading from authentication file.");
            }
        }

        // Notify
        Log.info("Authentication key can be found in: " + authSecret.getAbsolutePath());
    }

    public String getAuthKey() {
        return authKey;
    }
}
