package com.nisum.user.api.dto;

import java.util.List;
import com.nisum.user.api.model.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegistrationResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private List<Phone> phones;
}
