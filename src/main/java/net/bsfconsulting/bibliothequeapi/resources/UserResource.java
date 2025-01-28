package net.bsfconsulting.bibliothequeapi.resources;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.bsfconsulting.bibliothequeapi.dto.UserDto;
import net.bsfconsulting.bibliothequeapi.entity.User;
import net.bsfconsulting.bibliothequeapi.mapper.UserMapper;
import net.bsfconsulting.bibliothequeapi.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Ajouter un utilisateur", description = "Ajoute un nouvel utilisateur dans le système.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur ajouté avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PostMapping("/add")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        User user = UserMapper.INSTANCE.userDtoToUser(userDto);
        User savedUser = userService.saveUser(user);
        UserDto savedUserDto = UserMapper.INSTANCE.userToUserDto(savedUser);
        return ResponseEntity.ok(savedUserDto);
    }

    @Operation(summary = "Obtenir tous les utilisateurs", description = "Récupère une liste de tous les utilisateurs enregistrés dans le système.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des utilisateurs récupérée avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDto> userDtos = users.stream()
                .map(UserMapper.INSTANCE::userToUserDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }
}
