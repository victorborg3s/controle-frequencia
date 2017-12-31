package entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistroDia {

	private List<Registro> registros;
	private Double saldoHoras;
	private Date dataReferencia;

	public RegistroDia() {
		registros = new ArrayList<Registro>();
		saldoHoras = new Double("0");
	}

	private void atualizarSaldoHoras(Registro entrada, Registro saida) {
		if ((saida.getMomento() != null) && (entrada.getMomento() != null)) {
			saldoHoras = Double.sum(saldoHoras, new Double(saida.getMomento().getTime() - entrada.getMomento().getTime()) / (1000 * 60 * 60));
		}
	}

	public List<Registro> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}

	public double getSaldoHoras() {
		return saldoHoras;
	}

	public void setSaldoHoras(Double saldoHoras) {
		this.saldoHoras = saldoHoras;
	}

	public void addRegistro(Registro reg) {
		Registro auxReg = null;

		if (!registros.isEmpty() && getLastInsertedRegistro().getTipo().equals(RegistroTipo.ENTRADA)
				&& reg.getTipo().equals(RegistroTipo.SAIDA)) {
			atualizarSaldoHoras(registros.get(registros.size() - 1), reg);
		}

		if ((registros.isEmpty() && reg.getTipo().equals(RegistroTipo.SAIDA))
				|| (!registros.isEmpty() && getLastInsertedRegistro().getTipo().equals(reg.getTipo()))) {
			
			auxReg = new Registro();
			auxReg.setFuncionario(reg.getFuncionario());
			auxReg.setMomento(null);
			
			if (reg.getTipo().equals(RegistroTipo.ENTRADA)) {
				auxReg.setTipo(RegistroTipo.SAIDA);
			} else {
				auxReg.setTipo(RegistroTipo.ENTRADA);
			}
			
			registros.add(auxReg);
		}

		registros.add(reg);
	}

	public Registro getLastInsertedRegistro() {
		if (registros.size() == 0) {
			return null;
		} else {
			return registros.get(registros.size() - 1);
		}
	}
	
	public Registro getRegistro(Integer index) {
		return registros.get(index);
	}
	
	public Registro getFirstRegistro() {
		return getRegistro(0);
	}
	
	public Registro getSecondRegistro() {
		return getRegistro(1);
	}
	
	public Registro getThirdRegistro() {
		return getRegistro(2);
	}
	
	public Registro getFourthRegistro() {
		return getRegistro(3);
	}

	public Date getDataReferencia() {
		return dataReferencia;
	}

	public void setDataReferencia(Date dataReferencia) {
		this.dataReferencia = dataReferencia;
	}

	public String getFormatedDataReferencia() {
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		return fmt.format(dataReferencia);
	}
	
}
