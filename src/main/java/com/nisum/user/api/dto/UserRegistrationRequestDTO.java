package com.nisum.user.api.dto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import com.nisum.user.api.model.Phone;
import com.nisum.user.api.util.UserUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UserRegistrationRequestDTO {
    @NotBlank(message = "El nombre no puede ser vacío")
    private String name;

    @Email(regexp = UserUtil.EMAIL_REGEX, message = "El correo tiene formato incorrecto")
    @NotBlank(message = "El correo no puede estar vacío")
    private String email;

    @NotBlank(message = "La contraseña no puede ser vacío")
    @Pattern(regexp = UserUtil.PASSWORD_REGEX, message = "La contraseña tiene formato incorrecto")
    private String password;

    @NotEmpty(message = "Lista de teléfonos inválida")
    @Valid
    private List<PhoneRequestDTO> phones;
}
