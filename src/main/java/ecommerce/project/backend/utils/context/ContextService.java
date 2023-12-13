package ecommerce.project.backend.utils.context;

import ecommerce.project.backend.entities.User;

public interface ContextService {
    User loadUserFromContext();
}
