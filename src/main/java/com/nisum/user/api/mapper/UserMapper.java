package com.nisum.user.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.nisum.user.api.constant.UserStatus;
import com.nisum.user.api.dto.UserRegistrationRequestDTO;
import com.nisum.user.api.dto.UserResponseDTO;
import com.nisum.user.api.model.User;
import com.nisum.user.api.service.TokenService;
import com.nisum.user.api.util.UserUtil;

@Component
public  class UserMapper {
	
	final ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private TokenService tokenService;
	
	public  User fromUserRegistrationRequestDTOToUser(UserRegistrationRequestDTO userRegistrationRequestDTO) {
		User user = modelMapper.map(userRegistrationRequestDTO, User.class);
		String idGenerated = UserUtil.generateId();
		user.setId(idGenerated);
		user.setIsActive(UserStatus.INACTIVE);
		user.setToken(tokenService.generateTokenByUserType(user.getEmail()));		
		return user;		
	}

	public  UserResponseDTO fromUserToUserResponseDTO(User user) {
		return  modelMapper.map(user, UserResponseDTO.class);		
	}
}
