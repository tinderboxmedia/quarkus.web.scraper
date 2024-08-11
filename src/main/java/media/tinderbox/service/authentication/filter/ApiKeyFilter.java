package media.tinderbox.service.authentication.filter;

import io.quarkus.security.UnauthorizedException;
import jakarta.ws.rs.container.ContainerRequestContext;
import media.tinderbox.service.authentication.AuthKeyGenerator;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;

public class ApiKeyFilter {

    private final AuthKeyGenerator authKeyGenerator;

    public ApiKeyFilter(AuthKeyGenerator authKeyGenerator) {
        this.authKeyGenerator = authKeyGenerator;
    }

    @ServerRequestFilter(preMatching = true)
    public void filterApiKey(ContainerRequestContext containerRequestContext) {
        String apiKeyHeader = containerRequestContext.getHeaderString("x-api-key");
        if (!authKeyGenerator.getAuthKey().equals(apiKeyHeader)) {
            throw new UnauthorizedException();
        }
    }

}
