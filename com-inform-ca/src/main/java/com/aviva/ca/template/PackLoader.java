package com.inform.ca.template;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.commons.lang3.StringUtils;

import com.inform.ca.config.Config;

public class PackLoader {
     private List<Template>  javaTemplates = new ArrayList();
     private List<Template>  testTemplates = new ArrayList();
     private List<Template>  resourcesTemplates = new ArrayList();
     private List<Template>  pomTemplates = new ArrayList();
     private List<Template>  notTemplates = new ArrayList();
     private TemplatePack templatePack;
     private Config config;
     
     public PackLoader(Config config) {
    	 this.config = config;
    	 templatePack = loadTemplatePack();
    	 
    	 
     }
     
     
     public TemplatePack loadPomTemplate() {
    	 TemplatePack templatePack = new TemplatePack();
    	 templatePack.setTemplatePath(config.getTemplatePOMPath());
    	 templatePack.setAutoCodePath(config.getAutoCodePOMPath());
    	 loadSubTemplatePack(templatePack);
    	 return templatePack;
    	 
     }
     
     public TemplatePack loadResourcesTemplate() {
    	 TemplatePack templatePack = new TemplatePack();
    	 templatePack.setTemplatePath(config.getTemplateResourcesPath());
    	 templatePack.setAutoCodePath(config.getAutoCodeResourcesPath());
    	 loadSubTemplatePack(templatePack);
    	 return templatePack;
     }
     
   
     public TemplatePack loadTestTemplatePack() {
         TemplatePack templatePack = new TemplatePack();
         templatePack.setTemplatePath(config.getTemplateTestPath());
         templatePack.setAutoCodePath(config.getAutoCodeTestPath());
         templatePack.setPackageName(config.getAutoCodeRootPackage());         
         loadSubTemplatePack(templatePack);
     	 return templatePack; 
     }
     
     public TemplatePack loadJavaTemplatePack() {
        TemplatePack templatePack = new TemplatePack();
        templatePack.setTemplatePath(config.getTemplateJavaPath());
        templatePack.setAutoCodePath(config.getAutoCodeJavaPath());
        templatePack.setPackageName(config.getAutoCodeRootPackage());
        loadSubTemplatePack(templatePack);
    	return templatePack; 
     }
     
     
     public TemplatePack loadTemplatePack() {
    	 TemplatePack templatePack = new TemplatePack();
         templatePack.setTemplatePath(config.getTemplateBasePath());
         templatePack.setAutoCodePath(config.getAutoCodeBasePath());
         loadSubTemplatePack(templatePack);
     	 return templatePack;
     }
     
     public TemplatePack loadSubTemplatePack(TemplatePack templatePack) {
    	 String path = templatePack.getTemplatePath();
    	 File files = new File(path);
    	 
    	 List<Template> templates = new ArrayList<Template>();
    	 
    	 if (files.listFiles() != null) {
        	 List<TemplatePack> subPacks = new ArrayList<TemplatePack>();
	    	 for (File file : files.listFiles()) {
	    		if (!file.isHidden()) {
		    		if (file.isDirectory()) {
		    			TemplatePack pack = new TemplatePack();
		    			pack.setTemplatePath(file.getAbsolutePath());
		    			pack.setAutoCodePath(templatePack.getAutoCodePath() + File.separator + file.getName());
		    			pack.setPackageName(templatePack.getPackageName() + "." + file.getName());
		    			subPacks.add(pack);
		    		} else {
		    			Template template = new Template();

		    			template.setTemplatePath(file.getAbsolutePath().replaceAll(file.getName(), ""));
		    			template.setAutoCodePath(templatePack.getAutoCodePath());
		    			String autoCodeFileName = changeTemplateFileNameToAutoCodeFileName(file.getName());
		    			template.setTemplateFileName(file.getName());
		    			template.setPackageName(templatePack.getPackageName());
		                templates.add(template);
		    			if (StringUtils.reverse(file.getAbsolutePath()).indexOf(StringUtils.reverse(Config.javaTemplateExternd)) == 0) {
		    				addJavaTemplate(template);
		    			} else if (StringUtils.reverse(file.getAbsolutePath()).indexOf(StringUtils.reverse(Config.propertyTemplateExternd)) == 0) {
		    				resourcesTemplates.add(template);
		    			} else if (StringUtils.reverse(file.getAbsolutePath()).indexOf(StringUtils.reverse(Config.pomTemplateExternd)) == 0) {
		    				pomTemplates.add(template);
		    			}
		    				
		    		}
		    	 }
		    	 templatePack.setSubPack(subPacks);
		    	 templatePack.setTemplates(templates);
	    	 }
	    	 for (TemplatePack pack : subPacks) {
	    		 loadSubTemplatePack(pack);
	    	 }
	    	 
    	 } else {
    		File file = files;
    		if (!file.isHidden()) {
	 			Template template = new Template();
	 			template.setTemplatePath(file.getAbsolutePath().replaceAll(file.getName(), ""));
	 			template.setAutoCodePath(templatePack.getAutoCodePath());
	 			template.setTemplateFileName(file.getName());
	 			template.setPackageName(templatePack.getPackageName());
	            templates.add(template); 
	       	    templatePack.setTemplates(templates);
    		}
    	 }   	 
    	 return templatePack; 
     }
     
     private void addJavaTemplate(Template template) {
    	 changeJavaAutoCodePath(template);
    	 javaTemplates.add(template);
     }
     
     
     private void changeJavaAutoCodePath(Template template) {
    	 String templatePackage = config.getTemplateRootPackage().replaceAll("[.]", Matcher.quoteReplacement(File.separator));
    	 String autoCodePackage = config.getAutoCodeRootPackage().replaceAll("[.]", Matcher.quoteReplacement(File.separator));
    	 template.setAutoCodePath(template.getAutoCodePath().replace(templatePackage, autoCodePackage));
     }
     
     private String changeTemplateFileNameToAutoCodeFileName(String templateFileName) {
    	 String pattern = "[.]\\w+[.]vm";
    	 String fileNameNoExtend = templateFileName.replaceAll(pattern, "");	 
    	 return StringUtils.reverse(fileNameNoExtend);
     }
     
     public List<Template> getTemplates() {
    	 List<Template> templates = new ArrayList<Template>(); 
     	 getTemplatesFromSubPack(templatePack, templates); 
       	     	 
    	 return templates; 	 
     }

     private void getTemplatesFromSubPack(TemplatePack subTemplatePack, List<Template> templates) {
    	 for (Template template : subTemplatePack.getTemplates()) {
    		 templates.add(template);
    	 }
    	 
    	 for (TemplatePack pack : subTemplatePack.getSubPack()) {
    		 getTemplatesFromSubPack(pack, templates);
    	 }
     }
     
    public TemplatePack getTemplatePackByExternd(String fileName) {
    	Map<String, TemplatePack> mapTemplatePacks = config.getMapTemplatePacks();
    	
    	for (String key : mapTemplatePacks.keySet()) {
    		if (StringUtils.reverse(fileName).indexOf(StringUtils.reverse(key)) == 0) {
    			return mapTemplatePacks.get(key);
    		}
    	}
    	return null;
    }
     
     
	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}
	
     
}
