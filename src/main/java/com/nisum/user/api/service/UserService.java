package com.nisum.user.api.service;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nisum.user.api.dto.UserRegistrationRequestDTO;
import com.nisum.user.api.dto.UserResponseDTO;
import com.nisum.user.api.dto.UserUpdateRequest;
import com.nisum.user.api.exceptions.BadRequestException;
import com.nisum.user.api.exceptions.NotFoundException;
import com.nisum.user.api.mapper.UserMapper;
import com.nisum.user.api.model.User;
import com.nisum.user.api.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	public UserResponseDTO saveUser(UserRegistrationRequestDTO userRegistrationRequestDTO) throws BadRequestException {
		log.info("Guardando User");
		Optional<User> userOptional = this.userRepository.findByEmail(userRegistrationRequestDTO.getEmail());
		if (userOptional.isPresent()) {
			throw new BadRequestException(
					"Ya existe un usuario registrado con el correo: " + userRegistrationRequestDTO.getEmail());
		}
		User user = userMapper.fromUserRegistrationRequestDTOToUser(userRegistrationRequestDTO);
		log.info("Registering user: {}", user);
		return userMapper.fromUserToUserResponseDTO(userRepository.save(user));
	}

	@Transactional(readOnly = true)
	public List<UserResponseDTO> findAllUsers() {
		return userRepository.findAll().stream().map(userMapper::fromUserToUserResponseDTO).toList();
	}

	public UserResponseDTO findUserById(String id) throws NotFoundException {
		return this.userMapper.fromUserToUserResponseDTO(
				userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado")));
	}

	public UserResponseDTO updateUser(String id, @Valid UserUpdateRequest request) throws NotFoundException {
		User userDB = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
		userDB.populateUserEntityByUpdate(request);
		log.info("Updating user. ID: {}, Request: {}, UserDB: {}", id, request, userDB);
		return userMapper.fromUserToUserResponseDTO(userRepository.save(userDB));
	}

	public void deleteUser(String id) throws NotFoundException {
		User userDB = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
		log.info("Deleting user. ID: {}, UserDB: {}", id, userDB);
		userRepository.delete(userDB);
	}
}
