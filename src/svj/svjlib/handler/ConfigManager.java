package svj.svjlib.handler;

import svj.svjlib.exc.WEditException;
import svj.svjlib.tools.FileTools;

import java.util.*;

/**
 * Работает с конфиг-файлом.
 * <BR/> Формат файла - Проперти
 * <BR/> Рабочая директория - {home}/svjlib
 */
public class ConfigManager {

    /**
     * директрия где располагается конфиг-файл. Пример: {home}/svjlib
     */
    //private final String dirPath;

    /**
     * Конфиг-файл
     */
    private final String configFileName;

    /**
     * флаг, был ли загружен конфиг-файл
     */
    private boolean loaded = false;

    private Properties props = null;

    public ConfigManager(String configFileName) {
        this.configFileName = configFileName;
    }

    public String getConfigFileName() {
        return configFileName;
    }

    public void load () throws WEditException {
        props = FileTools.loadProperties(configFileName);
        loaded = true;
    }

    public void save () throws WEditException {
        if (props != null)
            FileTools.saveProps(configFileName, props);
    }

}
