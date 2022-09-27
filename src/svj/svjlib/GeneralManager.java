package svj.svjlib;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 21.09.22 18:57
 */
public class GeneralManager {

    //private FunctionManager fm;
    private ContentFrame    frame;
    //private ConfigManager   config;


    public ContentFrame getFrame ()
    {
        return frame;
    }


    public void close ()
    {
        // Сохраняем параметры пользователя - НЕТ - сохораняем и сразу же, при доабвлениях и изменениях.
    }

}
