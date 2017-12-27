package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parametro")
public class Parametro extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 7309109087193111409L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "chave")
	private ParametroChave chave;

	@Column(name = "valor")
	private String valor;

	@Override
	public Integer getId() {
		return id;
	}

	public ParametroChave getChave() {
		return chave;
	}

	public void setChave(ParametroChave chave) {
		this.chave = chave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
