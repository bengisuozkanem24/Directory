package com.directory.person.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactInfoRequest {

    private ContactType contactType;
    private String content;
}
