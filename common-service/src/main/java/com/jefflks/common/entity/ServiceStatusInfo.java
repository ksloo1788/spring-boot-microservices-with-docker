package com.jefflks.common.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author JeffLKS
 *
 */
public class ServiceStatusInfo {
	@JsonProperty("DateTime")
	private String dateTime;
	
	@JsonProperty("ServiceName")
	private String serviceName;
	
	@JsonProperty("HostName")
	private String hostName;

	@JsonProperty("IP_Address")
	private String ipAddress;

	@JsonProperty("Port")
	private String port;

	@JsonProperty("Status")
	private String status;

	/**
	 * Default Constructor
	 */
	public ServiceStatusInfo() {
	}

	/**
	 * @param dateTime
	 * @param serviceName
	 * @param hostName
	 * @param ipAddress
	 * @param port
	 * @param status
	 */
	public ServiceStatusInfo(String dateTime, String serviceName, String hostName, String ipAddress, String port,
			String status) {
		super();
		this.dateTime = dateTime;
		this.serviceName = serviceName;
		this.hostName = hostName;
		this.ipAddress = ipAddress;
		this.port = port;
		this.status = status;
	}

	/**
	 * @return the dateTime
	 */
	public String getdateTime() {
		return dateTime;
	}

	/**
	 * @param dateTime the dateTime to set
	 */
	public void setdateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the hostName
	 */
	public String gethostName() {
		return hostName;
	}

	/**
	 * @param hostName the hostName to set
	 */
	public void sethostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ServiceStatusInfo [dateTime=" + dateTime + ", serviceName=" + serviceName + ", hostName=" + hostName
				+ ", ipAddress=" + ipAddress + ", port=" + port + ", status=" + status + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(dateTime, hostName, ipAddress, port, serviceName, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ServiceStatusInfo)) {
			return false;
		}
		ServiceStatusInfo other = (ServiceStatusInfo) obj;
		return Objects.equals(dateTime, other.dateTime) && Objects.equals(hostName, other.hostName)
				&& Objects.equals(ipAddress, other.ipAddress) && Objects.equals(port, other.port)
				&& Objects.equals(serviceName, other.serviceName) && Objects.equals(status, other.status);
	}

}
