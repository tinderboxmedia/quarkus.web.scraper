package media.tinderbox.service.authentication;

public sealed interface AuthKeyGenerator permits AuthKeyGeneratorDev, AuthKeyGeneratorProd {

    public String getAuthKey();

}
