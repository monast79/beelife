package ru.crimea.beelife.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "apiaries")
public class Apiary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "apiary_seq")
    @SequenceGenerator(name = "apiary_seq", sequenceName = "apiary_seq_id", allocationSize = 1)
    @Column(updatable = false)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private ApiaryType type;

    @OneToMany(mappedBy = "apiary")
    private Set<Beehive> beehives;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
