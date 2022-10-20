package svj.svjlib.tools;


import svj.svjlib.WCons;
import svj.svjlib.obj.BookTitle;

import javax.swing.text.AttributeSet;

import java.util.*;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 30.05.2012 14:29:00
 */
public class DumpTools
{
    /** Распечать stack trace текущего места. Т.е. выводим полное дерево java-классов, которые были вызваны. */
    public static StringBuilder printCurrentStackTrace() {
        // Ошибка - это вызов не из AWT потока. Выяснить - откуда пришел вызов (распечатать stacktrace)
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StringBuilder message = new StringBuilder(128);

        // StackTraceElement с индексом 0 — это вершина стека. Отсальные элементы - дальнейшие вызовы.
        // Состав элемента:
        // - getClassName — это имя класса, из которого делался или делается вызов.
        // - getMethodName — это имя метода, из которого делается вызов.

        if (stackTraceElements.length > 0)
        {
            for (StackTraceElement trace : stackTraceElements)
            {
                message.append ( '\t' );
                // Выводит: класс, метод, номер линии, ссылки на файл, содержащий данный класс и т.д.
                message.append ( trace );
                message.append ( WCons.END_LINE );
            }
            message.append(WCons.END_LINE);
        }
        return message;
    }

    /**
     * Перечислить содержимое  массива через символ CH.
     * В конце строки символа CH - нет
     */
    public static String printArray (Object[] array, char ch )
    {
        String result = "Null";
        StringBuilder sb = new StringBuilder( 128 );
        if ( ( array == null ) || ( array.length == 0 ) ) return result;

        for ( Object anArray : array )
        {
            //result = result + array[i] + ch + " ";
            sb.append ( anArray );
            sb.append ( ch );
            sb.append ( " " );
        }
        // удалить последнюю запятую (символ CH)
        result = sb.toString();
        result = result.substring ( 0, result.length() - 2 );
        return result;
    }

    public static String printBytes (byte[] array, char ch )
    {
        String result = "Null";
        StringBuilder sb = new StringBuilder( 128 );
        if ( ( array == null ) || ( array.length == 0 ) ) return result;

        for ( byte anArray : array )
        {
            //result = result + array[i] + ch + " ";
            sb.append ( anArray );
            sb.append ( ch );
        }
        // удалить последнюю запятую (символ CH)
        result = sb.toString();
        //result = result.substring ( 0, result.length() - 2 );
        return result;
    }

    public static String printMap (Map array, String separator )
    {
        StringBuilder sb;

        sb = new StringBuilder( 128 );
        if ( array == null  ) return "Null";
        if ( array.isEmpty()  ) return "Empty";

        if (separator == null)  separator = "\n";

        for ( Object key : array.keySet() )
        {
            sb.append ( separator );
            sb.append ( key );
            sb.append ( "\t: " );
            sb.append ( array.get ( key ) );
        }
        return sb.toString();
    }

    public static String printCollection (Collection array )
    {
        String result;
        StringBuilder sb = new StringBuilder( 128 );
        if ( array == null  ) return "Null";
        if ( array.isEmpty()  ) return "Empty";

        int ic = 1;
        for ( Object anArray : array )
        {
            sb.append ( WCons.NEW_LINE );
            sb.append ( "- " );
            sb.append ( ic );
            sb.append ( "). " );
            sb.append ( anArray );
            ic++;
        }
        return sb.toString();
    }

    public static String printBookTitles (Collection<BookTitle> array )
    {
        StringBuilder sb = new StringBuilder( 128 );
        if ( array == null  ) return "Null";
        if ( array.isEmpty()  ) return "Empty";

        int ic = 1;
        for ( BookTitle anArray : array )
        {
            sb.append ( WCons.NEW_LINE );
            sb.append ( "- " );
            sb.append ( ic );
            sb.append ( "). " );
            sb.append ( anArray.info() );
            ic++;
        }
        return sb.toString();
    }

    public static String printCollection (Collection array, char ch )
    {
        String result;
        StringBuilder sb = new StringBuilder( 128 );
        if ( array == null  ) return "Null";
        if ( array.isEmpty()  ) return "Empty";

        for ( Object anArray : array )
        {
            //result = result + array[i] + ch + " ";
            sb.append ( anArray );
            sb.append ( ch );
            sb.append ( " " );
        }
        // удалить последнюю запятую (символ CH)
        result = sb.toString();
        result = result.substring ( 0, result.length() - 2 );
        return result;
    }

    public static String printCollectionAsClass (Collection array, char ch )
    {
        String result;
        StringBuilder sb = new StringBuilder( 128 );
        if ( array == null  ) return "Null";
        if ( array.isEmpty()  ) return "Empty";

        for ( Object anArray : array )
        {
            //result = result + array[i] + ch + " ";
            sb.append ( anArray.getClass().getSimpleName() );
            sb.append ( '/' );
            sb.append ( anArray.hashCode() );
            sb.append ( ch );
            sb.append ( " " );
        }
        // удалить последнюю запятую (символ CH)
        result = sb.toString();
        result = result.substring ( 0, result.length() - 2 );
        return result;
    }


    public static StringBuilder printAttributeSet (AttributeSet attr )
    {
        StringBuilder result;
        Enumeration en;
        Object name, value;

        result = new StringBuilder(512);
        result.append ( "\n[ AttributeSet :" );

        if ( attr == null )
        {
            result.append ( "\n NULL" );
        }
        else
        {
            en  = attr.getAttributeNames ();
            while ( en.hasMoreElements() )
            {
                name    = en.nextElement();
                //Log.l.debug ( "attr = ", name );
                //Log.l.debug ( "attr class = ", name.getClass().getName() );  // StyleConstants
                result.append ( "\n  " );
                result.append ( name );
                result.append ( " = " );
                value   = attr.getAttribute(name);
                result.append ( value );
                result.append ( "\t\t " );
                result.append ( name.getClass().getSimpleName() );
                result.append ( '/' );
                if ( value == null )
                    result.append ( "Null" );
                else
                    result.append ( value.getClass().getSimpleName() );
            }
        }
        result.append ( "\n ]" );
        return result;
    }

    /* Распечать строку в виде кода символов. */
    public static StringBuilder printString (String str )
    {
        StringBuilder result;
        byte[]          bytes;

        result  = new StringBuilder(512);

        result.append ( "\nString: '");
        if ( str == null )
        {
            result.append("NULL");
        }
        else
        {
            result.append ( str );
            result.append ( "'\n" );

            result.append ( "size : " );
            result.append ( str.length() );
            result.append ( "'\n" );

            bytes   = str.getBytes();

            for ( byte aByte : bytes )
            {
                result.append ( aByte );
                result.append ( ", " );
            }
        }

        return result;
    }

    public static boolean hasLittelCode (String str, int code )
    {
        boolean result;
        byte[]  bytes;

        result = false;
        if ( str != null )
        {
            bytes   = str.getBytes();

            for ( byte aByte : bytes )
            {
                if ( aByte <= code )
                {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

}
