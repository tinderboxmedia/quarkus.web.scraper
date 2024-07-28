package media.tinderbox.service.authentication;

import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.runtime.Startup;
import jakarta.inject.Singleton;

import java.io.File;
import java.nio.file.Files;

@Startup
@Singleton
@IfBuildProfile("prod")
public final class AuthKeyGeneratorProd implements AuthKeyGenerator {

    private final String authKey;

    public AuthKeyGeneratorProd() {
        String currentDir = System.getProperty("user.dir");
        File authSecret = new File(currentDir, "auth/authSecret.txt");

        String authSecretPath = authSecret.getAbsolutePath();

        // if empty
            // add secret key
        // else
            // read in key

        this.authKey = "saved-and-secured-key - " + authSecretPath;
    }

    public String getAuthKey() {
        return authKey;
    }
}
