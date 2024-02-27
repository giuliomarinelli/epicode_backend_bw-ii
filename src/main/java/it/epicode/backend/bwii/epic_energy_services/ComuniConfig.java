package it.epicode.backend.bwii.epic_energy_services;

import it.epicode.backend.bwii.epic_energy_services.Models.entities.Comune;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Provincia;
import it.epicode.backend.bwii.epic_energy_services.repositories.ComuneRepository;
import it.epicode.backend.bwii.epic_energy_services.repositories.ProvinciaRepository;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;

//@Component
//@Order(2)
public class ComuniConfig implements CommandLineRunner {

    public static final Logger logger = LoggerFactory.getLogger(ComuniConfig.class);

    @Autowired
    private ComuneRepository comuneRp;

    @Autowired
    private ProvinciaRepository provinciaRp;


    @Override
    public void run(String... args) throws Exception {
        File file = new File("src/main/resources/datasets/comuni-italiani.csv");
        try {
            String content = FileUtils.readFileToString(file, StandardCharsets.ISO_8859_1);
            String[] rows = content.split("\n");
            for (int i = 1; i < rows.length; i++) {
                String[] row = rows[i].split(";");
                String nome = row[5].trim();
                String siglaProvincia = row[13].trim();
                Provincia p = provinciaRp.findById(siglaProvincia).orElseThrow(() -> new Exception("Provincia non trovata"));
                Comune comune = new Comune(nome, p);
                comuneRp.save(comune);
                logger.info("Salvato nuovo comune => " + nome);
            }
        } catch (Exception e) {
            logger.error("Errore => " + e.getMessage());
        }
    }
}
