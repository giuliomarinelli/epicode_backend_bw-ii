package it.epicode.backend.bwii.epic_energy_services;

import it.epicode.backend.bwii.epic_energy_services.Models.entities.Provincia;
import it.epicode.backend.bwii.epic_energy_services.repositories.ProvinciaRepository;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;


@Component
@Order(1)
public class ProvinceConfig implements CommandLineRunner {
    public static final Logger logger = LoggerFactory.getLogger(ProvinceConfig.class);

    @Autowired
    private ProvinciaRepository provinciaRp;

    @Override
    public void run(String... args) throws Exception {
        File file = new File("src/main/resources/datasets/province-italiane.csv");
        try {
            String content = FileUtils.readFileToString(file, Charset.defaultCharset());
            String[] rows = content.split("\n");
            for (int i = 1; i < rows.length; i++) {
                String[] row = rows[i].split(";");
                if (row[0].equals("Roma")) row[0] = "RM";
                Provincia provincia = new Provincia(row[0], row[1], row[2]);
                provinciaRp.save(provincia);
                logger.info("Salvata provincia => " + provincia.getSigla());
            }
        } catch (IOException e) {
            logger.error("Errore => " + e.getMessage());
        }
    }
}
