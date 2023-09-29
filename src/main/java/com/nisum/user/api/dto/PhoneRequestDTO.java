package com.nisum.user.api.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PhoneRequestDTO {

    @NotBlank(message = "El número es inválido")
    private String number;

    @NotBlank(message = "El código de ciudad es inválido")
    private String cityCode;

    @NotBlank(message = "El código de país es inválido")
    private String countryCode;

}
