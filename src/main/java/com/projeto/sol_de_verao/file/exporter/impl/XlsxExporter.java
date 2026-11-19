package com.projeto.sol_de_verao.file.exporter.impl;

import com.projeto.sol_de_verao.dto.ProductDTO;
import com.projeto.sol_de_verao.file.exporter.contract.ProductExporter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class XlsxExporter implements ProductExporter {

    @Override
    public Resource exportProduct(List<ProductDTO> products) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Products");

            Row headerRow = sheet.createRow(0);
            String[] header = {"ID","Name","Category","Unit Price","Quantity","Inventory","Enabled"};
            for (int i = 0; i < header.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(header[i]);
                cell.setCellStyle(createHeaderCellStyle(workbook));
            }

            int rowIndex = 1;
            for (ProductDTO product : products) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(product.getId());
                row.createCell(1).setCellValue(product.getName());
                row.createCell(2).setCellValue(product.getCategory().getDescription());
                row.createCell(3).setCellValue((RichTextString) product.getUnitPrice());
                row.createCell(4).setCellValue(product.getQuantity());
                row.createCell(5).setCellValue(product.getInventory().getDescription());
                row.createCell(6).setCellValue(product.getEnabled() != null && product.getEnabled() == true ? "Yes" : "No");
            }

            for (int i = 0; i < header.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            return new ByteArrayResource(outputStream.toByteArray());
        }
    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);

        return style;
    }
}
