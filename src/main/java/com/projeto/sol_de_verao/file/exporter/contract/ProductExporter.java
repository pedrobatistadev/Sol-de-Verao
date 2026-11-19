package com.projeto.sol_de_verao.file.exporter.contract;

import com.projeto.sol_de_verao.dto.ProductDTO;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface ProductExporter {

    Resource exportProduct(List<ProductDTO> products) throws IOException;
}
