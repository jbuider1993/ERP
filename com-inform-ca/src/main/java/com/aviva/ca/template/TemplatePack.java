package com.inform.ca.template;

import java.util.List;

public class TemplatePack {
	
	private String packageName;
    private String templatePath;
    private String autoCodePath;
    private List<Template> templates;
    private List<TemplatePack> subPack;
    
    public TemplatePack() {
    }
    
    public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public List<Template> getTemplates() {
		return templates;
	}
	public void setTemplates(List<Template> templates) {
		this.templates = templates;
	}
	public List<TemplatePack> getSubPack() {
		return subPack;
	}
	public void setSubPack(List<TemplatePack> subPack) {
		this.subPack = subPack;
	}
	public String getTemplatePath() {
		return templatePath;
	}
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	public String getAutoCodePath() {
		return autoCodePath;
	}
	public void setAutoCodePath(String autoCodePath) {
		this.autoCodePath = autoCodePath;
	}
	
}
