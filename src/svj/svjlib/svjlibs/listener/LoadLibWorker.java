package svj.svjlib.svjlibs.listener;

import svj.svjlib.Log;
import svj.svjlib.exc.WEditException;
import svj.svjlib.obj.BookTitle;
import svj.svjlib.obj.ResponseObject;
import svj.svjlib.svjlibs.SLCons;
import svj.svjlib.svjlibs.obj.LibInfo;
import svj.svjlib.svjlibs.obj.LoadLibInfo;
import svj.svjlib.svjlibs.stax.Fb2TitleStaxParser;

import javax.swing.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Загрузка данных по новой библиотеке. Берем заголовки зипованных книг, парсим их и заносим в свой каталог.
 * <BR/>
 */
public class LoadLibWorker extends SwingWorker<ResponseObject, Void> {

    private final LoadLibInfo loadLibInfo = new LoadLibInfo();

    private  final        String endTag = "</title-info>";
    private final Collection<BookTitle> bookList = new ArrayList<>();

    private Fb2TitleStaxParser bookParser = new Fb2TitleStaxParser();

    //private  final String libDirPath;
    private  final LibInfo libInfo;

    private     JLabel totalZipValue, loadZipValue, totalBooksValue;
    private int countBooks = 0;



    public LoadLibWorker(LibInfo libInfo, JLabel totalZipValue, JLabel loadZipValue, JLabel totalBooksValue)
    {
        //this.libDirPath = libInfo.getLibDir();
        this.libInfo = libInfo;

        this.totalZipValue = totalZipValue;
        this.loadZipValue = loadZipValue;
        this.totalBooksValue = totalBooksValue;
    }

    /**
     *
     * @return
     * @throws Exception  При ошибках WaitingObjectDialog сам сформирует правильный ResponseObject.
     */
    @Override
    protected ResponseObject doInBackground() throws Exception {

        ResponseObject result = new ResponseObject();

        // парсим все файлы новой библиотеки
        processLoad(libInfo.getLibDir());

        result.setObject(bookList);

        result.setObject2(loadLibInfo);

        Log.file.info("loadLibInfo = {}", loadLibInfo);
        Log.file.info("libInfo = {}", libInfo);
        Log.file.info("wrongGenre = {}", SLCons.BOOKS_MANAGERS.getWrongGenre());


        // Добавить новую библиотеку - libInfo
        SLCons.LIBS_MANAGER.addLib(libInfo);

        // Сохранить изменения в конфиг-файл - libs.xml
        SLCons.LIBS_MANAGER.saveFile();

        // Добавить книги в общую кучу - BooksManager
        SLCons.BOOKS_MANAGERS.addBooks(bookList);

        // Скинуть книги файлом в конфиг (Не java-обьектом, т.к. при изменениях в классе инфа пропадет)
        // - YML? - но тогад при проблемах в структуре не сможем вмешаться - лучше свой парсер
        SLCons.BOOKS_MANAGERS.saveFile(bookList, libInfo.getId());


        return result;
    }

    private void processLoad(String libDir) throws WEditException {

        File dirFile = new File(libDir);
        String[] list = dirFile.list();
        String msg;
        int maxCount;

        loadLibInfo.setLibDir(libDir);


        if (list != null) {
            msg = "list size = " + list.length;
            //todo
            maxCount = list.length;    // 2 часа будет парсится библиотека
            //maxCount = 2;
        } else {
            msg = "list files is Null";
            maxCount = 0;
        }

        totalZipValue.setText(Integer.toString(maxCount));

        Log.l.info(msg);

        int ic = 0;
        if (list != null) {
            // перебор зип-архивов, находящихся в директории
            // - каждый зип-архив содержит в себе зип-файлы книг. один зип-файл - одна книга.
            for (String fileName : list) {

                //Log.file.info("- {}) {}", ic, fileName );

                ic++;
                if (ic > maxCount) break;
                //if (countBooks > 20) break;

                if (isZip(fileName)) {
                    // todo Но это может быть и файл книги - толкьо упакованный
                    //  -- На будущее, либо задавать тип библиотеки - myrulib, flibusta...

                    // Это ЗИП-архив. Разархивируем
                    processZipArchive(libDir, fileName);
                } else {
                    // Это файл книги - вытаскиваем из него инфу
                    //BookTitle bookTitle = createBookTitle(fileName);
                    //result.add(bookTitle);
                    loadLibInfo.incBookNoneZip();
                }
                loadLibInfo.incSourceArchive();
                loadZipValue.setText(Integer.toString(ic));
            }
        }

        Log.file.info("result.books.size = {}", bookList.size() );
    }

    /**
     * Обрабатываем зип-архив, который содержит в себе зип-файлы книг (один зип - одна книга)
     * @param libDir         Директория, в которой лежат зип-файлы (полный путь)
     * @param zipFileName    Короткое имя файла, расположенного в указанной директории.
     * @throws WEditException
     */
    private void processZipArchive(String libDir, String zipFileName) throws WEditException {

        ZipEntry zipEntry;
        //long size, fullSize;

        String fullZipName = libDir + File.separator + zipFileName;

        try {
            ZipFile zf = new ZipFile(fullZipName);

            Enumeration en = zf.entries();
            // Перебор зип-файлов в зип-архиве.
            // - каждый зип-файл - это одна книга
            while(en.hasMoreElements())
            {
                // запись в зип-архиве. по идее - зип-файл книги
                zipEntry = (ZipEntry) en.nextElement();
                //size = zipEntry.getCompressedSize();
                //fullSize = zipEntry.getSize();     // то же что и getCompressedSize

                //Log.file.info("size = {}; fullSize = {}; zipEntry = {}", size, fullSize, zipEntry);

                // Обработка книг
                if (isZip(zipEntry.getName())) {
                    // книга в ZIP архиве
                    loadLibInfo.incSourceBook();
                    processZipBook(zf, zipEntry, zipFileName);
                } else {
                    // Не ЗИП архив - пока пропускаем
                    loadLibInfo.incBookNoneZip();
                }

                countBooks++;
                //if (countBooks > 20) break;
                totalBooksValue.setText(Integer.toString(countBooks));
            }
        } catch (Exception e) {
            Log.file.error("fullZipName = " + fullZipName, e);
            throw new WEditException("Error read zipFile = " + fullZipName, e);
        }

    }

