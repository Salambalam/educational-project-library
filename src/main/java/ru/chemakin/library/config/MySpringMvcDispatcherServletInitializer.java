package ru.chemakin.library.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.EnumSet;

public class MySpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /** возвращает конфигурационные классы, которые будут использоваться для настройки ApplicationContext
     в данном случае NULL, что значит, что настройка будет проводиться классом SpringConfig **/
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    /** возвращает конфигурационные классы, которые будут использоваться для настройки DispatcherServlet **/
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    /** определяет, какие URL будут обрабатываться диспетчером сервлетов Spring MVC.
     *  В данном случает будет обрабатывать все URL **/
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /** метод вызывается при запуске приложения **/
    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        registerCharacterEncodingFilter(aServletContext);
        registerHiddenFieldFilter(aServletContext);
    }

    /** метод, который регистрирует фильтр HiddenHttpMethodFilter,
     *  который позволяет использовать HTTP-методы PUT, PATCH и DELETE в браузерах,
     *  которые их не поддерживают. **/
    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
    }


    /** метод, который устанавливает кодироку UTF-8 **/
    private void registerCharacterEncodingFilter(ServletContext aContext) {
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        FilterRegistration.Dynamic characterEncoding = aContext.addFilter("characterEncoding", characterEncodingFilter);
        characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
    }
}
