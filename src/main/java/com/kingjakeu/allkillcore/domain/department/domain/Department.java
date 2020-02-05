package com.kingjakeu.allkillcore.domain.department.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Department {

    @Id
    private String id;

    @Column
    private String name;

    @Override
    public String toString(){
        return "\nID : "+id+"\n"+"NAME : "+name+"\n";
    }
}
