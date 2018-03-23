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
 * A Information.
 * @author H199653
 */
@Entity
@Table(name = "INFORMACAO")
@Getter
@Setter
public class Information implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inf_id")
    private Long id;

    @NotNull
    @Size(min = 4, max = 30)
    @Column(name = "inf_nome", length = 30, nullable = false)
    private String name;

    @Column(name = "inf_descricao")
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
        Information information = (Information) o;
        if (information.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), information.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
