package it.unibo.mvc;

import java.lang.reflect.InvocationTargetException;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;

/**
 * Application entry-point.
 */
public final class LaunchApp {
    private static final String DNSO = "it.unibo.mvc.view.DrawNumberStandardOutputView";
    private static final String DNSW = "it.unibo.mvc.view.DrawNumberSwingView";
    private static final int NUMBER_OF_VIEWS = 6;
    private static final int NUMBER_OF_VIEWS_DIVIDED = 3;

    private LaunchApp() { }

    /**
     * Runs the application.
     *
     * @param args ignored
     * @throws ClassNotFoundException if the fetches class does not exist
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalAccessException in case of reflection issues
     * @throws IllegalArgumentException in case of reflection issues
     */
    public static void main(final String... args) throws ClassNotFoundException,
        NoSuchMethodException,
        InvocationTargetException,
        InstantiationException,
        IllegalAccessException {
        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);
        final String[] viewClasses = new String[NUMBER_OF_VIEWS];
        for (int i = 0; i < NUMBER_OF_VIEWS_DIVIDED; i++) {
            viewClasses[i] = DNSO;
            viewClasses[i + 1] = DNSW;
        }
        for (final String s : viewClasses) {
            final Class<?> clazz = Class.forName(s);
            final var constructor = clazz.getDeclaredConstructor();
            final DrawNumberView view = (DrawNumberView) constructor.newInstance();
            app.addView(view);
        }
    }
}
