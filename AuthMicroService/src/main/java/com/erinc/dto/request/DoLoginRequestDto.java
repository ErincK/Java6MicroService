package com.erinc.dto.request;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoLoginRequestDto {
    String username;
    String password;
}
