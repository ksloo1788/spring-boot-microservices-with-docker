package com.jefflks.common.service;

import java.util.Optional;

import com.jefflks.common.entity.ServiceStatusInfo;


public interface HealthCheckService {
	Optional<ServiceStatusInfo> checkServiceStatus();
}