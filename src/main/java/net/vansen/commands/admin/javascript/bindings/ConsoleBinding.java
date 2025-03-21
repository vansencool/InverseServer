package net.vansen.commands.admin.javascript.bindings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("all")
public class ConsoleBinding {

    public static final ConsoleBinding INSTANCE = new ConsoleBinding();
    private final Logger logger = LoggerFactory.getLogger(ConsoleBinding.class);

    public void log(Object message) {
        logger.info("By Javascript: {}", message.toString());
    }

    public void info(Object message) {
        logger.info("By Javascript: {}", message.toString());
    }

    public void warn(Object message) {
        logger.warn("By Javascript: {}", message.toString());
    }

    public void error(Object message) {
        logger.error("By Javascript: {}", message.toString());
    }

    public void debug(Object message) {
        logger.debug("By Javascript: {}", message.toString());
    }

    public void trace(Object message) {
        logger.trace("By Javascript: {}", message.toString());
    }
}
