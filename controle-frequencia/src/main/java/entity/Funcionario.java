package entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "funcionario")
@NamedQueries({
	@NamedQuery(name = "FuncionarioByDigital", query = "FROM Funcionario f WHERE f.digital = :digital") })
public class Funcionario extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 7309109087193111409L;
	
	public static final String QUERY_BY_DIGITAL = "FuncionarioByDigital";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "senha")
	private String senha;

	@Column(name = "ativo")
	private Boolean ativo;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FuncionarioImpressaoDigital digital;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	@Override
	public String toString() {
		return this.nome;
	}

	public FuncionarioImpressaoDigital getDigital() {
		return digital;
	}

	public void setDigital(FuncionarioImpressaoDigital digital) {
		this.digital = digital;
	}

}
