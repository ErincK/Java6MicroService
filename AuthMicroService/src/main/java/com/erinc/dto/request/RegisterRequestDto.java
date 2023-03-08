package com.erinc.dto.request;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    String username;
    String email;
    String password;
    String repassword;
}
