package com.inform.ca.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.MatchResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;







import com.inform.ca.config.Config;

import static java.lang.Integer.parseInt;
import static org.apache.commons.io.FilenameUtils.getName;

public class VelocityGenerator {

	// @Autowired
	private VelocityEngine engine = new VelocityEngine();
			
	private void processDirectory(VelocityContext context, String templateFile, String outCodeFile) {
		File template = new File(templateFile);
		File[] fileList = template.listFiles();
		for (File file : fileList) {
			if (file.isFile()) {
				String tempFile = templateFile + "/" + file.getName();
				String outFile  = outCodeFile + "/" + StringUtils.removeEnd(file.getName(), Config.suffixOfTemp);
				generateTemplateToTarget(context, tempFile, outFile);	
			} else if (file.isDirectory()) {
				String tempFile = templateFile + "/" + file.getName();
				String outFile  = outCodeFile + "/" + file.getName();
				processDirectory(context, tempFile, outFile);				
			}
		}
	}
	
    public void evaluateTempack(VelocityContext context, String templateFile, String outCodeFile) {
		File template = new File(templateFile);
		if (template.isFile()) {
			generateTemplateToTarget(context, templateFile, outCodeFile);
		} else if (template.isDirectory()) {
			processDirectory(context, templateFile, outCodeFile);
		}
    }
     
    private void generateTemplateToTarget(VelocityContext context, String templateFile, String outCodeFile) {
    	if (StringUtils.endsWith(templateFile, Config.suffixOfTemp)) {
    		evaluate(context, templateFile, outCodeFile);
    	} else {
    		copyFile(templateFile, outCodeFile);
    	}
    }
    
    private void copyFile(String templateFile, String outCodeFile) {
    	try {
			FileUtils.copyFile(new File(templateFile), new File(outCodeFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	private void evaluate(VelocityContext context, String templateFile, String outCodeFile) {
		File template = new File(templateFile);
		String templatePath = template.getParent();
		String fileName = template.getName();
		VelocityEngine engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "file, classpath, string");
		engine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, templatePath);
		engine.setProperty(RuntimeConstants.INPUT_ENCODING, StandardCharsets.UTF_8.name());
		engine.setProperty(RuntimeConstants.ENCODING_DEFAULT, StandardCharsets.UTF_8.name());
		engine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, Boolean.FALSE);
		engine.setProperty("file.resource.loader.class", FileResourceLoader.class.getName());
		
		engine.init();

		final VelocityContext ctx = new VelocityContext(context);
		try {			
		    final org.apache.velocity.Template t = engine.getTemplate(fileName);

		    FileWriter fileWriter;
			File autoCodeOut = new File(outCodeFile);
			autoCodeOut.getParentFile().mkdirs();
			fileWriter = new FileWriter(autoCodeOut);
			t.merge(ctx, fileWriter);
			fileWriter.flush();
			fileWriter.close();
		} catch(IOException e) {
			displayLinesInError(e, templateFile);
			e.printStackTrace();
		} catch(ParseErrorException parseException) {
			displayLinesInError(parseException, templateFile);
			throw new IllegalStateException();
		} catch(MethodInvocationException mie) {
			throw mie;
		} finally {}		
	}

	
	private void evaluate(Map<String, Object > context, Template template) throws IOException {
		try {
			VelocityEngine engine = new VelocityEngine();
			String templatePath = template.getTemplatePath();
			engine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, templatePath);
			engine.init();
			final org.apache.velocity.Template t = engine.getTemplate(template.getTemplateFileName());
			final VelocityContext ctx = new VelocityContext(context);
			String autoCodeFileName = template.getAutoCodeFullName();
			File autoCodeOut = new File(autoCodeFileName);
			autoCodeOut.getParentFile().mkdirs();
			FileWriter fileWriter = new FileWriter(autoCodeOut);
			t.merge(ctx, fileWriter);
			fileWriter.flush();
			fileWriter.close();
		} catch(ParseErrorException parseException) {
			throw new IllegalStateException();
		} catch(MethodInvocationException mie) {
			throw mie;
		} finally {}
	}


	private String getAutoCodeFileExtenstion(String fileName, String matchExtenstion) {
		if (fileName.indexOf(matchExtenstion) >= 0) {
			String autoCodeFileExtersion = matchExtenstion.substring(0, matchExtenstion.indexOf("."));
			return fileName.replaceAll(matchExtenstion, autoCodeFileExtersion);
		} else {
			return fileName;
		}
	}
	
	
	private void displayLinesInError(IOException e2,  String templateFile) {
        Scanner scanner = new Scanner(e2.getMessage());
        String match = scanner.findInLine("\\[line (\\d+), column (\\d+)\\]");
        if (match == null) {
            return;
        }
        MatchResult result = scanner.match();
        int lineInError = parseInt(result.group(1));
        int column = parseInt(result.group(2));
		
        String[] lines;
		try {
			lines = new String(Files.readAllBytes(Paths.get(templateFile)), StandardCharsets.UTF_8).split("\\n");
            int linesBeforeToDisplay = 2;
            int linesAfterToDisplay = 2;
           
            for (int i = Math.max(0, lineInError - linesBeforeToDisplay); i < lineInError; i++) {
                System.err.println( lines[i] );
            }
			
            System.err.println(String.format("Line number: %d Column : %d" , lineInError, column ));
            
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	private void displayLinesInError(VelocityException exception,  String templateFile) {
        Scanner scanner = new Scanner(exception.getMessage());
        String match = scanner.findInLine("\\[line (\\d+), column (\\d+)\\]");
        if (match == null) {
            return;
        }
        MatchResult result = scanner.match();
        int lineInError = parseInt(result.group(1));
        int column = parseInt(result.group(2));
		
        String[] lines;
		try {
			lines = new String(Files.readAllBytes(Paths.get(templateFile)), StandardCharsets.UTF_8).split("\\n");
            int linesBeforeToDisplay = 2;
            int linesAfterToDisplay = 2;
           
            for (int i = Math.max(0, lineInError - linesBeforeToDisplay); i < lineInError; i++) {
                System.err.println( lines[i] );
            }
			
            System.err.println(String.format("Line number: %d Column : %d" , lineInError, column ));
            
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void displayLinesInError(VelocityException exception, TemplatePack templatePack, Template template) {
		try {
			Scanner scanner = new Scanner(exception.getMessage());
			String match = scanner.findInLine("\\[line (\\d+), column (\\d+)\\]");
			if (match == null) {
				return;
			}
			MatchResult result = scanner.match();
			int lineInError = parseInt(result.group(1));
			int column = parseInt(result.group(2));

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private String prefix(TemplatePack templatePack, Template template, int line) {
		return prefix(templatePack, template, "" + line);
	}

	private String prefix(TemplatePack templatePack, Template template, String message) {
		return "[" + templatePack.getPackageName() + ":" + getName(template.getTemplateFileName()) + ":" + message + "]";
	}
}