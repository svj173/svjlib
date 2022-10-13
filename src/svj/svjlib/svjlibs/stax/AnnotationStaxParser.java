package svj.svjlib.svjlibs.stax;

import svj.svjlib.Log;
import svj.svjlib.WCons;
import svj.svjlib.exc.WEditException;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * <BR/>
 */
public class AnnotationStaxParser extends SvjStaxParser {

    private static final String ANNOTATION = "annotation";
    private static final String PR = "pr";
    private static final String EMPTY_LINE = "empty-line";


    public String read (XMLEventReader eventReader) throws WEditException
    {
        String          tagName, value;
        XMLEvent event;
        StartElement startElement;
        EndElement endElement;
        boolean         bWork;

        StringBuilder result = new StringBuilder();
        tagName = null;

        try
        {
            bWork   = true;

            while ( bWork )
            {
                if ( ! eventReader.hasNext() ) return result.toString();

                event = eventReader.nextEvent();

                if ( event.isStartElement() )
                {
                    startElement = event.asStartElement();
                    tagName      = startElement.getName().getLocalPart();

                    if ( tagName.equals(ANNOTATION) )
                    {
                        value    = getText ( eventReader );
                        if (value != null)  result.append(value);
                        continue;
                    }
                    if ( tagName.equals(PR) )
                    {
                        value    = getText ( eventReader );
                        if (value != null)  result.append(value);
                        continue;
                    }

                    // какие-то другие теги
                    value    = getText ( eventReader );
                    if (value != null)  result.append(value);
                }

                if ( event.isEndElement() )
                {
                    endElement  = event.asEndElement();
                    tagName     = endElement.getName().getLocalPart();

                    if ( tagName.equals(ANNOTATION) )
                    {
                        bWork = false;
                        continue;
                    }
                    if ( tagName.equals(EMPTY_LINE) )
                    {
                        result.append(WCons.END_LINE);
                        continue;
                    }
                    if ( tagName.equals(PR) )
                    {
                        result.append(WCons.END_LINE);
                    }
                }
            }

        } catch ( WEditException ex ) {
            Log.file.error ("error. tagName = " + tagName, ex);
            throw ex;
        } catch ( Exception e ) {
            Log.file.error ("err",e);
            throw new RuntimeException ( "Системная ошибка чтения аннотации книги", e );
        }
        return result.toString();
    }

}
