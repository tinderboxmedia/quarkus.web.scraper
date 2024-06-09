package media.tinderbox.service.authentication;

import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;
import jakarta.inject.Singleton;
import java.io.File;

@Startup
@Singleton
public class AuthKeyGenerator {

    private final String authKey;

    public AuthKeyGenerator() {
        this.authKey = "23456";
    }

    public String getAuthKey() {
        return authKey;
    }
}
