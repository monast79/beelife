package ru.crimea.beelife.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "hive_weights")
@NamedQuery(name = "BeehiveWeight.getBeehiveDetailsFromDate", query = "FROM BeehiveWeight bw WHERE bw.beehive.id  = :beehiveId and bw.measure > :fromDate")
public class BeehiveWeight extends BasePersistableObject{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weight_seq")
    @SequenceGenerator(name = "weight_seq", sequenceName = "hive_weight_seq_id", allocationSize = 1)
    @Column(updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hive_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Beehive beehive;

    private Timestamp measure;

    @Column(
            columnDefinition = "NUMERIC(5,2)")
    private Double weight;
}
