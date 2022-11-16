package svj.svjlib.svjlibs.stax;


import svj.svjlib.Log;
import svj.svjlib.exc.WEditException;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 20.08.2011 9:37:33
 */
public class SvjStaxParser
{
    //protected static final QName NAME         = new QName("name");
    //protected static final QName TYPE         = new QName( ConfigParam.TYPE);

    /**
     * Удаляем символы, которые недопустимы внутри XML-атрибутов
     * Т.е. в конструкицях типа <author name="hdja/khdj"
     * @param value
     * @return
     */
    protected String replaceName(String value) {

        if (value == null) return value;

        value = value.trim();
        if (! value.isEmpty()) {
            value = value.replace('\'', '.');
            value = value.replace('\"', '.');
            value = value.replace('<', '.');
            value = value.replace('>', '.');
            value = value.replace('/', '.');
            value = value.replace('&', '.');
        }
        return value;
    }

    /**
     * Удаляем символы, которые недопустимы внутри текста XML-тегов.
     * Т.е. в конструкциях типа <author>dfksdhfksjldgjsld</author>,
     * аанотации, название серии
     */
    protected String processWrongSymbol(String text) {
        if (text == null) return "";

        text = text.replace('<', '.');
        text = text.replace('>', '.');
        text = text.replace('&', '.');
        if (text.isEmpty()) text = "XXX";
        return text;
    }


    protected String getText (XMLEventReader eventReader ) throws WEditException
    {
        String result = null;
        XMLEvent event = null;
        Characters characters;

        try
        {
            event = eventReader.nextEvent();
            //Log.file.debug ("getText: event = ", event );

            if ( event.isCharacters() )
            {
                characters = event.asCharacters();
                //Log.file.debug ("WEditStaxParser.getText: characters = ", characters );
                if ( characters != null )
                {
                    //result = characters.getData().trim();
                    result = characters.getData();
                    //if ( result.length() == 0 ) result = null;
                    //*
                    // Что это ??? - проверка что нельзя убирать крайние пробелы?
                    // - characters.isWhiteSpace() - true - значит вся стркоа состоит из пробелов.
                    // - characters.isIgnorableWhiteSpace()
                    if ( (result != null) && (!characters.isIgnorableWhiteSpace()) && (!characters.isWhiteSpace()) )
                    {
                        //result = characters.getData();
                        //result  = result.trim();  -- нельзя, иначе пропадут разделительные пробелы между текстами разных шрифтов.
                        // преобразуем символы html - >< и т.д. -- Рано еще - т.к. такие строки почему-то парсер разбивает на отдельные текстовые лексемы (&.. gt;..).
                        //     Их сначала надо как-то собирать.
                        //result  = Convert.revalidateXml ( result );
                        if ( result.length() == 0 ) result = null;
                    }
                    //*/
                }
            }
            // Иначе - это тег закрытия - при отсутствии данных - например: <object_class></object_class>

        } catch ( Exception e )        {
            Log.file.error ( "error. event = " + event, e);
            throw new  WEditException (  e, "Ошибка получения текстовых данных тега :\n", e );
        }

        if (result != null)  result = result.trim();
        
        return result;
    }

}
