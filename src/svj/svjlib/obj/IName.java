package svj.svjlib.obj;


/**
 * Интерфейс объектов, имеющих название (как правило, на русском).
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.09.2010 14:11:24
 */
public interface IName {
    default String getName() {
        return "-";
    };
}
