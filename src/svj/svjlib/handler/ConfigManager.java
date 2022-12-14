package svj.svjlib.handler;

import svj.svjlib.exc.WEditException;
import svj.svjlib.svjlibs.SLCons;
import svj.svjlib.svjlibs.SLPar;
import svj.svjlib.tools.FileTools;
import svj.svjlib.tools.StringTools;

import java.util.*;

/**
 * Работает с конфиг-файлом.
 * <BR/> Формат файла - Проперти
 * <BR/> Путь - SLCons.CONFIG_FILE
 * <BR/> Рабочая директория - {home}/svjlib
 */
public class ConfigManager {

    private static String EXPORT_DIR_P = "exportDir";
    private static String LIBS_INFO_DIR_P = "libsDir";

    /**
     * директрия где располагается конфиг-файл. Пример: {home}/svjlib
     */
    //private final String dirPath;

    /**
     * Конфиг-файл - SLCons.CONFIG_FILE - константа
     */
    //private final String configFileName;

    /**
     * флаг, был ли загружен конфиг-файл
     */
    //private boolean loaded = false;

    private Properties props = new Properties();

    public void load () throws WEditException {
        props = FileTools.loadProperties(SLCons.CONFIG_FILE);
        //loaded = true;
    }

    public void save () throws WEditException {
        FileTools.saveProps(SLCons.CONFIG_FILE, props);
    }

    public void update() {
        String str = props.getProperty(EXPORT_DIR_P);
        if (! StringTools.isEmpty(str)) {
            SLPar.EXPORT_DIR = str;
        }
        str = props.getProperty(LIBS_INFO_DIR_P);
        if (! StringTools.isEmpty(str)) {
            SLPar.LIBS_INFO_DIR = str;
        }
    }

    public void setExportDir(String dirPath) {
        if (! StringTools.isEmpty(dirPath)) {
            props.put(EXPORT_DIR_P, dirPath);
        }
    }

    public void setLibsDir(String dirPath) {
        if (! StringTools.isEmpty(dirPath)) {
            props.put(LIBS_INFO_DIR_P, dirPath);
        }
    }

    @Override
    public String toString() {
        return "ConfigManager{" +
                "configFileName='" + SLCons.CONFIG_FILE + '\'' +
                "; props=" + props +
                '}';
    }
}
