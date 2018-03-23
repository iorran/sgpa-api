package com.lgc.ctps.sgpa.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * A InformationValue.
 * @author H199653
 */
@Entity
@Table(name = "VALOR_INFORMACAO")
@Getter
@Setter
public class InformationValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inv_id")
    private Long id;

    @NotNull
    @Column(name = "inv_valor", nullable = false)
    private String value;

    @ManyToOne
	@JoinColumn(name = "inf_id")
    private Information information;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InformationValue informationValue = (InformationValue) o;
        if (informationValue.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), informationValue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
