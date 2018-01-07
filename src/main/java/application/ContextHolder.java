package application;

import application.entity.Config;
import application.repository.ConfigRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public final class ContextHolder implements ApplicationContextAware
{
    private static ApplicationContext context;
    private static Config config;

    public static <T> T getBean(Class<T> requiredType)
    {
        checkContext();
        return context.getBean(requiredType);
    }

    public static Config getConfig()
    {
        if (config == null)
        {
            ConfigRepository configRepository = getBean(ConfigRepository.class);
            Pageable pageable = new PageRequest(0, 1);
            Page<Config> all = configRepository.findAll(pageable);
            if (all.getTotalElements() != 0)
            {
                config = all.getContent().get(0);
            }
        }
        return config;
    }

    private static void checkContext()
    {
        Objects.requireNonNull(context, "ApplicationContext is null");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        context = applicationContext;
        checkContext();
    }
}