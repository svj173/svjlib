package svj.svjlib.svjlibs.stax;

import svj.svjlib.Log;
import svj.svjlib.exc.WEditException;
import svj.svjlib.svjlibs.obj.Author;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.*;

/**
 * Парсит блок автора в xml-текстовом куске заголовка книги формата FB2.
 * <BR/>
 */
public class AuthorStaxParser extends SvjStaxParser {

    private static final String AUTHOR = "author";
    private static final String FIRST = "first-name";
    private static final String MIDDLE = "middle-name";
    private static final String LAST = "last-name";


    public Author read (XMLEventReader eventReader) throws WEditException
    {
        String          tagName, value;
        XMLEvent        event;
        StartElement    startElement;
        EndElement      endElement;
        boolean         bWork;

        Author result = new Author();
        tagName = null;

        try
        {
            bWork   = true;

            while ( bWork )
            {
                if ( ! eventReader.hasNext() ) return result;

                event = eventReader.nextEvent();

                if ( event.isStartElement() )
                {
                    startElement = event.asStartElement();
                    tagName      = startElement.getName().getLocalPart();
                    //Log.file.debug ( "----- tagName = {}", tagName );

                    if ( tagName.equals(FIRST) )
                    {
                        value    = getText ( eventReader );
                        if (value != null)  result.setFirstName(replaceName(value));
                        continue;
                    }

                    if ( tagName.equals(MIDDLE) )
                    {
                        value    = getText ( eventReader );
                        if (value != null)  result.setMiddleName(replaceName(value));
                        continue;
                    }

                    if ( tagName.equals(LAST) )
                    {
                        value    = getText ( eventReader );
                        if (value != null)  result.setLastName(replaceName(value));
                        continue;
                    }

                }

                if ( event.isEndElement() )
                {
                    endElement  = event.asEndElement();
                    tagName     = endElement.getName().getLocalPart();

                    if ( tagName.equals(AUTHOR) )
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
