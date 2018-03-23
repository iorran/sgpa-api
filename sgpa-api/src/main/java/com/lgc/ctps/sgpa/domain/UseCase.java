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
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * A UseCase.
 * @author H199653
 */
@Entity
@Table(name = "CASO_USO")
@Getter
@Setter
public class UseCase implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usc_id")
    private Long id;

    @NotNull
    @Size(min = 4, max = 20)
    @Column(name = "usc_nome", length = 20, nullable = false)
    private String name;

    @Column(name = "usc_descricao")
    private String description;

    @ManyToOne
	@JoinColumn(name = "rol_id")
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UseCase useCase = (UseCase) o;
        if (useCase.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), useCase.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
