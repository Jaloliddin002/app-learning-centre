package uz.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class AuditorAwareImp implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails)
            return Optional.of(((UserDetails) principal).getUsername());
        return Optional.of(principal.toString());

    }
}
