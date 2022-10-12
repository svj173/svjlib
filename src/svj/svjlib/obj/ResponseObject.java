package svj.svjlib.obj;


import svj.svjlib.exc.WEditException;


/**
 * Простой обьект ответа. Применяется в SwingWorker, чтобы из другого потока получить информативный результат.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 27.04.2012 14:32:32
 */
public class ResponseObject {
    /* Собственно обьект ответа. Object - т.к. может и через сокет передаваться. */
    private Object object;
    /* исключение - на случай ошибки выполнения. */
    private WEditException ex = null;
    private Object object2;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public WEditException getException() {
        return ex;
    }

    public void setException(WEditException ex) {
        this.ex = ex;
    }

    public boolean isError() {
        return ex != null;
    }

    public String toString() {
        Object obj;
        StringBuilder result;

        result = new StringBuilder(512);

        result.append("[ ResponseObject: ");

        obj = getObject();
        result.append("object value = '");
        result.append(obj);
        result.append("';");
        if (obj != null) {
            result.append(" object class = '");
            result.append(obj.getClass().getSimpleName());
            result.append("';");
        }
        result.append(" ex = '");
        result.append(getException());
        result.append("']");

        return result.toString();
    }

    public Object getObject2() {
        return object2;
    }

    public void setObject2(Object object2) {
        this.object2 = object2;
    }
}
