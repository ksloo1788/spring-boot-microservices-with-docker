package com.jefflks.common.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jefflks.common.entity.ServiceStatusInfo;

@Service
public final class HealthCheckServiceImpl implements HealthCheckService{
	
	Logger logger =  LoggerFactory.getLogger(HealthCheckServiceImpl.class);
	
	@Value("${server.port}")
	String port;
	
	@Value("${spring.application.name}")
	String serviceName;
	
	@Override
	public Optional<ServiceStatusInfo> checkServiceStatus() {
		
		String datetime;
		
		String hostname;

		String ipAddress;

		final String status = "UP";

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		datetime = sdf1.format(timestamp);

		try {
			hostname = InetAddress.getLocalHost().getHostName();
			ipAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			hostname = "Unknown hostname";
			ipAddress = "Unknown IP Address";
		}
		
		
		return Optional.ofNullable(new ServiceStatusInfo(datetime, serviceName, hostname, ipAddress, port, status));

	}
	
}
