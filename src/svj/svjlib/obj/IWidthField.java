package svj.svjlib.obj;

/**
 * Интерфейс описания колонок в таблице
 * - Имя
 * - Пропорциональная ширина по умолчани (в сумме должны давать еденицу)
 * задать 0 если колонку не надо включать по умолчанию
 */
public interface IWidthField extends IName, IWidth {

    /*
     todo Сделать abstract и перенести сюда общее для всех Field обьектов.
    private String  name, descr;
    private Class   fieldClass;
    private int     strDbWidth;

    private boolean reinitFromLanguage = false;

     */
}
