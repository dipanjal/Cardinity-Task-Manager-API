package rest.api.cardinity.taskmanager;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import rest.api.cardinity.taskmanager.service.DummyService;

/**
 * @author dipanjal
 * @since 2/8/2021
 */
@Component
@RequiredArgsConstructor
public class StartupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final DummyService dummyService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        dummyService.createDummies();
    }
}
