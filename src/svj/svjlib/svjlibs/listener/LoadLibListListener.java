package svj.svjlib.svjlibs.listener;

import svj.svjlib.Log;
import svj.svjlib.exc.WEditException;
import svj.svjlib.gui.dialog.WidgetsDialog;
import svj.svjlib.gui.widget.FileWidget;
import svj.svjlib.gui.widget.StringFieldWidget;
import svj.svjlib.obj.BookTitle;
import svj.svjlib.tools.DialogTools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Загрузить новую библиотеку.
 * <BR/> Эти данные хранятся в SLPar.LIBS
 * <BR/>
 */
public class LoadLibListListener implements ActionListener {

    private FileWidget dirPath;
    private StringFieldWidget myLibName;

    @Override
    public void actionPerformed(ActionEvent event) {
        // todo

        // Диалог ввода данных
        //     -- рутовый пароль - если нет прав дсотупа
        //    -- путь до директории - там будет арзличать - файл fb2, зип-файл, зип-зип файл - различеат сам, но можно ввести и тип архива библиотеки
        //    -- свое название библиотеки -- уникальное среди всех названий

        // Проверяем - есть ли у нас такой путь - т.е. массив загруженных библиотек - без их привязки к книгам
        //if (Par.LIBS != null )

        //String msg = "<html><body><h1>Не реализовано</h1></body></html>";

        //DialogTools.showHtml("Добавить библиотеку", msg);

        // виджеты
        // StringFieldWidget (String titleName, String value, boolean hasEmpty )
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

            // todo Проверяем права дсотупа к директории. Если что - запрашиваем логин-пароль на доступ.

            // Par.BOOKS - список всех книг - туда добавить если есть данные

            try {
                // сам процесс чтения
                // todo Здесь необходим бегунок с процентом рабоыт - по кол-ву файлов в библиотеке
                Collection<BookTitle> result = processLoad(libDir);

                // todo сохранить инфу о новой библиотеке в конфиг-директории Проги

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
        Collection<BookTitle> result = new ArrayList<>();

        if (list != null) {
            msg = "list size = " + list.length;
            //maxCount = list.length;
            maxCount = 2;
        } else {
            msg = "list files is Null";
            maxCount = 0;
        }

        Log.l.info(msg);

        int ic = 0;
        if (list != null) {
            BookTitle bookTitle;
            for (String fileName : list) {

                Log.file.info("- {}) {}", ic, fileName );


                ic++;
                if (ic > maxCount) break;

                if (fileName.endsWith(".zip") || fileName.endsWith(".ZIP")) {
                    // todo Но это может быть и файл книги - толкьо упакованный  -- На будущее

                    // Это ЗИП-архив. Разархивируем
                    processZipArchive(libDir, fileName);
                } else {
                    // Это файл книги - вытаскиваем из него инфу
                    bookTitle = createBookTitle(fileName);
                    result.add(bookTitle);
                }
            }
        }

        Log.file.info("result = {}", result.size() );

        //throw new RuntimeException("Files list: " + msg);

        return result;
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
        ZipInputStream zin = null;
        String endTag = "</description>";

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

                Log.file.info("size = {}; fullSize = {}; zipEntry = {}", size, fullSize, zipEntry);

                //try(ZipInputStream zin = new ZipInputStream(zipEntry.))
                //{
                // по дефолту кодирвока - UTF-8
                zin = new ZipInputStream(zf.getInputStream(zipEntry));
                    ZipEntry entry;
                    String name;
                    while((entry=zin.getNextEntry())!=null){

                        name = entry.getName(); // получим название файла
                        //size=entry.getSize();  // получим его размер в байтах
                        //System.out.printf("File name: %s \t File size: %d \n", name, size);

                        // распаковка - только заголовок FB2
                        int ic = 0;
                        //StringBuilder sb = new StringBuilder(100);
                        // todo Почему-то не берутся русские буквы. Надо конвертить и выход?
                        StringBuilder str = new StringBuilder();
                        for (int c = zin.read(); c != -1; c = zin.read()) {
                            ic++;
                            //sb.append((char)c);
                            str.append((char) c);
                            if (str.toString().endsWith(endTag) || ic > 4000)
                             break;
                        }
                        zin.closeEntry();
                        Log.file.info("FB2 title = {}", str.toString());
                    }

            }

        } catch (Exception e) {
            Log.file.error("fullZipName = " + fullZipName, e);
            throw new WEditException("Error read zipFile = " + fullZipName, e);
        } finally {
            if (zin != null) {
                try {
                    zin.close();
                } catch (IOException e) {
                    Log.file.error("ZIP close error", e);

                }
            }
        }

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
