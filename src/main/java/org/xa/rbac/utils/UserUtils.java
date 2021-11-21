package org.xa.rbac.utils;
import org.xa.rbac.model.User;
import org.springframework.security.core.context.SecurityContextHolder;


public class UserUtils {
    public static User getCurrentUser() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
