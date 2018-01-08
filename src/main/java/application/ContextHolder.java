package application;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public final class ContextHolder implements ApplicationContextAware
{
    private static ApplicationContext context;

    public static <T> T getBean(Class<T> requiredType)
    {
        checkContext();
        return context.getBean(requiredType);
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