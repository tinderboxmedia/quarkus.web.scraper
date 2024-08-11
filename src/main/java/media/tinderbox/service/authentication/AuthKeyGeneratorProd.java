package media.tinderbox.service.authentication;

import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;
import jakarta.inject.Singleton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
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
            Log.warn("Issue with creating authentication file: " + exception.getMessage());
        }

        // Content
        if (authSecret.length() == 0) {
            try (FileWriter writer = new FileWriter(authSecret.getAbsolutePath())) {
                String randomAuthKey = generateSafeToken();
                writer.write(randomAuthKey);
                this.authKey = randomAuthKey;
                Log.info("Random authKey written to authentication file.");
            } catch (IOException exception) {
                Log.warn("Issue with writing to authentication file: " + exception.getMessage());
            }
        } else {
            try (Scanner scanner = new Scanner(authSecret)) {
                this.authKey = scanner.nextLine();
                Log.info("Existing authKey read from authentication file.");
            } catch (NoSuchElementException | FileNotFoundException exception) {
                Log.warn("Issue with reading from authentication file: " + exception.getMessage());
            }
        }

        // Notify
        Log.info("Authentication key can be found in: " + authSecret.getAbsolutePath());
    }

    public String getAuthKey() {
        return authKey;
    }

    private String generateSafeToken() {
        // Generate
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);

        // Make readable
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(bytes);
    }
}
