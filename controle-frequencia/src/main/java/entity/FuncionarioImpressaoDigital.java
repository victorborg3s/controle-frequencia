package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.machinezoo.sourceafis.FingerprintTemplate;

@Entity
@Table(name = "funcionario_impressao_digital")
public class FuncionarioImpressaoDigital {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "impressao_digital", length = Integer.MAX_VALUE)
	@Lob
	private byte[] fingerprint;

	public byte[] getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(byte[] fingerprint) {
		this.fingerprint = fingerprint;
	}

	public FingerprintTemplate getFingerprintTemplate() {
		return new FingerprintTemplate(fingerprint);
	}
	
}
