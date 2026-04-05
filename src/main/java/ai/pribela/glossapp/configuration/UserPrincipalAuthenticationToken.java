package ai.pribela.glossapp.configuration;

import ai.pribela.glossapp.repository.data.Learner;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;

public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {

    private final Learner learner;
    private final Jwt jwt;

    public UserPrincipalAuthenticationToken(Learner learner, Jwt jwt, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.learner = learner;
        this.jwt = jwt;
        setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return jwt;
    }

    @Override
    public Object getPrincipal() {
        return learner;
    }
}
