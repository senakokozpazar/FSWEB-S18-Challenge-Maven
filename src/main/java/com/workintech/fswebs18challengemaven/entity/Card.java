package com.workintech.fswebs18challengemaven.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="card", schema="fsweb")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="value")
    private Integer value;

    @Column(name="type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name="color")
    @Enumerated(EnumType.STRING)
    private Color color;
}
