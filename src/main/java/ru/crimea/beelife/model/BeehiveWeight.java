package ru.crimea.beelife.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "hive_weights")
public class BeehiveWeight {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weight_seq")
    @SequenceGenerator(name = "weight_seq", sequenceName = "hive_weight_seq_id", allocationSize = 1)
    @Column(updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hive_id")
    private Beehive beehive;

    private Date measure;

    @Column(
            columnDefinition = "NUMERIC(5,2)")
    private Double weight;
}
