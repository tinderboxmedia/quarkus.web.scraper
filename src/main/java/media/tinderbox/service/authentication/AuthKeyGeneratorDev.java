package media.tinderbox.service.authentication;

import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.runtime.Startup;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Startup
@Singleton
@IfBuildProfile("dev")
public final class AuthKeyGeneratorDev implements AuthKeyGenerator {

    private final String authKey;

    public AuthKeyGeneratorDev(@ConfigProperty(name = "security.authentication.key") String authKey) {
        this.authKey = authKey;
    }

    public String getAuthKey() {
        return authKey;
    }
}
