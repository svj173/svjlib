package svj.svjlib.svjlibs.listener;

import svj.svjlib.Log;
import svj.svjlib.exc.WEditException;
import svj.svjlib.obj.BookTitle;
import svj.svjlib.obj.ResponseObject;
import svj.svjlib.svjlibs.obj.LoadLibInfo;
import svj.svjlib.svjlibs.stax.Fb2TitleStaxParser;

import javax.swing.*;

import java.io.File;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * <BR/>
 */
public class LoadLibWorker extends SwingWorker<ResponseObject, Void> {

    private final LoadLibInfo libInfo = new LoadLibInfo();

    private  final        String endTag = "</title-info>";
    private final Collection<BookTitle> bookList = new ArrayList<>();

    private Fb2TitleStaxParser bookParser = new Fb2TitleStaxParser();

    private  final String libDirPath;
    private  final String libName;


    public LoadLibWorker(String libDir, String libName) {
        this.libDirPath = libDir;
        this.libName = libName;
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
        processLoad(libDirPath);

        result.setObject(bookList);

        Log.file.info("libInfo = {}", libInfo);


        // Добавить новую библиотеку

        // Сохранить изменения в конфиг-файл

        // Добавить книги в общую кучу

        // Скинуть файлом в конфиг (Не java-обьектом, т.к. при изменениях в классе инфа пропадет)
        // - YML? - но тогад при проблемах в структуре не сможем вмешаться - лучше свой парсер


        return result;
    }

    private void processLoad(String libDir) throws WEditException {

        File dirFile = new File(libDir);
        String[] list = dirFile.list();
        String msg;
        int maxCount;

        libInfo.setLibDir(libDir);


        if (list != null) {
            msg = "list size = " + list.length;
            // todo
            //maxCount = list.length;
            maxCount = 1;
        } else {
            msg = "list files is Null";
            maxCount = 0;
        }

        Log.l.info(msg);

        int ic = 0;
        if (list != null) {
            for (String fileName : list) {

                //Log.file.info("- {}) {}", ic, fileName );

                ic++;
                if (ic > maxCount) break;

                if (isZip(fileName)) {
                    // todo Но это может быть и файл книги - толкьо упакованный  -- На будущее

                    // Это ЗИП-архив. Разархивируем
                    processZipArchive(libDir, fileName);
                } else {
                    // Это файл книги - вытаскиваем из него инфу
                    //BookTitle bookTitle = createBookTitle(fileName);
                    //result.add(bookTitle);
                    libInfo.incBookNoneZip();
                }
                libInfo.incSourceArchive();
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
        long size, fullSize;

        String fullZipName = libDir + File.separator + zipFileName;

        try {
            ZipFile zf = new ZipFile(fullZipName);

            Enumeration en = zf.entries();
            while(en.hasMoreElements())
            {
                // запись в зип-архиве. по идее - зип-файл книги
                zipEntry = (ZipEntry) en.nextElement();
                size = zipEntry.getCompressedSize();
                fullSize = zipEntry.getSize();     // то же что и getCompressedSize

                //Log.file.info("size = {}; fullSize = {}; zipEntry = {}", size, fullSize, zipEntry);

                // Обработка книг
                if (isZip(zipEntry.getName())) {
                    // книга в ZIP архиве
                    libInfo.incSourceBook();
                    processZipBook(zf, zipEntry, zipFileName);
                } else {
                    // Не ЗИП архив - пока пропускаем
                    libInfo.incBookNoneZip();
                }
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

                int istart = text.indexOf("encoding=\"");
                istart = istart + "encoding=\"".length();
                int iend = text.indexOf("\"", istart + 1);
                //Log.file.info("- istart = {}; iend = {}", istart, iend);
                if (iend - istart > 20) {
                    Log.file.info("++++++ Code format error = {}", iend - istart);
                    libInfo.incBadCodeText();
                } else {
                    String code = text.substring(istart, iend);
                    //Log.file.info("        Code format = '{}'", code);
                    text = new String(bytes, 0, offset, code);
                    //Log.file.info("FB2 title = {}", text);
                    //
                    BookTitle bookTitle = parseFb2(text, code);
                    if (bookTitle != null) {
                        // директория библиотеки
                        bookTitle.setArchiveDirName(libDirPath);
                        // имя зип-файла, содержащего в себе зип-файлы книг
                        bookTitle.setArchiveName(zipFileName);
                        // имя зип-файла архива книги
                        bookTitle.setFileName(zipEntry.getName());
                        bookTitle.setBookSize(entry.getSize() * 3);
                        bookList.add(bookTitle);
                    }
                }

                zin.closeEntry();
            }
        } catch (Exception e) {
            libInfo.incParseError();
            Log.file.error("Error in zipEntry = '" + zipEntry + "'; entryName = " + name, e);

        //} finally {
            //Utils.close(zin);
        }

    }

    /**
     *
     * @param text  Кусок заголовка FB2 до  заключительного тега "/description"
     * @return  Инфа о книге - титл, автор, анотация, язык книги, серия и пр.
     */
    private BookTitle parseFb2(String text, String code) {
        BookTitle result = null;

        // читаем заголовок FB2 - своим парсером
        try {
            result = bookParser.read(text, code);
            libInfo.incBook();
        } catch (WEditException e) {
            Log.file.error("text = " + text, e);
            libInfo.incParseError();
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
