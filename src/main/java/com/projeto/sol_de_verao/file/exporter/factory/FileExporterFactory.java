package com.projeto.sol_de_verao.file.exporter.factory;

import com.projeto.sol_de_verao.file.MediaTypes;
import com.projeto.sol_de_verao.file.exporter.contract.ProductExporter;
import com.projeto.sol_de_verao.file.exporter.impl.XlsxExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileExporterFactory {

    @Autowired
    private ApplicationContext applicationContext;

    public ProductExporter getExporter(String acceptHeader) {
        if (acceptHeader.equals(MediaTypes.APPLICATION_XLSX_VALUE)) {
            return applicationContext.getBean(XlsxExporter.class);
        }

        return null;
    }

}
