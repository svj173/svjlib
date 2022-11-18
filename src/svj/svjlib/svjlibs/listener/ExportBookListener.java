package svj.svjlib.svjlibs.listener;

import svj.svjlib.Log;
import svj.svjlib.WCons;
import svj.svjlib.exc.WEditException;
import svj.svjlib.obj.BookTitle;
import svj.svjlib.svjlibs.SLCons;
import svj.svjlib.svjlibs.obj.Author;
import svj.svjlib.svjlibs.obj.LibInfo;
import svj.svjlib.svjlibs.table.BookTablePanel;
import svj.svjlib.tools.DialogTools;
import svj.svjlib.tools.FileTools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Экспорт выбранных книг из ZIP формата.
 * <BR/>
 */
public class ExportBookListener implements ActionListener {

    private final BookTablePanel bookTablePanel;

    public ExportBookListener(BookTablePanel bookTablePanel) {
        this.bookTablePanel = bookTablePanel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        Collection<BookTitle> books = bookTablePanel.getSelectedItems();

        if (books.isEmpty()) return;

        // Стартова проверка. Только для Удаленной работы - на предмет прописки IP компа, диретории в компе, логин-пароль.
        checkStart();

        // Взять параметр - рабочая директория для Экспорта
        String exportDir = SLCons.DIR_PATH + File.separator + "export";

        StringBuilder sb = new StringBuilder();

        String authorDir;
        boolean ok;

        for (BookTitle book : books) {

            // Создаем путь до диреткории Автора
            authorDir = createAuthDir(exportDir, book);
            //sb.append("\nДиректория автора: ");
            //sb.append(authorDir);


            // Прогоняем путь на предмет создания остутсвующих поддиректорий
            ok = FileTools.createFolder(authorDir);
            if (! ok) {
                sb.append("\nERROR. Не создана директория: ");
                sb.append(authorDir);
                continue;
            }

            // Создаем имя для распакованной книги
            String resultFileName = createRealFileName(authorDir, book.getBookTitle());

            // Раззипуем книгу - для удаленной рабоыт этот метод переписывается
            // Сохраняем книгу в директории автора
            // - При удаленке, зип файл затягивается себе, в директторию автора, там распаковываеться и удаляется
            try {
                unzipBook(book, authorDir, resultFileName);

                sb.append("\nКнига \n'");
                sb.append(book.getBookTitle());
                sb.append("'\n сохранена в : \n");
                sb.append(resultFileName);
                sb.append(WCons.END_LINE);

            } catch (Exception e) {
                sb.append("\nERROR. Ошибка распаковки книги '");
                sb.append(book.info());
                sb.append("' : ");
                sb.append(e.getMessage());
                Log.file.error("unzipBook error. book = " + book.info() + "; authorDir = " + authorDir, e);
            }



            // Складываем в  журнал
        }


        //DialogTools.showMessage("Выбраны книги", DumpTools.printBookTitles(books));
        DialogTools.showMessage("Экспорт", sb);

    }

    private String createRealFileName(String authorDir, String bookTitle) {

        bookTitle = bookTitle.replace(" ", "_");
        bookTitle = bookTitle.replace("\n", "_");
        bookTitle = bookTitle.replace("\r", "_");
        String result = authorDir + File.separator + bookTitle + ".fb2";

        return result;
    }

    private void unzipBook(BookTitle book, String authorDir, String resultFileName) throws WEditException {

        // Берем Библиотеку
        LibInfo libInfo = SLCons.LIBS_MANAGER.getLib(book.getLibId());

        // раззипуем архив, найдем наш entry
        processZipArchive ( libInfo.getLibDir(), book.getArchiveName(), book.getFileName(), resultFileName);

    }

    private String createAuthDir(String exportDir, BookTitle book) {

        // Взять первого автора
        Author author = getFirstAuthor(book);

        String authorFirstSymbol = "Others";
        String authorFullName = "Unknown";
        if (author != null) {
            // Берем первую букву первого автора
            authorFirstSymbol = getAuthorFirstSymbol(author);
            authorFullName = author.getSimple();
        }

        String authDir = exportDir + File.separator + authorFirstSymbol + File.separator + authorFullName;

        return authDir;
    }

    private Author getFirstAuthor(BookTitle book) {
        if (book.getAuthors().isEmpty())  return null;
        for (Author author: book.getAuthors()) {
            return author;
        }
        return null;
    }

    private String getAuthorFirstSymbol(Author author) {
        String result;

        String name = author.getSimple();
        char ch = name.charAt(0);
        char[] chb = new char[1];
        chb[0] = ch;
        result = new String(chb);

        return result;
    }

    /**
     * Стартова проверка. Только для Удаленной работы - на предмет прописки IP компа, диретории в компе, логин-пароль.
     */
    private void checkStart() {
    }

    private void processZipArchive(String libDir, String archiveZipFileName,
                                   String bookZipFileName, String resultFileName) throws WEditException {

        /* отладка
        StringBuilder sb = new StringBuilder(512);
        sb.append("libDir = ");
        sb.append(libDir);
        sb.append("\narchiveZipFileName = ");
        sb.append(archiveZipFileName);
        sb.append("\nbookZipFileName = ");
        sb.append(bookZipFileName);
        */

        ZipEntry zipEntry;
        //long size, fullSize;

        // путь до зип-архива
        String fullZipName = libDir + File.separator + archiveZipFileName;

        try {
            ZipFile zf = new ZipFile(fullZipName);
            //sb.append("\n\nzipArch = ");
            //sb.append(zf);

            // берем зип-файл книги
            zipEntry = zf.getEntry(bookZipFileName);
            //sb.append("\n\nzipEntry = ");
            //sb.append(zipEntry);

            // распаковка
            processZipBook ( zf, zipEntry, archiveZipFileName, resultFileName);

        } catch (Exception e) {
            Log.file.error("fullZipName = " + fullZipName, e);
            throw new WEditException("Error read zipFile = " + fullZipName, e);
        }

        // отладка
        //DialogTools.showMessage("ZIP", sb);
    }

    private void processZipBook(ZipFile zf, ZipEntry zipEntry, String archiveZipFileName,
                                String resultFileName) throws WEditException {

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

                FileOutputStream fout = new FileOutputStream(resultFileName);

                /*
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                */


                byte[] buf = new byte[1024];
                int nLength;

                while (true) {
                    try {
                        nLength = zin.read(buf);
                    } catch (EOFException ex) {
                        break;
                    }

                    if (nLength < 0)
                        break;

                    fout.write(buf, 0, nLength);
                }

                fout.flush();
                zin.closeEntry();
                fout.close();

                /*
                // распаковка - только заголовок FB2

                int bufSize = 80000;
                byte[] bytes = new byte[bufSize + 10];

                int offset = 0;
                int numRead = 0;

                //while (offset < bytes.length && (numRead=zin.read(bytes, offset, bytes.length-offset)) >= 0)
                while (offset < bytes.length && (numRead = zin.read(bytes, offset, 1024)) >= 0) {
                    offset += numRead;
                    String str = new String(bytes);
                    //if (str.contains(endTag)) break;
                }

                zin.closeEntry();
                */
            }
        } catch (Exception e) {
            Log.file.error("Error in zip-archive = '" + archiveZipFileName + "'; zipFile = " + name, e);
            throw new WEditException("Ошибка распаковки файла: " + archiveZipFileName, e);
        }

    }

}
