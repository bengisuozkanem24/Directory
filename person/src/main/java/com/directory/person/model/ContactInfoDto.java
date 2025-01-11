package com.directory.person.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ContactInfoDto {

    private UUID id;
    private ContactType contactType;
    private String content;

}
