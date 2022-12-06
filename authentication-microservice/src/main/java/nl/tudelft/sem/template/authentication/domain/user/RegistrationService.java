package nl.tudelft.sem.template.authentication.domain.user;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * A DDD service for registering a new user.
 */
@Service
public class RegistrationService {
    private final transient UserRepository userRepository;
    private final transient RoleRepository roleRepository;
    private final transient PasswordHashingService passwordHashingService;

    /**
     * Instantiates a new UserService.
     *  @param userRepository  the user repository
     * @param roleRepository
     * @param passwordHashingService the password encoder
     */
    public RegistrationService(UserRepository userRepository, RoleRepository roleRepository, PasswordHashingService passwordHashingService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordHashingService = passwordHashingService;
    }

    /**
     * Register a new user.
     *
     * @param netId    The NetID of the user
     * @param password The password of the user
     * @throws Exception if the user already exists
     */
    public AppUser registerUser(NetId netId, Password password, List<Role> list) throws Exception {

        if (checkNetIdIsUnique(netId)) {
            // Hash password
            HashedPassword hashedPassword = passwordHashingService.hash(password);

            // Create new account
            AppUser user = new AppUser(netId, hashedPassword,list);
            roleRepository.saveAll(list);
            userRepository.save(user);

            return user;
        }

        throw new NetIdAlreadyInUseException(netId);
    }

    public boolean checkNetIdIsUnique(NetId netId) {
        return !userRepository.existsByNetId(netId);
    }
}
