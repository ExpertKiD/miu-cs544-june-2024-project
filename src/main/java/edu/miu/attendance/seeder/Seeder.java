package edu.miu.attendance.seeder;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@Slf4j
public class Seeder {
    private final boolean seed;

    private final SeederHelper seederHelper;


    public Seeder(@Value("${spring.application.seed:false}") boolean seed,
                  SeederHelper seederHelper) {
        this.seed = seed;
        this.seederHelper = seederHelper;
    }

    @PostConstruct
    public void seedDatabase() {
        if (!seed) {
            return;
        }

        log.info("Seeder");
        log.info(seederHelper.toString());

        seederHelper.seedDatabase();
    }
}
