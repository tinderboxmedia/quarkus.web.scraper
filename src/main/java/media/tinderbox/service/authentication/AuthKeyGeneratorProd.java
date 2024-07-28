package media.tinderbox.service.authentication;

import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.runtime.Startup;
import jakarta.inject.Singleton;

@Startup
@Singleton
@IfBuildProfile("prod")
public final class AuthKeyGeneratorProd implements AuthKeyGenerator {

    private final String authKey;

    public AuthKeyGeneratorProd() {
        String currentDir = System.getProperty("user.dir");
        this.authKey = "saved-and-secured-key - " + currentDir;
    }

    public String getAuthKey() {
        return authKey;
    }
}
