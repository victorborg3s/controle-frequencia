package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "registro")
@NamedQueries({
		@NamedQuery(name = "RegistrosByFuncionarioAndPeriodo", query = "SELECT r FROM Registro r JOIN r.funcionario as f WHERE r.funcionario = :funcionario AND r.momento BETWEEN :inicioPeriodo and :fimPeriodo ORDER BY r.momento ") })
public class Registro extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 7309109087193111409L;
	@Transient private SimpleDateFormat fmtData, fmtHora = null;

	public Registro() {
		this.fmtData = new SimpleDateFormat("dd/MM/yyyy");
		this.fmtHora = new SimpleDateFormat("HH:mm:ss");
	}
	
	public static final String QUERY_BY_FUNCIONARIO_AND_PERIODO = "RegistrosByFuncionarioAndPeriodo";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;

	@Column(name = "tipo")
	private RegistroTipo tipo;

	@Column(name = "momento")
	private Date momento;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public RegistroTipo getTipo() {
		return tipo;
	}

	public void setTipo(RegistroTipo tipo) {
		this.tipo = tipo;
	}

	public Date getMomento() {
		return momento;
	}

	public void setMomento(Date momento) {
		this.momento = momento;
	}

	public String getMomentoFormatedDia() {
		if (momento == null) {
			return "";
		} else {
			if (fmtData == null) {
				fmtData = new SimpleDateFormat("dd/MM/yyyy");
			}
			return fmtData.format(momento);
		}
	}
	
	public String getMomentoFormatedHora() {
		if (momento == null) {
			return "";
		} else {
			if (fmtHora == null) {
				fmtHora = new SimpleDateFormat("HH:mm:ss");
			}
			return fmtHora.format(momento);
		}
	}
	
}
