package com.jefflks.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jefflks.common.entity.ServiceStatusInfo;
import com.jefflks.common.service.HealthCheckService;

@RestController
@RequestMapping({ "/common/health" })
public class HealthCheckController {

	@Value("${server.port}")
	String port;

	@Autowired
	HealthCheckService healthCheckService;

	@GetMapping({"/status"})
	public ResponseEntity<ServiceStatusInfo> checkServiceStatus() {
		return ResponseEntity.of(healthCheckService.checkServiceStatus());
	}
}
