package ru.crimea.beelife.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "beehives")
public class Beehive {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "beehive_seq")
    @SequenceGenerator(name = "beehive_seq", sequenceName = "beehive_seq_id", allocationSize = 1)
    @Column(updatable = false)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "apiary_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Apiary apiary;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "type")
    private HiveType hiveType;

    private Integer frame;
}
