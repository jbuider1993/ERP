package com.inform.ca.template;

import java.io.File;

public class Template {
    private String packageName;
    private String templatePath;
    private String autoCodePath;
    private String templateFileName;
    private String autoCodeFileName;
       
    private String getAutoCodeFileNameFromTemplateFileName(String fileName, String matchExtenstion) {
    	if (fileName.indexOf(matchExtenstion) >=0 && matchExtenstion.indexOf(".") >0) {
    		String autoCodeFileExtersion = matchExtenstion.substring(0, matchExtenstion.indexOf("."));
    		return fileName.replaceAll(matchExtenstion, autoCodeFileExtersion);   		
    	} else {
    		return fileName;
    	}
    }   
    
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
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
	public String getTemplateFileName() {
		return templateFileName;
	}
	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;	
        String templateExtenstion = templateFileName.substring(templateFileName.indexOf(".") + 1);
        this.autoCodeFileName = getAutoCodeFileNameFromTemplateFileName(templateFileName, templateExtenstion);				
	}
	public String getAutoCodeFileName() {			
		return autoCodeFileName;
	}
   
	public String getAutoCodeFullName() {
		return autoCodePath + File.separator + autoCodeFileName;
	}
	
	public String getTemplateFullName() {
		return templatePath + File.separator + templateFileName;
	}
}
