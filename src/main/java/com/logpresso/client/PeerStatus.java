package com.logpresso.client;

import java.util.Map;

public class PeerStatus {
	private String peerGuid;
	private Boolean isPaired;

	public PeerStatus(Object respMap) {
		@SuppressWarnings("unchecked")
		Map<String, Object> resp = (Map<String, Object>) respMap;
		this.peerGuid = (String) resp.get("peer_guid");
		this.isPaired = (Boolean) resp.get("is_paired");
	}
	
	public String getPeerGuid() {
		return peerGuid;
	}
	
	public boolean isPaired() {
		return isPaired == null ? false : (boolean) isPaired;
	}

}
