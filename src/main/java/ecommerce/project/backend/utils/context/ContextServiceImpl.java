package ecommerce.project.backend.utils.context;

import ecommerce.project.backend.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ContextServiceImpl implements ContextService {
    @Override
    public User loadUserFromContext() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
