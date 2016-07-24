package ru.skuptsov.telegram.bot.platform.config;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sergey Kuptsov
 * @since 30/05/2016
 */
@Configuration
@EnableMetrics(proxyTargetClass = true)
public class MetricsConfiguration extends MetricsConfigurerAdapter {

    @Override
    public MetricRegistry getMetricRegistry() {
        return new MetricRegistry();
    }

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        SharedMetricRegistries.add("default", metricRegistry);

        JmxReporter
                .forRegistry(metricRegistry)
                .build()
                .start();
    }
}