    private void processZipBook(ZipFile zf, ZipEntry zipEntry, String zipFileName) {

        ZipInputStream zin = null;
        ZipEntry entry;
        String name = null;

        try {
            zin = new ZipInputStream(zf.getInputStream(zipEntry), UTF_8);

            // в архиве - толкьо одна книга. но все равно делаем цикл
            while ((entry = zin.getNextEntry()) != null) {

                name = entry.getName(); // получим короткое название файла
                //Log.file.info("-- zipFileName = {}", zipFileName);
                //Log.file.info("-- zipEntry = {}", zipEntry);
                //Log.file.info("-- entry = {}", entry);

                // распаковка - только заголовок FB2

                int bufSize = 80000;
                byte[] bytes = new byte[bufSize + 10];

                int offset = 0;
                int numRead = 0;

                //while (offset < bytes.length && (numRead=zin.read(bytes, offset, bytes.length-offset)) >= 0)
                while (offset < bytes.length && (numRead = zin.read(bytes, offset, 1024)) >= 0) {
                    offset += numRead;
                    String str = new String(bytes);
                    if (str.contains(endTag))
                        break;
                }
                //Log.file.info("offset = {}", offset);
                //Log.file.info("bytes.size = {}", bytes.length);
                String text = new String(bytes, 0, offset);
                //Log.file.info("text.size = {}", bytes.length);

                // выделяем кодировку текста - может отсутствовать
                String textCode = getTextCode(text);

                //Log.file.info("        Code format = '{}'", code);
                if (textCode != null) {
                    try {
                        // если кодирвока задана - переделываем весь еткст заново -  в укзаннйо кодировке
                        text = new String(bytes, 0, offset, textCode);
                    } catch (UnsupportedEncodingException e) {
                        // здесь вылетает
                        // java.io.UnsupportedEncodingException: rsion=
                        // - Неверная кодировка символов = 'rsion='
                        Log.file.error("new String(bytes, 0, offset, code) Error. offset = " + offset
                                + "; textCode = '" + textCode + "'");
                        throw e;
                    }
                }

                // некоторые глобальные изменения в тексте
                text = handleText(text);

                //Log.file.info("FB2 title = {}", text);
                //
                BookTitle bookTitle = parseFb2(text, textCode);
                if (bookTitle != null) {
                    // директория библиотеки
                    bookTitle.setLibId(libInfo.getId());
                    // имя зип-архива, содержащего в себе зип-файлы книг
                    bookTitle.setArchiveName(zipFileName);
                    // имя зип-файла этой книги
                    bookTitle.setFileName(zipEntry.getName());
                    bookTitle.setBookSize(entry.getSize() * 3);
                    bookList.add(bookTitle);
                }

                zin.closeEntry();
            }
        } catch (Exception e) {
            loadLibInfo.incParseError();
            Log.file.error("Error in zip-archive = '" + zipFileName + "'; zipFile = " + name, e);

        //} finally {
            //Utils.close(zin);
        }

    }

    private String handleText(String text) {
        // скобочки внутри Аннотаций
        text = text.replace('«', '"');
        text = text.replace('»', '"');
        text = text.replace("<<<</", " ");

        // фрагменты внутри названий книг
        text = text.replace("&lt;", "\'");
        text = text.replace("&gt;", "\'");
        return text;
    }

    private String getTextCode(String text) {

        String code = null;

        int istart = text.indexOf("encoding=\"");
        if (istart < 0) {
            // нет описания кодировки
        } else {

            istart = istart + "encoding=\"".length();
            int iend = text.indexOf("\"", istart + 1);
            //Log.file.info("- istart = {}; iend = {}", istart, iend);

            if (iend - istart > 20) {
                //Log.file.info("++++++ Code format error = {}", iend - istart);
                loadLibInfo.incBadCodeText();
            } else {
                code = text.substring(istart, iend);
            }
        }

        return code;
    }

    /**
     *
     * @param text  Кусок заголовка FB2 до  заключительного тега "/description"
     * @param code  Кодировка текста. Может быть null
     * @return  Инфа о книге - титл, автор, анотация, язык книги, серия и пр.
     */
    private BookTitle parseFb2(String text, String code) {
        BookTitle result = null;

        // читаем заголовок FB2 - своим парсером
        try {
            result = bookParser.read(text, code);
            loadLibInfo.incBook();
        } catch (WEditException e) {
            Log.file.error("text = " + text, e);
            loadLibInfo.incParseError();
        }

        return result;
    }

    private boolean isZip(String name) {
        return (name.endsWith(".zip") || name.endsWith(".ZIP"));
    }

    /**
     *
     * @param fileName  Это НЕ zip-файл книги, а сама книга формата FB2. Другое пока не реализовано.
     * @return   Инфа о книге.
     */
    private BookTitle createBookTitle(String fileName) {
        BookTitle result = new BookTitle();

        // читаем заголовок FB2 - своим парсером

        return result;
    }

}
