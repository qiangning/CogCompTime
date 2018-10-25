package edu.illinois.cs.cogcomp.temporal.configurations;

import edu.illinois.cs.cogcomp.core.utilities.configuration.Configurator;
import edu.illinois.cs.cogcomp.core.utilities.configuration.Property;
import edu.illinois.cs.cogcomp.core.utilities.configuration.ResourceManager;

import java.io.IOException;

public class temporalConfigurator extends Configurator {
    public static Property EVENT_DETECTOR_WINDOW = new Property("EVENT_DETECTOR_WINDOW", "2");
    public static Property EVENT_TEMPREL_WINDOW = new Property("EVENT_TEMPREL_WINDOW", "3");
    public static Property EVENT_TIMEX_TEMPREL_WINDOW = new Property("EVENT_TIMEX_TEMPREL_WINDOW", "3");
    @Override
    public ResourceManager getDefaultConfig() {
        Property[] props = {EVENT_DETECTOR_WINDOW,EVENT_TEMPREL_WINDOW,EVENT_TIMEX_TEMPREL_WINDOW};
        return new ResourceManager(generateProperties(props));
    }

    public ResourceManager getConfig(String config_fname)  throws IOException {
        return super.getConfig(new ResourceManager(config_fname));
    }
}
