package com.inform.ca.template;

import java.util.List;
import java.util.Map;

public class ConfigProperty {
     private String name;
     private String version;
     private Map<String, Object> context;
     private List<TempPackage> packages;
         
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public Map<String, Object> getContext() {
		return context;
	}
	public void setContext(Map<String, Object> context) {
		this.context = context;
	}
	public List<TempPackage> getPackages() {
		return packages;
	}
	public void setPackages(List<TempPackage> packages) {
		this.packages = packages;
	}

   
}
