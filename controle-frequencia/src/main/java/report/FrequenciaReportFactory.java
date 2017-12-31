package report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.RegistroService;
import entity.Funcionario;
import entity.Registro;
import entity.RegistroDia;
import entity.RegistroTipo;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class FrequenciaReportFactory {

	private String path;
	private String pathToReportPackage;

	public FrequenciaReportFactory() {
		this.path = this.getClass().getClassLoader().getResource("").getPath();
		this.pathToReportPackage = this.path + "report/";
		System.out.println(path);
	}

	public void imprimir(Funcionario funcionario, Date inicio, Date fim) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		
		params = new HashMap<String, Object>();
		params.put("P_FUNCIONARIO", funcionario.getNome());
		params.put("P_PERIODO_INICIO", fmt.format(inicio));
		params.put("P_PERIODO_FIM", fmt.format(fim));
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(fim);
		cal.set(Calendar.AM_PM, cal.getMaximum(Calendar.AM_PM));
		cal.set(Calendar.HOUR, cal.getMaximum(Calendar.HOUR));
		cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
		
		List<RegistroDia> dias = processarParaRelatorio(funcionario, inicio, cal.getTime());

		JasperReport report = JasperCompileManager
				.compileReport(this.pathToReportPackage + "registros_por_funcionario_periodo.jrxml");
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dias);

		JasperPrint print = JasperFillManager.fillReport(report, params, dataSource);
		
		JasperViewer.viewReport(print, false);
	}

	private List<RegistroDia> processarParaRelatorio(Funcionario funcionario, Date inicio, Date fim) {
		List<RegistroDia> dias = null;
		List<Registro> registros = null;
		Integer count = 0;

		registros = RegistroService.getRegistrosByFuncionarioAndPeriodo(
				funcionario, inicio, fim
			);
		
		dias = prepareRegistroDiasList(inicio, fim);
		
		
		for (RegistroDia dia : dias) {
			while (count < registros.size() 
					&& registros.get(count).getMomentoFormatedDia().equals(dia.getFormatedDataReferencia())) {
				
				if (dia.getRegistros().size() < 4) {
					dia.addRegistro(registros.get(count));
				}
				
				count++;
				
			}
			
			if (dia.getRegistros().size() < 4) {
				completarQuantidadeRegistros(dia, funcionario);
			}
				
		}

		return dias;
	}

	private List<RegistroDia> prepareRegistroDiasList(Date inicio, Date fim) {
		ArrayList<RegistroDia> dias = new ArrayList<RegistroDia>();
		RegistroDia auxDia = null;
		
		Calendar cal = Calendar.getInstance();
		Calendar calFim = Calendar.getInstance();
		
		cal.setTime(inicio);
		calFim.setTime(fim);
		
		while (cal.before(calFim)) {
			auxDia = new RegistroDia();
			auxDia.setDataReferencia(cal.getTime());
			dias.add(auxDia);
			cal.add(Calendar.DATE, 1);
		}
		
		return dias;
	}

	private void completarQuantidadeRegistros(RegistroDia currentDia, Funcionario funcionario) {
		Registro auxReg = null;

		while (currentDia.getRegistros().size() < 4) {
			auxReg = new Registro();
			auxReg.setFuncionario(funcionario);
			auxReg.setMomento(null);

			if ((currentDia.getLastInsertedRegistro() == null) || currentDia.getLastInsertedRegistro().getTipo().equals(RegistroTipo.SAIDA)) {
				auxReg.setTipo(RegistroTipo.ENTRADA);
			} else {
				auxReg.setTipo(RegistroTipo.SAIDA);
			}

			currentDia.addRegistro(auxReg);
		}
	}

}
