package ai.pribela.glossapp.security;

import ai.pribela.glossapp.repository.LearnerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final LearnerRepository learnerRepository;

    public CustomUserDetailsService(LearnerRepository learnerRepository) {
        this.learnerRepository = learnerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return learnerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find username " + username));
    }
}
