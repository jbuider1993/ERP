package com.inform.ca.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.inform.ca.template.TemplatePack;

public class Config {
    private String templateBasePath;
    private String autoCodeBasePath;
    
	private static final String mainPublicPath = "src" + File.separator + "main" + File.separator + "java";
	private static final String resourcesPublicPath = "src" + File.separator + "main" + File.separator + "resources";
	private static final String testPublicPath = "src" + File.separator + "test" + File.separator + "java";
	
	public static final String javaTemplateExternd = "java.p.vm";
	public static final String propertyTemplateExternd = "property.p.vm";
	public static final String pomTemplateExternd = "xml.p.vm";
	public static final String suffixOfConfig = ".vm.json";
	public static final String suffixOfTemp = ".vm";
	
	
    private String templateJavaPath;
    private String templateRootPackage;
    
    private String autoCodeJavaPath;
    private String autoCodeRootPackage;
        
    private String templateGroupId;
    private String autoCodeGroupId;
    private static Map<String, TemplatePack> mapTemplatePacks = new HashMap();
    private static TemplatePack notTemplatePack = new TemplatePack();
    
    static {
    	mapTemplatePacks.put(javaTemplateExternd, new TemplatePack());
    	mapTemplatePacks.put(propertyTemplateExternd, new TemplatePack());
    	mapTemplatePacks.put(pomTemplateExternd, new TemplatePack());
    }
    
    private static final String pomXMLFileName = "pom.xml";
    
    
    public Map<String, TemplatePack> getMapTemplatePacks() {
    	return mapTemplatePacks;
    }
    
    public TemplatePack getNotTemplatePack() {
    	return notTemplatePack;
    }
    
    public String getTemplatePOMPath() {
    	return templateBasePath;
    }
    
    
    public String getAutoCodePOMPath() {
    	return autoCodeBasePath;
    }
     
    public String getTemplateResourcesPath() {
    	return  templateBasePath + File.separator + resourcesPublicPath; 
    }
    
    public String getTemplateTestPath() {
    	return  templateBasePath + File.separator + testPublicPath; 
    }
    
    public String getAutoCodeTestPath() {
    	return getAutoCodeBasePath() + File.separator + testPublicPath;
    }
    
    public String getAutoCodeResourcesPath() {
    	return getAutoCodeBasePath() + File.separator + resourcesPublicPath;
    }
    
	public String getTemplateJavaPath() {
		return templateBasePath + File.separator + mainPublicPath + File.separator + StringUtils.replace(templateRootPackage, ".", File.separator);
	}

	public String getTemplateRootPackage() {
		return templateRootPackage;
	}
	public void setTemplateRootPackage(String templateRootPackage) {
		this.templateRootPackage = templateRootPackage;
	}
	
	public String getAutoCodeJavaPath() {
		return autoCodeBasePath + File.separator + mainPublicPath + File.separator + StringUtils.replace(autoCodeRootPackage, ".", File.separator);
	}

	public String getAutoCodeRootPackage() {
		return autoCodeRootPackage;
	}
	public void setAutoCodeRootPackage(String autoCodeRootPackage) {
		this.autoCodeRootPackage = autoCodeRootPackage;
	}
    
	
	public String getTemplateBasePath() {
		return templateBasePath;
	}
	public void setTemplateBasePath(String templateBasePath) {
		this.templateBasePath = templateBasePath;
	}
	public String getAutoCodeBasePath() {
		return autoCodeBasePath;
	}
	public void setAutoCodeBasePath(String autoCodeBasePath) {
		this.autoCodeBasePath = autoCodeBasePath;
	}
	public String getTemplateGroupId() {
		return templateGroupId;
	}
	public void setTemplateGroupId(String templateGroupId) {
		this.templateGroupId = templateGroupId;
	}
	public String getAutoCodeGroupId() {
		return autoCodeGroupId;
	}
	public void setAutoCodeGroupId(String autoCodeGroupId) {
		this.autoCodeGroupId = autoCodeGroupId;
	}
	
	public void scanTEmplateChangeXMLExtend(String fromExtend, String toExtend) {
		changeFileExtend(getTemplateBasePath(),  fromExtend, toExtend);
	}
		
	public void scanTemplateChangeJavaExtend(String fromExtend, String toExtend) {
		changeFileExtend(getTemplateBasePath(), fromExtend, toExtend);
	}		
	
	public void scanTemplateChangeExtend(String fromExtend, String toExtend) {
		changeFileExtend(getTemplateBasePath(), fromExtend, toExtend);
	}	
	
	private void changeFileExtend(String path, String fromExtend, String toExtend) {
		File files = new File(path);
		List<String> subPaths = new ArrayList<String>();
		for (File file : files.listFiles()) {
			if (file.isDirectory()) {
				subPaths.add(file.getAbsolutePath());
			} else {
				if (StringUtils.reverse(file.getAbsolutePath()).indexOf(StringUtils.reverse(fromExtend)) == 0) {
					String fileName = StringUtils.reverse(file.getAbsolutePath());
					if (fileName.indexOf(StringUtils.reverse(fromExtend)) == 0) {
					   String changeName = StringUtils.reverse(fileName.replaceFirst(StringUtils.reverse(fromExtend), StringUtils.reverse(toExtend))); 
					   File changeTo = new File(changeName);
					   file.renameTo(changeTo);
					}
				}
			}
			
		}
		
		for (String subPath : subPaths) {
			changeFileExtend(subPath,  fromExtend, toExtend);
		}
	}
    
}
