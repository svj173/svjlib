package svj.svjlib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Набор логгеров общего применения.
 * <BR/> Чтобы в каждом классе не создавать новые лог-сущности.
 * <BR/> 2022-09-16
 */
public class Log {

    // Применение

    /**
     * При работе с файлами.
     */
    public static final Logger FILE = LoggerFactory.getLogger("file");
    public static final Logger l = LoggerFactory.getLogger("kernel");

    private Log() {
    }

}
