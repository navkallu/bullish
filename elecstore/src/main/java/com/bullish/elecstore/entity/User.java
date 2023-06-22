package com.bullish.elecstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name = "tbl_user")
@Entity
@Setter
@Getter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;
    private String name;

}
