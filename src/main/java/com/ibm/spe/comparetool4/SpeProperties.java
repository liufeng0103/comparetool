package com.ibm.spe.comparetool4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 从spe.properties中获取需要的配置
 * @author Luis
 */
public class SpeProperties {
	// Properties文件路径
    private static final String PROPERTIES_FILENAME = "./config/spe.properties";
    
    private static Properties propChecker = null;
    
    /**
     * Some class level initialization
     */
    static {    	
    	// Load the properties from file so they are available for each accessor method
    	reloadProperties();
    }
    
    /**
     * Don't let anyone instantiate this class.
     */
    private SpeProperties() {};
    
    /**
     * Load the Checker properties from the checker.properties file
     */
    private static final void loadProperties() {
		try {
			if(propChecker == null) {
				propChecker = new Properties();
				FileInputStream inProperties = new FileInputStream(PROPERTIES_FILENAME);
				propChecker.load(inProperties);
				inProperties.close();
			}
		} catch (FileNotFoundException e) {
			System.err.println("找不到配置文件: " + PROPERTIES_FILENAME);
			throw new RuntimeException();
		} catch (IOException e) {
			System.err.println(PROPERTIES_FILENAME + " 配置文件加载失败");
			throw new RuntimeException();
		}
    }
    
    /**
     * Reload the properties from the cfc.properties file
     */
    public static final void reloadProperties() {
    	loadProperties();
    }
    
    /**
     * 获取Financial文件跟mrt文件比较时要比较的sheet，这些sheet是在financial文件中的
     * @return String
     */
    public static final String[] getFMSheets() {
    	reloadProperties();
    	if(propChecker.getProperty("FM.compared_sheets") == null) {
    		throw new RuntimeException("Financial文件跟Mrt文件比较测试中没有配置要测试的sheet, 请在spe.properties中配置FM.compared_sheets, sheet之间用,隔开");
    	}
    	return propChecker.getProperty("FM.compared_sheets").split(",");
    }
    
    /**
     * 获取mrt跟mrt文件比较是要比较的sheet
     * @return String
     */
    public static final String[] getMMSheets() {
    	reloadProperties();
    	if(propChecker.getProperty("MM.compared_sheets") == null) {
    		throw new RuntimeException("Mrt文件跟Mrt文件比较测试中没有配置要测试的sheet, 请在spe.properties中配置MM.compared_sheets, sheet之间用,隔开");
    	}
    	return propChecker.getProperty("MM.compared_sheets").split(",");
    }
    
    /**
     * 获取Financial文件跟mrt文件比较时要比较的sheet，这些sheet是在financial文件中的
     * @return String
     */
    public static final String[] getFFSheets() {
    	reloadProperties();
    	if(propChecker.getProperty("FF.compared_sheets") == null) {
    		throw new RuntimeException("Financial文件跟Financial文件比较测试中没有配置要测试的sheet, 请在spe.properties中配置FM.compared_sheets, sheet之间用,隔开");
    	}
    	return propChecker.getProperty("FF.compared_sheets").split(",");
    }
    
    /**
     * 比较结果的文件名,如果没有配置默认为CompareResult.xls
     * @return String
     */
    public static final String getResultFileName() {
    	reloadProperties();
    	return propChecker.getProperty("result_file", "CompareResult.xls");
    }
    
    /**
     * 是否忽略US Global数据
     * @return String
     */
    public static final boolean ignoreUsGlobal() {
    	reloadProperties();
    	if("no".equalsIgnoreCase(propChecker.getProperty("ignore_us_global"))) {
    		return false;
    	} else {
    		return true;
    	}    	
    }
    
    
    public static final String getProperty(String property) {
    	reloadProperties();
    	return propChecker.getProperty(property);
    }

    public static final String getBigFileSize() {
        reloadProperties();
        return propChecker.getProperty("file_size", "5000000");
    }
}
