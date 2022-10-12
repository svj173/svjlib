package svj.svjlib.svjlibs.manager;

import svj.svjlib.Log;
import svj.svjlib.exc.WEditException;
import svj.svjlib.svjlibs.SLCons;
import svj.svjlib.svjlibs.SLPar;
import svj.svjlib.svjlibs.obj.LibInfo;
import svj.svjlib.tools.FileTools;

import java.io.File;
import java.util.*;

/**
 * Работает со списком библиотек.
 * <BR/>
 * <BR/> svj, 2022-10-11
 */
public class LibsManager {

    /**
     * Список загруженных библиотек. Возможно, сортировать по названию.
     * Ключ - индекс библиотеки (ее ИД). Начинается с 0.
     */
    private Map<Long, LibInfo> libs = new HashMap<>();

    public void addLib(LibInfo libInfo) {
        if (libInfo != null) {
            libs.put(libInfo.getId(), libInfo);
        }
    }

    public void checkLib(LibInfo libInfo) throws WEditException {
        if (libInfo == null) {
            String errMsg = "Не задана библиотека";
            throw new WEditException(errMsg);

        }

        LibInfo l = libs.get(libInfo.getId());
        if (l != null)  {
            String errMsg = "Библиотека с номером '" + libInfo.getId() + "' не может быть загружена - такая уже есть: " + l;
            throw new WEditException(errMsg);
        }

        // Проверка на директорию
        String libDir = libInfo.getLibDir();
        for (LibInfo l1 : libs.values()) {
            if (l1.getLibDir().equals(libDir)) {
                String errMsg = "Библиотека с директорией '" + libDir + "' не может быть загружена - такая уже есть: " + l1;
                throw new WEditException(errMsg);
            }
        }
    }
    
    public void checkLibDir(String libDir) throws WEditException {
        if (libDir == null) {
            String errMsg = "Не задана директория библиотеки";
            throw new WEditException(errMsg);

        }

        /*
        LibInfo l = libs.get(lib.getIndex());
        if (l != null)  {
            String errMsg = "Библиотека с номером '" + lib.getIndex() + "' не может быть загружена - такая уже есть: " + l;
            throw new WEditException(errMsg);
        }
        */

        // Проверка на директорию
        for (LibInfo l1 : libs.values()) {
            if (l1.getLibDir().equals(libDir)) {
                String errMsg = "Библиотека с директорией '" + libDir + "' не может быть загружена - такая уже есть: " + l1;
                throw new WEditException(errMsg);
            }
        }
    }

    /**
     * Сохранить изменения в конфиг-файл - libs.xml
     */
    public void saveFile() throws Exception {
        // Сформировать полный путь до файла
        String fileName = SLPar.CONF_DIR + File.separator + SLCons.LIBS_FILE_NAME;
        Log.file.info("Libs config file name = {}", fileName);

        // Если нет промежуточных директорий - создать
        FileTools.createFolder(new File(SLPar.CONF_DIR));

        // Сохранить Map<Integer, LibInfo> libs в файле как XML.
        // - если был такой файл - перезаписать
        String text = getLibsAsXml();
        FileTools.save(fileName, text);
    }

    private String getLibsAsXml() {
        StringBuilder sb = new StringBuilder(512);

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<libs>\n");

        for (LibInfo libInfo : libs.values()) {
            sb.append("  <lib id='");
            sb.append(libInfo.getId());
            sb.append("' name='");
            sb.append(libInfo.getName());
            sb.append("' libDir='");
            sb.append(libInfo.getLibDir());
            sb.append("' />\n");
        }

        sb.append("</libs>\n");

        return sb.toString();
    }

}
