package fr.xibalba.axiumwebsite.lang;

import com.vaadin.flow.i18n.I18NProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.Locale.*;
import static java.util.ResourceBundle.getBundle;

@Service
public class Lang implements I18NProvider {

    public static final String RESOURCE_BUNDLE_NAME = "lang";

    private static final ResourceBundle RESOURCE_BUNDLE_EN = getBundle(RESOURCE_BUNDLE_NAME , ENGLISH);

    private static final List<Locale> providedLocales = List.of(ENGLISH, FRENCH);

    @Override
    public List<Locale> getProvidedLocales() {

        return providedLocales;
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {

        ResourceBundle resourceBundle = RESOURCE_BUNDLE_EN;

        if (getProvidedLocales().contains(locale)) {
            resourceBundle = getBundle(RESOURCE_BUNDLE_NAME, locale);
        }

        if (!resourceBundle.containsKey(key)) {

            return key + " - " + locale;
        } else {

            return String.format(resourceBundle.getString(key), params);
        }
    }
}