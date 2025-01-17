package net.bsfconsulting.bibliothequeapi.resources;

import net.bsfconsulting.bibliothequeapi.entity.User;
import net.bsfconsulting.bibliothequeapi.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/add")
    public User addUser(@RequestBody User user){
        return userService.saveUser(user);
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
