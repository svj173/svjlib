package svj.svjlib.svjlibs.stax;

import svj.svjlib.Log;
import svj.svjlib.exc.WEditException;
import svj.svjlib.obj.BookTitle;
import svj.svjlib.svjlibs.SLCons;
import svj.svjlib.svjlibs.obj.Author;

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
    private static final String LANG = "lang";
    private static final String SERIAL_INDEX = "serialIndex";
    private static final String SERIAL_NAME = "serialName";
    private static final String LIB_ID = "libId";
    private static final String ANNOTATION = "annotation";
    private static final String ARCHIVE_NAME = "archiveName";
    private static final String BOOK_TITLE = "bookTitle";
    private static final String FILE_NAME = "fileName";
    private static final String AUTHOR = "author";
    private static final String GENRE = "genre";

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
    public Collection<BookTitle> read (String fileName, String code) throws WEditException
    {
        BookTitle       bookTitle = new BookTitle();;
        Author          author;
        String          tagName, str, name, libDir;
        XMLEvent        event;
        StartElement    startElement;
        EndElement      endElement;
        Attribute       attr;
        boolean         bWork;
        XMLEventReader  eventReader;


        Collection<BookTitle> result = new ArrayList<>();
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

                    switch (tagName) {
                        case BOOK:
                            bookTitle = new BookTitle();
                            break;

                        case SIZE:
                            bookTitle.setBookSize(getText ( eventReader ));
                            break;

                        case LANG:
                            bookTitle.setLang(getText ( eventReader ));
                            break;

                        case SERIAL_INDEX:
                            bookTitle.setSerialIndex(getText ( eventReader ));
                            break;

                        case SERIAL_NAME:
                            bookTitle.setSerialName(getText ( eventReader ));
                            break;

                        case ANNOTATION:
                            bookTitle.setAnnotation(getText ( eventReader ));
                            break;

                        case ARCHIVE_NAME:
                            bookTitle.setArchiveName(getText ( eventReader ));
                            break;

                        case BOOK_TITLE:
                            bookTitle.setBookTitle(getText ( eventReader ));
                            break;

                        case FILE_NAME:
                            bookTitle.setFileName(getText ( eventReader ));
                            break;

                        case GENRE:
                            str = getText ( eventReader );
                            if (str != null) {
                                String[] sg = str.split(SLCons.GENRE_SEP);
                                for (String s1 : sg) {
                                    bookTitle.addGenre(s1);
                                }
                            }
                            break;

                        case LIB_ID:
                            str = getText ( eventReader );
                            bookTitle.setLibId(str);
                            if (bookTitle.getLibId() == 0) {
                                Log.file.error("Str to Long error for str '{}'", str);
                            }
                            break;

                        case AUTHOR:
                            author = new Author();
                            attr    = startElement.getAttributeByName ( FIRST );
                            if ( attr != null ) author.setFirstName(attr.getValue());
                            attr    = startElement.getAttributeByName ( MIDDLE );
                            if ( attr != null ) author.setMiddleName(attr.getValue());
                            attr    = startElement.getAttributeByName ( LAST );
                            if ( attr != null ) author.setLastName(attr.getValue());
                            bookTitle.addAuthor(author);
                            break;

                        default:
                            Log.file.error("Unknown tag '{}' when parse '{}'", tagName, fileName);
                    }

                    result.add(bookTitle);
                }

                if ( event.isEndElement() )
                {
                    endElement  = event.asEndElement();
                    tagName     = endElement.getName().getLocalPart();

                    if ( tagName.equals(BOOKS) )
                    {
                        bWork = false;
                    }
                }
            }

        //} catch ( WEditException ex ) {
        //    throw ex;
        } catch ( Exception e ) {
            Log.file.error ("error. tagName = " + tagName + "; current bookTitle = " + bookTitle, e);
            throw new WEditException ( "Системная ошибка чтения файла информации о загруженных книгах (" + fileName + ")", e );
        }
        return result;
    }

}
