package com.nisum.user.api.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.nisum.user.api.dto.UserRegistrationRequestDTO;
import com.nisum.user.api.dto.UserResponseDTO;
import com.nisum.user.api.dto.UserUpdateRequest;
import com.nisum.user.api.exceptions.BadRequestException;
import com.nisum.user.api.exceptions.NotFoundException;
import com.nisum.user.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Usuarios", description = "Api de Usuarios")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("")
	@Operation(summary = "Alta de Usuario", description = "Recibe los datos de usuario y los registra ")
	public ResponseEntity<Object> saveUser(@RequestBody @Valid UserRegistrationRequestDTO userRegistrationRequestDTO)
			throws BadRequestException {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userRegistrationRequestDTO));
	}

	@Operation(summary = "Recuperacion de Usuarios", description = "Retorna la lista de todos los usuarios ")
	@GetMapping
	public List<UserResponseDTO> findAllUsers() {
		return userService.findAllUsers();
	}

	@Operation(summary = "Busqueda de Usuario", description = "Recibe como parametro el identificador de Usuario, lo busca y lo retorna si existe")
	@GetMapping("/{id}")
	public UserResponseDTO findUserById(@PathVariable("id") String id) throws NotFoundException {
		return userService.findUserById(id);
	}

	@Operation(summary = "Actualización de Usuario", description = "Recibe como parametro el identificador de Usuario, lo busca y lo actualiza si existe")
	@PatchMapping("/{id}")
	public UserResponseDTO updateUser(@PathVariable("id") String id, @Valid @RequestBody UserUpdateRequest request) throws NotFoundException {
		return userService.updateUser(id, request);
	}

	@Operation(summary = "Borrado de Usuario", description = "Recibe como parametro el identificador de Usuario, lo busca y lo borra si existe")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUserById(@PathVariable("id") String id) throws NotFoundException {
		userService.deleteUser(id);
	}

}
