package svj.svjlib.svjlibs.listener;

import svj.svjlib.Log;
import svj.svjlib.exc.WEditException;
import svj.svjlib.gui.dialog.WidgetsDialog;
import svj.svjlib.gui.widget.FileWidget;
import svj.svjlib.gui.widget.StringFieldWidget;
import svj.svjlib.obj.BookTitle;
import svj.svjlib.svjlibs.obj.LoadLibInfo;
import svj.svjlib.svjlibs.stax.Fb2TitleStaxParser;
import svj.svjlib.tools.DialogTools;
import svj.svjlib.tools.DumpTools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Загрузить новую библиотеку.
 * <BR/> Эти данные хранятся в SLPar.LIBS
 * <BR/>
 */
public class LoadLibListListener implements ActionListener {

    private FileWidget dirPath;
    private StringFieldWidget myLibName;
    //private  final        String endTag = "</description>";
    private  final        String endTag = "</title-info>";
    private final LoadLibInfo libInfo = new LoadLibInfo();
    private final Collection<BookTitle> bookList = new ArrayList<>();

    private Fb2TitleStaxParser bookParser = new Fb2TitleStaxParser();

    private  String libDirPath = "none";


    @Override
    public void actionPerformed(ActionEvent event) {

        // Диалог ввода данных
        //     -- рутовый пароль - если нет прав дсотупа
        //    -- путь до директории - там будет арзличать - файл fb2, зип-файл, зип-зип файл - различеат сам, но можно ввести и тип архива библиотеки
        //    -- свое название библиотеки -- уникальное среди всех названий

        // Проверяем - есть ли у нас такой путь - т.е. массив загруженных библиотек - без их привязки к книгам
        //if (Par.LIBS != null )

        // виджеты
        // дефолтная директория - берется из конфиг-файла Редактора - директория полсденей загруженной библиотеки
        String defDir = "/home/svj/Serg/Libruks/Архивы Либрусек";
        dirPath = new FileWidget("Директория библиотеки", false, defDir);

        myLibName = new StringFieldWidget("Задайте имя библиотеки", "Моя библиотека", false);

        WidgetsDialog dialog = new WidgetsDialog("Добавить библиотеку");
        dialog.addWidget(dirPath);
        dialog.addWidget(myLibName);
        dialog.setTitleWidth(350);
        dialog.setValueWidth(250);
        dialog.pack();

        dialog.showDialog();

        if (dialog.isOK()) {

            String libName = myLibName.getValue();
            // Берем директорию
            String libDir = dirPath.getValue();
            libDirPath = libDir;

            // todo Проверяем права дсотупа к директории. Если что - запрашиваем логин-пароль на доступ.

            // Par.BOOKS - список всех книг - туда добавить если есть данные

            try {
                // сам процесс чтения
                // todo Здесь необходим бегунок с процентом рабоыт - по кол-ву файлов в библиотеке
                libInfo.setLibDir(libDir);
                Collection<BookTitle> result = processLoad(libDir);

                // todo сохранить инфу о новой библиотеке в конфиг-директории Проги

                Log.file.info("{}", DumpTools.printBookTitles(bookList));
                Log.file.info("libInfo = {}", libInfo);

                // Добавить новую библиотеку

                // Сохранить изменения в конфиг-файл

                // Добавить книги в общую кучу

                // Скинуть файлом в конфиг (Не java-обьектом, т.к. при изменениях в классе инфа пропадет)
                // - YML? - но тогад при проблемах в структуре не сможем вмешаться - лучше свой парсер

                /*
                Стуктура

                - home_dir = {User_home}/svjlib

                Конфиг файл  - svjlib_conf.xml - ?
                1) Экспорт - диреткория куда извлекать файлы
                2) Список библиотек
                 - название
                 - директория
                 - ссылка на файл-инфу

                3) файлы-инфо о книгах
                 - bibl_1.xml
                 ...

                 ??? - для Либрусек - этот файл будте много-много метров.
                 -- может, разбить на несколько?

                 При старте программы парсится конфиг-файл, и извлекаются данные о библиотеках и о книгах.
                 Данные о книгах суммируются в одном массиве
                 Возможно - сразу же создавать мапперы
                 - по жанрам      - мап в мапе - Общий жанр (Деткетивы) / Поджанры (Исторические детективы)
                 - по алфавиту авторов



                 */

            } catch (Exception e) {
                Log.l.error("libDir = " + libDir, e);
                DialogTools.showError("Ошибка загрузки библиотеки '" + libDir + "' :\n " + e.getMessage() + "\n",
                        "Ошибка загрузки");
            }
        }
    }

    private Collection<BookTitle> processLoad(String libDir) throws WEditException {

        File dirFile = new File(libDir);
        String[] list = dirFile.list();
        String msg;
        int maxCount;

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
            BookTitle bookTitle;
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
                    //bookTitle = createBookTitle(fileName);
                    //result.add(bookTitle);
                    libInfo.incBookNoneZip();
                }
                libInfo.incSourceArchive();
            }
        }

        Log.file.info("result.books.size = {}", bookList.size() );

        return bookList;
    }

    /**
     *
     * @param libDir         Директория файлов (полный путь)
     * @param zipFileName    Короткое имя файла, расположенного в указаннйо директории.
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

        // todo читаем заголовок FB2 - своим парсером
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
