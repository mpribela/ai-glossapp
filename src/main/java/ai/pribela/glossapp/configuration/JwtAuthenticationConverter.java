package ai.pribela.glossapp.configuration;

import ai.pribela.glossapp.repository.LearnerRepository;
import ai.pribela.glossapp.repository.data.Learner;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final LearnerRepository learnerRepository;

    public JwtAuthenticationConverter(LearnerRepository learnerRepository) {
        this.learnerRepository = learnerRepository;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String auth0Id = jwt.getSubject();
        Learner learner = learnerRepository.findByAuth0Id(auth0Id).orElseGet(() -> {
            String username = jwt.getClaimAsString("username");
            return learnerRepository.save(new Learner(username, auth0Id));
        });
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");

        return new UserPrincipalAuthenticationToken(learner, jwt, authorities);
    }
}
