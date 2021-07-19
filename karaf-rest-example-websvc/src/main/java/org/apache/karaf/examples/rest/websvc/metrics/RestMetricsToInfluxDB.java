package org.apache.karaf.examples.rest.websvc.metrics;

import java.time.Duration;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.influx.InfluxConfig;
import io.micrometer.influx.InfluxConsistency;
import io.micrometer.influx.InfluxMeterRegistry;

public class RestMetricsToInfluxDB {
	
	private static final String INFLUX_DB_URL = "http://172.16.13.13:8086";
	
	private static final String INFLUX_DB_BUCKET = "micrometer";
	
	private static final String INFLUX_DB_USERNAME = "admin";
	
	private static final String INFLUX_DB_PASSWORD = "keybridge123";
	
	private static final String INFLUX_DB_TOKEN = "KFpU5duqCDsJ87_ot4pUtgVHnKfE4lJSPbjA07IUpJXn7TqzkMSDAtSDOESyrarTFrzj3fh-NujSnrlR8z_rMg==";
	
	private static final String INFLUX_DB_ORG = "Keybridge Wireless";
	
	private InfluxConfig influxDBConfig() {
		InfluxConfig influxConfig = new InfluxConfig() {
			
			@Override
			public String get(String key) {
				return null;
			}
			
			@Override
			public boolean autoCreateDb() {
				return false;
			}
			
			@Override
			public Duration step() {
				return Duration.ofSeconds(10);
			}
			
			@Override
			public int batchSize() {
				return 10000;
			}
			
			@Override
			public boolean compressed() {
				return true;
			}
			
			@Override
			public Duration connectTimeout() {
				return Duration.ofSeconds(1);
			}
			
			@Override
			public InfluxConsistency consistency() {
				return InfluxConsistency.ONE;
			}
			
			@Override
			public Duration readTimeout() {
				return Duration.ofSeconds(10);
			}
			
			@Override
			public String bucket() {
				return INFLUX_DB_BUCKET;
			}
			
			@Override
			public String org() {
				return INFLUX_DB_ORG;
			}
			
			@Override
			public String uri() {
				return INFLUX_DB_URL;
			}
			
			@Override
			public String userName() {
				return INFLUX_DB_USERNAME;
			}
			
			@Override
			public String password() {
				return INFLUX_DB_PASSWORD;
			}
			
			@Override
			public String token() {
				return INFLUX_DB_TOKEN;
			}
		};
		
		return influxConfig;
	}
	
	public void meterRegistry() {
		InfluxMeterRegistry metricRegistry = new InfluxMeterRegistry(this.influxDBConfig(), Clock.SYSTEM);
		new ClassLoaderMetrics().bindTo(metricRegistry);
		new JvmMemoryMetrics().bindTo(metricRegistry);
		new JvmGcMetrics().bindTo(metricRegistry);
		new ProcessorMetrics().bindTo(metricRegistry);
		new JvmThreadMetrics().bindTo(metricRegistry);
	}

}
