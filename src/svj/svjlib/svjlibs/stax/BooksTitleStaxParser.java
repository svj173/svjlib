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
public class BooksTitleStaxParser extends SvjStaxParser {

    // todo
    private static final String BOOKS = "books";
    private static final String BOOK = "book";
    private static final String SIZE = "size";

    private static final QName FIRST    = new QName("first");
    private static final QName MIDDLE  = new QName("middle");
    private static final QName LAST  = new QName("last");


    /*
     Пример файла:

  <book>
    <size>3603999</size>
    <lang>ru</lang>
    <serialIndex>2</serialIndex>
    <serialName>Арифмоман</serialName>
    <libId>1665653212679</libId>
    <annotation>День Костяного Тигра 1514 года по календарю Парифата. По земному… вероятно, 9 декабря 2016 года. Не уверен точно.
      ...</annotation>
    <archiveName>fb2-610371-610471.zip</archiveName>
    <bookTitle>Арифмоман. В небесах</bookTitle>
    <fileName>610470.zip</fileName>
    <author first='Александр' middle='Валентинович' last='Рудазов' />
    <author first='Петр' last='Иванов' />
    <genre>sf_fantasy;sf_history</genre>
  </book>

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
