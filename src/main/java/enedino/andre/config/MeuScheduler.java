package enedino.andre.config;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class MeuScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(MeuScheduler.class);

//    @ConfigProperty(name = "viacep-api")
//    String url;

//    @Scheduled(every = "60s")
//    void executeTask() {
//        LOG.info("Tarefa agendada executada!");
//    }
}
