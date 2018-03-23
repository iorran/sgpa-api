package com.lgc.ctps.sgpa.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * A RolePerson.
 *
 * @author H199653
 */
@Entity
@Table(name = "PESSOA_PAPEL")
@Getter
@Setter
public class PersonRole implements Serializable {

	private static final long serialVersionUID = 3001834011705630088L;

	@EmbeddedId
	private PK id;

	@Column(name = "PEP_CHAVE", unique = true)
	private String login;

	@Embeddable
	@Getter
	@Setter
	public static class PK implements Serializable {

		private static final long serialVersionUID = 816705623263533406L;

		@ManyToOne
		@JoinColumn(name = "app_id")
		private Application application;

		@ManyToOne
		@JoinColumn(name = "rol_id")
		private Role role;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((application == null) ? 0 : application.hashCode());
			result = prime * result + ((role == null) ? 0 : role.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PK other = (PK) obj;
			if (application == null) {
				if (other.application != null)
					return false;
			} else if (!application.equals(other.application))
				return false;
			if (role == null) {
				if (other.role != null)
					return false;
			} else if (!role.equals(other.role))
				return false;
			return true;
		}
	}
}
