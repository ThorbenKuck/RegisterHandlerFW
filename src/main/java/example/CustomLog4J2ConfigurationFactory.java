package example;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.util.ReflectionUtil;

import java.io.Serializable;
import java.net.URI;

@Plugin (name = "CustomConfigurationFactory", category = ConfigurationFactory.CATEGORY)
@Order (1)
public class CustomLog4J2ConfigurationFactory extends ConfigurationFactory implements Serializable {
	private Level level;
	private String filePrefix;

	public CustomLog4J2ConfigurationFactory(Level level) {
		this.level = level;
		this.filePrefix = ReflectionUtil.getCallerClass(2).getSimpleName();
	}

	public CustomLog4J2ConfigurationFactory() {
		this(Level.TRACE);
	}

	private Configuration createConfiguration(final String name, ConfigurationBuilder<BuiltConfiguration> builder, final String filePrefix) {
		builder.setConfigurationName(name);
		builder.setStatusLevel(level);

		//Kann eigentlich egal sein.
		//Wenn das level DEBUG ist, wird die Ausgabe definitiv getätigt, egal was die anderen Filter machen.
//		builder.add(builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT, Filter.Result.NEUTRAL).
//				addAttribute("level", Level.DEBUG));

		LayoutComponentBuilder layoutComponentBuilder = builder.newLayout("PatternLayout").
//				addAttribute("pattern", "%d [%t] %C{1} %-5level: %msg%n%throwable");
		addAttribute("pattern", "%d [%t] %C{1} %level : %msg%n%throwable");

		AppenderComponentBuilder appenderBuilder = builder.newAppender("Stdout", "CONSOLE").
				addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
		appenderBuilder.add(layoutComponentBuilder);

		//Keine Ahnung was das macht :D
//		appenderBuilder.add(builder.newFilter("MarkerFilter", Filter.Result.DENY,
//				Filter.Result.NEUTRAL).addAttribute("marker", "FLOW"));

		builder.add(appenderBuilder);

		ComponentBuilder triggeringPolicy = builder.newComponent("Policies")
				.addComponent(builder.newComponent("CronTriggeringPolicy").addAttribute("schedule", "0 0 0 * * ?"))
				.addComponent(builder.newComponent("SizeBasedTriggeringPolicy").addAttribute("size", "100M"));

//		AppenderComponentBuilder fileAppenderBuilder = builder.newAppender("rolling", "RollingFile")
//				.addAttribute("fileName", "target/rolling.html")
//				.addAttribute("filePattern", "target/archive/rolling-%d{yy-MM-dd}.html.gz")
//				.add(
//						builder.newLayout("HTMLLayout")
//								.addAttribute("charset", "UTF-8")
//								.addAttribute("title", "Logs")
//								.addAttribute("locationInfo", true)
//				)
//				.addComponent(triggeringPolicy);

		AppenderComponentBuilder fileAppenderBuilder = builder.newAppender("rolling", "RollingFile")
				.addAttribute("fileName", "target/" + filePrefix + "_rolling.log")
				.addAttribute("filePattern", "target/archive/rolling-%d{yy-MM-dd}.log.gz")
				.add(layoutComponentBuilder)
				.addComponent(triggeringPolicy);

		builder.add(fileAppenderBuilder);

		builder.add(builder.newRootLogger(level).add(builder.newAppenderRef("Stdout")).add(builder.newAppenderRef("rolling")));

		return builder.build();
	}

	@Override
	protected String[] getSupportedTypes() {
//		return new String[0];
		return new String[]{"*"};
	}

	@Override
	public String toString() {
		return "CustomLog4J2ConfigurationFactory{" +
				"level=" + level +
				", filePrefix='" + filePrefix + '\'' +
				'}';
	}

	@Override
	public Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource source) {
		return getConfiguration(loggerContext, source.toString(), null);


	}

	/**
	 * Returns the Configuration.
	 *
	 * @param loggerContext  The logger context
	 * @param name           The configuration name.
	 * @param configLocation The configuration location.
	 *
	 * @return The Configuration.
	 */
	@Override
	public Configuration getConfiguration(LoggerContext loggerContext, String name, URI configLocation) {
		ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
		return createConfiguration(name, builder, filePrefix);
	}
}
