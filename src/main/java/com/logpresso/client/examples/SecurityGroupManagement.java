package com.logpresso.client.examples;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import com.logpresso.client.Logpresso;
import com.logpresso.client.SecurityGroup;

public class SecurityGroupManagement {
	private Logpresso logpresso;

	public static void main(String[] args) throws IOException {
		new SecurityGroupManagement().run();
	}

	public void run() throws IOException {
		logpresso = new Logpresso();
		try {
			// System.out.println("foo");
			logpresso.connect(Settings.HOST, Settings.USER, Settings.PASSWORD);

			createSecurityGroup();
			updateSecurityGroup();
			removeSecurityGroup();
		} finally {
			logpresso.close();
		}

	}

	private void createSecurityGroup() throws IOException {
		if (findSecurityGroup(logpresso, "demo") != null) {
			System.out.println("demo security group already exists. skipping.");
			return;
		}

		SecurityGroup group = new SecurityGroup();
		group.setName("demo");
		group.setAccounts(new HashSet<String>(Arrays.asList("root")));
		group.setGrantedTables(new HashSet<String>(Arrays.asList("sys_cpu_logs", "araqne_query_logs")));
		logpresso.createSecurityGroup(group);
		System.out.println("created demo security group.");
	}

	private void updateSecurityGroup() throws IOException {
		SecurityGroup group = findSecurityGroup(logpresso, "demo");
		if (group == null) {
			System.out.println("demo security group already exists. skipping.");
			return;
		}
		
		group.setName("demo");
		group.setDescription("updated description");
		group.setAccounts(new HashSet<String>(Arrays.asList("root")));
		group.setGrantedTables(new HashSet<String>(Arrays.asList("sys_cpu_logs")));
		logpresso.updateSecurityGroup(group);
		System.out.println("updated demo security group.");
	}
	
	private void removeSecurityGroup() throws IOException {
		SecurityGroup group = findSecurityGroup(logpresso, "demo");
		if (group == null) {
			System.out.println("demo security group not found. skipping.");
			return;
		}

		logpresso.removeSecurityGroup(group.getGuid());
		System.out.println("removed demo security group");
	}

	private SecurityGroup findSecurityGroup(Logpresso logpresso, String name) throws IOException {
		// SDK does not support find api for now.
		for (SecurityGroup group : logpresso.listSecurityGroups()) {
			if (group.getName().equals(name))
				return group;
		}
		return null;
	}
}
