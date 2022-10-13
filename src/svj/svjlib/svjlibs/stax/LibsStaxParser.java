package svj.svjlib.svjlibs.stax;

import svj.svjlib.Log;
import svj.svjlib.exc.WEditException;
import svj.svjlib.svjlibs.obj.LibInfo;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Парсим файл библиотек
 *
 * <BR/>
 */
public class LibsStaxParser extends SvjStaxParser {

    private static final String LIBS = "libs";
    private static final String LIB = "lib";

    private static final QName NAME    = new QName("name");
    private static final QName ID  = new QName("id");
    private static final QName LIB_DIR  = new QName("libDir");


    /*
     Пример файла:
<?xml version="1.0" encoding="UTF-8"?>
<libs>
<lib id='1665643397991' name='Моя библиотека' libDir='/home/svj/Serg/Libruks/Архивы Либрусек' />
</libs>

     */

    /**
     *
     * @param fileName
     * @return
     * @throws WEditException
     */
    public Collection<LibInfo> read (String fileName, String code) throws WEditException
    {
        String          tagName, id, name, libDir;
        XMLEvent        event;
        StartElement    startElement;
        EndElement      endElement;
        Attribute       attr;
        boolean         bWork;
        LibInfo         libInfo;
        XMLEventReader eventReader;


        Collection<LibInfo> result = new ArrayList<>();
        tagName = null;

        try
        {
            bWork   = true;

            InputStream is = new FileInputStream(fileName);

            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            eventReader  = inputFactory.createXMLEventReader(is, code);


            while ( bWork )
            {
                if ( ! eventReader.hasNext() ) return result;

                event = eventReader.nextEvent();

                if ( event.isStartElement() )
                {
                    startElement = event.asStartElement();
                    tagName      = startElement.getName().getLocalPart();
                    //Log.file.debug ( "----- tagName = {}", tagName );

                    if ( tagName.equals(LIB) )
                    {
                        // ID
                        attr    = startElement.getAttributeByName ( ID );
                        if ( attr == null )
                            throw new WEditException ("Отсутствует ID библиотеки.");
                        id = attr.getValue();
                        // name
                        attr    = startElement.getAttributeByName ( NAME );
                        if ( attr == null )
                            throw new WEditException ("Отсутствует имя библиотеки.");
                        name = attr.getValue();
                        attr    = startElement.getAttributeByName ( LIB_DIR );
                        if ( attr == null ) {
                            throw new WEditException ("Отсутствует директория библиотеки.");
                        }
                        libDir = attr.getValue();

                        libInfo = new LibInfo(id, libDir, name);
                        result.add(libInfo);

                        continue;
                    }

                }

                if ( event.isEndElement() )
                {
                    endElement  = event.asEndElement();
                    tagName     = endElement.getName().getLocalPart();

                    if ( tagName.equals(LIBS) )
                    {
                        bWork = false;
                    }
                }
            }

        } catch ( WEditException ex ) {
            Log.file.error ("error. tagName = " + tagName, ex);
            throw ex;
        } catch ( Exception e ) {
            Log.file.error ("err",e);
            throw new RuntimeException ( "Системная ошибка чтения автора книги", e );
        }
        return result;
    }

}
