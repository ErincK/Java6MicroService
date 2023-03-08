package com.erinc.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;


@Data
@SuperBuilder // BaseEntity miras alındığı için bunu kullanıyoruz.
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {
    Long createat;
    Long updateat;
    boolean state;
}
