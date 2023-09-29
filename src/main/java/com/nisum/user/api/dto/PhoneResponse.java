package com.nisum.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PhoneResponse {
    private Long id;
    private String number;
    private String cityCode;
    private String countryCode;
}
