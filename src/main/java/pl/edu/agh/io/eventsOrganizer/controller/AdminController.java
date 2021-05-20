package pl.edu.agh.io.eventsOrganizer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.io.eventsOrganizer.errors.SqlException;
import pl.edu.agh.io.eventsOrganizer.model.User;
import pl.edu.agh.io.eventsOrganizer.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/admin/login")
    public ResponseEntity<Boolean> verifyAdmin(@RequestBody User user, HttpServletRequest request) {
        User foundUser = userRepository.findUserByUsername(user.getUsername());
        if (foundUser == null) {
            logger.error("User with given username does not exist.");
        } else if (!foundUser.getPassword().equals(user.getPassword())) {
            logger.error("Incorrect password");
        }
        return new ResponseEntity<>(
                foundUser != null
                        && foundUser.getAdmin() != null
                        && foundUser.getAdmin()
                        && foundUser.getPassword() != null
                        && foundUser.getPassword().equals(user.getPassword()),
                HttpStatus.OK);
    }

    @PostMapping("/admin/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user, HttpServletRequest request) throws SqlException {
        try {
            User newUser = new User(user.getUsername(), user.getPassword(), user.getAdmin());
            return new ResponseEntity<>(userRepository.save(newUser), HttpStatus.OK);
        } catch (Exception e) {
            throw new SqlException(
                    "Error during adding new user",
                    request.getRequestURI(),
                    List.of("User already exist")
            );
        }
    }
}
