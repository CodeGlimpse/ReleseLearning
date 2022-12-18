package com.example.releaselearning.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_class")
public class Class {

    @Id
    @Column(name = "class_id")
    private String classId;

    @NotNull(message = "所属老师为空")
    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.MERGE)
    @JoinColumn(name = "teacher_id")
    private Teacher teacherId;

}
