package svj.svjlib.svjlibs.stax;

import svj.svjlib.Log;
import svj.svjlib.exc.WEditException;
import svj.svjlib.obj.BookTitle;
import svj.svjlib.svjlibs.SLCons;
import svj.svjlib.svjlibs.obj.Author;
import svj.svjlib.tools.Convert;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Парсер заголовка титла книги формата FB2.
 * Сюда подставляется кусок текста, взятый из зип-файла книги в формате FB2.
 * <BR/>
 */
public class Fb2TitleStaxParser extends SvjStaxParser {

    private static final QName NAME             = new QName("name");
    private static final QName NUMBER             = new QName("number");

    private static final String DESCRIPTION = "description";
    private static final String BOOK_TITLE = "book-title";
    private static final String TITLE_INFO = "title-info";
    private static final String GENRE = "genre";
    private static final String LANG = "lang";
    private static final String SEQUENCE = "sequence";
    private static final String ANNOTATION = "annotation";
    private static final String AUTHOR = "author";


    /**
     *
     * @param text
     * @param code  Кодировка текста. Может быть null
     * @return
     * @throws WEditException
     */
    public BookTitle read (String text, String code) throws WEditException
    {
        String          tagName, value;
        XMLEvent event;
        StartElement startElement;
        Attribute attr;
        Author author;
        EndElement endElement;
        boolean         bWork;
        XMLEventReader eventReader;
        BookTitle result = new BookTitle();

        AuthorStaxParser authorParser = new AuthorStaxParser();
        AnnotationStaxParser annotationParser = new AnnotationStaxParser();

        tagName = null;

        try
        {
            InputStream is;

            if (code == null)
                is = new ByteArrayInputStream(text.getBytes());
            else
                is = new ByteArrayInputStream(text.getBytes(code));

            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            eventReader  = inputFactory.createXMLEventReader(is);

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

                    if ( tagName.equals(SEQUENCE) )
                    {
                        // Серия
                        attr    = startElement.getAttributeByName ( NAME );
                        if ( attr == null )
                            throw new WEditException ("Отсутствует имя Серии.");
                        result.setSerialName(attr.getValue());
                        attr    = startElement.getAttributeByName ( NUMBER );
                        if ( attr != null ) {
                            result.setSerialIndex(Convert.getInt(attr.getValue(), 0));
                        }

                        //attrValue    = getText ( eventReader );

                        continue;
                    }

                    if ( tagName.equals(LANG) )
                    {
                        // Язык книги
                        value    = getText ( eventReader );
                        result.setLang(value);

                        continue;
                    }

                    if ( tagName.equals(ANNOTATION) )
                    {
                        // Аннотация   - AnnotationStaxParser
                        value = annotationParser.read(eventReader);
                        result.setAnnotation(value);
                        //Log.file.info("ANNOTATION = '{}'", value);
                        continue;
                    }

                    if ( tagName.equals(BOOK_TITLE) )
                    {
                        // Название книги
                        value    = getText ( eventReader );
                        result.setBookTitle(value);

                        continue;
                    }

                    if ( tagName.equals(GENRE) )
                    {
                        // Жанр книги. Могут быть несколько
                        value    = getText ( eventReader );
                        result.addGenre(value);

                        // привязать книгу к мапу жанров
                        SLCons.BOOKS_MANAGERS.addBookToGenreMap(value, result);

                        continue;
                    }

                    if ( tagName.equals(AUTHOR) )
                    {
                        // Автор книги. Могут быть несколько
                        author = authorParser.read(eventReader);
                        result.addAuthor(author);

                        continue;
                    }

                    //throw new WEditException( null, "Ошибка загрузки атрибута книги '", bookContent.getName(), "' :\n Неизвестное имя стартового тега '", tagName, "'." );
                }

                if ( event.isEndElement() )
                {
                    endElement  = event.asEndElement();
                    tagName     = endElement.getName().getLocalPart();

                    if ( tagName.equals(TITLE_INFO) )
                    {
                        // конец рабоыт парсера
                        bWork = false;
                    }
                }
            }

        } catch ( WEditException ex ) {
            Log.file.error ( "error. tagName = " + tagName, ex);
            throw ex;
        } catch ( Exception e ) {
            Log.file.error ("err",e);
            throw new RuntimeException ( "Системная ошибка чтения заголовка книги", e );
        }
        return result;
    }

}
