package com.directory.report.model;

import com.directory.person.model.ContactInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PersonServiceResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private String company;
    private List<ContactInfo> contactInfoList;
}
