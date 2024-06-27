package com.sl.foodorderingsystem.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDto {
    private Integer id;
    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String status;
}
