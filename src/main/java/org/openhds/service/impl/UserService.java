package org.openhds.service.impl;

import org.openhds.errors.model.ErrorLog;
import org.openhds.repository.concrete.PrivilegeRepository;
import org.openhds.repository.concrete.RoleRepository;
import org.openhds.repository.concrete.UserRepository;
import org.openhds.security.model.Privilege;
import org.openhds.security.model.Role;
import org.openhds.security.model.User;
import org.openhds.security.model.UserDetailsWrapper;
import org.openhds.service.contract.AbstractUuidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by Wolfe on 7/1/2015.
 */
@Component
public class UserService extends AbstractUuidService<User, UserRepository> {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public User makePlaceHolder(String id, String name) {
        User user = new User();
        user.setUuid(id);
        user.setUsername(name);
        user.setFirstName(name);
        return user;
    }

    @Override
    public void validate(User user, ErrorLog errorLog) {
        super.validate(user, errorLog);
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username).get();
    }

    public Role findRoleByName(String name) {
        return roleRepository.findByName(name).get();
    }

    public Privilege findPrivledgeByGrant(Privilege.Grant grant) {
        return privilegeRepository.findByGrant(grant).get();
    }

    public User getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (null == authentication) {
            return getUnknownEntity();
        }

        Object principal = authentication.getPrincipal();
        if (null == principal || !(principal instanceof UserDetailsWrapper)) {
            return getUnknownEntity();
        }

        UserDetailsWrapper wrapper = (UserDetailsWrapper) principal;
        return wrapper.getUser();
    }
}
