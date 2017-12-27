package report;

import java.util.List;

import entity.Registro;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class FrequenciaReportFactory {
	
	private String path;
	private String pathToReportPackage;
	
	public FrequenciaReportFactory() {
		this.path = this.getClass().getClassLoader().getResource("").getPath();
		this.pathToReportPackage = this.path + "report/";
		System.out.println(path);
	}
	
	public void imprimir(List<Registro> registros) throws Exception	
	{
		JasperReport report = JasperCompileManager.compileReport(this.pathToReportPackage + "registros_por_funcionario_periodo.jrxml");
		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(registros));
		JasperExportManager.exportReportToPdf(print);
	}
	
	
	
}
