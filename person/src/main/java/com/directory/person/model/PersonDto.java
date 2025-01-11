package com.directory.person.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PersonDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String company;
    private List<ContactInfoDto> contactInfoList;
}
