package com.lgc.ctps.sgpa.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.lgc.ctps.sgpa.domain.enumeration.Access;

import lombok.Getter;
import lombok.Setter;

/**
 * A Permission.
 * @author H199653
 */
@Entity
@Table(name = "PERMISSAO")
@Getter
@Setter
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "per_id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "per_valor", nullable = false)
    private Access value;

    @ManyToOne
	@JoinColumn(name = "usc_id")
    private UseCase useCase;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Permission permission = (Permission) o;
        if (permission.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), permission.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
