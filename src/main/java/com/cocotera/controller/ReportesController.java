package com.cocotera.controller;

import com.cocotera.interfaces.IOrderRepository;
import com.cocotera.models.Order;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.io.OutputStream;
import java.util.HashMap;

@Controller
public class ReportesController {
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private IOrderRepository orderRepository;

    @GetMapping("/reportes")
    public void reportes(HttpServletResponse response, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);

        response.setHeader("Content-Disposition", "attachment; filename=\"reporte.pdf\";");
        response.setContentType("application/pdf");
        try {
            String ru = resourceLoader.getResource("classpath:static/lazo.jasper").getURI().getPath();
            JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, dataSource.getConnection());
            OutputStream outputStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("filtros")
    public void reporteConFiltro(HttpServletResponse response, @RequestParam String orderId) {
        response.setHeader("Content-Disposition", "inline");
        response.setContentType("application/pdf");

        try {
            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("codigo", orderId);
            String ru = resourceLoader.getResource("classpath:static/cocotera.jasper").getURI().getPath();
            JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parametros, dataSource.getConnection());
            OutputStream outputStream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
