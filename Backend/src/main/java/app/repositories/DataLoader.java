package app.repositories;

import app.models.Country;
import app.models.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.data.util.ProxyUtils.getUserClass;

/**
 * Loads dummy data into the mem databse.
 */
@Profile("test")
@Component
public class DataLoader implements CommandLineRunner
{
    @Override
    @Transactional
    public void run(String... args)  {
        System.out.println("Running CommandLine Startup");
        this.createCountries();
        this.createInitialUsers(10);

        System.out.println("Injected accounts from " +(this.userRepository != null ? getUserClass(this.userRepository.getClass()).getName() : "none"));
    }


    @Autowired
    private CountryRepository countryRepository;
    private void createCountries(){
        this.countryRepository.save(new Country(1,"Netherlands", "NL","NLD","https://sumting.s3.eu-west-2.amazonaws.com/flags/nl.png"));
        this.countryRepository.save(new Country(2,"Iceland", "IS","ISL","https://sumting.s3.eu-west-2.amazonaws.com/flags/is.png"));
        this.countryRepository.save(new Country(3,"Hong Kong", "HK","HKG","https://sumting.s3.eu-west-2.amazonaws.com/flags/hk.png"));

    }
    @Autowired
    private JPAUserRepository userRepository;

    private void createInitialUsers(int amount) {
        // check whether the repo is empty
        List<User> userList = (List<User>) this.userRepository.findAll();
        if (userList.size() > 0) return;
        System.out.println("Configuring some initial accounts in the repository");
        User admin=    User.buildRandom("admin");
        userList.add(this.userRepository.save(admin));
        for (int i = 0; i < amount; i++) {
            User u = null;
                u = User.buildRandom();
            userList.add(this.userRepository.save(u));
        }
        for (User a : userList) {
            a.setEncodedPassword(a.hashPassword("Test123!"));
            System.out.println("Added user: " + a + " (initial password = 'Test123!')" + (a.validateEncodedPassword("Test123!") ? " (password is valid)" : " (password is invalid)"));
        }
    }




}
