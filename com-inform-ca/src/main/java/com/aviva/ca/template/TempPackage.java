package com.inform.ca.template;

import java.util.List;
import java.util.Map;

public class TempPackage {
   private String name;
   private String templateBase;
   private String targetBase;
   private Map<String, Object> context;
   private Map<String, String> templateTargets;
   private List<TempPackage> packages;
     
	public Map<String, Object> getContext() {
		return context;
	}
	public void setContext(Map<String, Object> context) {
		this.context = context;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, String> getTemplateTargets() {
		return templateTargets;
	}
	public void setTemplateTargets(Map<String, String> templateTargets) {
		this.templateTargets = templateTargets;
	}
	
	public String getTemplateBase() {
		return templateBase;
	}
	public void setTemplateBase(String templateBase) {
		this.templateBase = templateBase;
	}
	public String getTargetBase() {
		return targetBase;
	}
	public void setTargetBase(String targetBase) {
		this.targetBase = targetBase;
	}
	
	public List<TempPackage> getPackages() {
		return packages;
	}
	public void setPackages(List<TempPackage> packages) {
		this.packages = packages;
	}
	
	
}
