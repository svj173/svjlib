package svj.svjlib.svjlibs.manager;

import svj.svjlib.exc.WEditException;
import svj.svjlib.svjlibs.obj.LibInfo;

import java.util.*;

/**
 * Работает со списком библиотек.
 * <BR/>
 * <BR/> svj, 2022-10-11
 */
public class LibsManager {

    /**
     * Списко загруженных библиотек. Возможно, сортировать по названию.
     */
    private Map<Integer, LibInfo> libs = new HashMap<>();

    public void addLib(LibInfo lib) throws WEditException {
        if (lib == null) return;

        LibInfo l = libs.get(lib.getIndex());
        if (l != null)  {
            String errMsg = "Библиотека с номером '" + lib.getIndex() + "' не может быть загружена - такая уже есть: " + l;
            throw new WEditException(errMsg);
        }
    }
    
}
