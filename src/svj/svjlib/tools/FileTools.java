package svj.svjlib.tools;

import svj.svjlib.Log;
import svj.svjlib.Par;
import svj.svjlib.exc.WEditException;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.*;
import java.util.zip.*;


/**
 * Сервисные утилиты по работе с файлами и их именами.
 * <BR>
 * <BR> User: Zhiganov
 * <BR> Date: 21.06.2010 14:19:32
 */
public class FileTools
{
    /**
     * Загрузить файл по ссылке из ресурса.
     * @param path
     * @param descr
     * @return
     */
    public static ImageIcon createImageFromUrl ( String path, String descr )
    {
        ImageIcon   result;
        URL         imgURL;

        try
        {
            imgURL = FileTools.class.getResource ( path);
            if (imgURL == null)
                result = null;
            else
            {
                if ( descr != null )
                    result = new ImageIcon ( imgURL, descr );
                else
                    result = new ImageIcon ( imgURL );
            }
        } catch ( Throwable t ) {
            Log.l.error ( "createImageFromUrl Error: path = "+path+"; descr = "+descr, t );
            result = createEmptyImage(16, 16);
        }
        return result;
    }

    public static ImageIcon createImageFromFileName ( String fileName, String descr )
    {
        ImageIcon   result;

        try
        {
            if ( descr != null )
                result = new ImageIcon ( fileName, descr );
            else
                result = new ImageIcon ( fileName );

        } catch ( Throwable t ) {
            Log.l.error ( "createImageFromFileName Error: fileName = "+fileName+"; descr = "+descr, t );
            result = createEmptyImage(16, 16);
        }
        return result;
    }

    /**
     * Создать белый рисунок заданного размера.
     * @param w  Ширина в пиксеклях.
     * @param h  Высота.
     * @return   Пустой рисунок.
     */
    public static ImageIcon createEmptyImage ( int w, int h )
    {
        ImageIcon       imageIcon = null;
        BufferedImage   resizedImg;
        Graphics2D      g2;

        try
        {
            resizedImg = new BufferedImage ( w, h, BufferedImage.TYPE_INT_ARGB );
            g2 = resizedImg.createGraphics();
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, w, h);
            g2.dispose();

            imageIcon = new ImageIcon ( resizedImg );

        } catch(Throwable t) {
            Log.l.error ( "createEmptyImage Error: ", t );
        }
        return imageIcon;
    }

    /**
     * Копируем файл в диреткорию.
     * Если результирующая директория является файлом - взять парент, как директорию. Если отсутствует - пока не реализовал.
     * @param srcFile   Имя исходного файла. Абс или относительное.
     * @param targetDir Результирующая директория. Только как абс путь.
     * @throws WEditException Ошибки копирования.
     */
    public static void copyFileToDir ( String srcFile, String targetDir ) throws WEditException
    {
        String  absFileName;
        File    file;

        absFileName = FileTools.createFileName ( Par.MODULE_HOME, srcFile );
        file        = new File ( targetDir );
        if ( file.isFile () )  file = file.getParentFile();

        copyFile ( new File(absFileName), file.getAbsolutePath() );
    }

    public static String copyFile ( File srcFile, String targetDir ) throws WEditException
    {
        FileInputStream     fis;
        FileOutputStream    fos;
        String              targetFileName;
        File                targetFile;
        byte[]              buf;
        int                 ic;

        //Log.l.debug ( "Start loadFile: Name = ", fileName );

        buf = new byte[16384];

        try
        {
            targetFileName  = Convert.concatObj ( targetDir, "/", srcFile.getName() );
            targetFile      = new File ( targetFileName );

            // Если уже есть такой файл - ничего не делать
            if ( targetFile.exists () )  return null;

            fis             = new FileInputStream ( srcFile );
            fos             = new FileOutputStream ( targetFileName );

            while ( (ic = fis.read ( buf )) != -1 )
            {
                fos.write ( buf, 0, ic );
            }

            fos.flush();
            fos.close ();
            fis.close();

        } catch ( Exception e )        {
            //LogWriter.file.error ( e, "Load file ERROR. codePage = '", codePage, "', fileName = '", fileName, "'" );
            throw new WEditException ( e, "Ошибка копирования файла '", srcFile, "' в '", targetDir, "' :\n", e );
        }
        //LogWriter.file.debug ( "load: Finish" );
        return targetFileName;
    }

    public static void closeFileStream ( OutputStream fos, String fileName )
    {
        if ( fos != null )
        {
            try
            {
                fos.close();
            } catch ( Exception e )                {
                Log.l.error ( Convert.concatObj ( "File '", fileName, "' close error." ), e);
            }
        }
    }

    public static String getExtension ( File f )
    {
        String  ext, s;
        int     i;

        ext = null;
        s   = f.getName();
        i   = s.lastIndexOf('.');

        if ( (i > 0) &&  ( i < (s.length() - 1) ) )
        {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }


    public static boolean deleteRecursive ( File path ) throws WEditException
    {
        if ( ! path.exists() )
            throw new WEditException ( null, "Нет такого файла '", path.getAbsolutePath(), "'." );

        boolean ret = true;

        if ( path.isDirectory () )
        {
            for ( File f : path.listFiles () )
            {
                ret = ret && deleteRecursive ( f );
            }
        }

        return ret && path.delete ();
    }

    /**
     * Удалить файл.
     * @param fileName Абсолютное имя файла.
     */
    public static boolean deleteFile ( String fileName ) // throws WEditException
    {
        File file;
        file = new File ( fileName );
        return file.delete();
    }

    /**
     * Создать полное имя файла - с абсолютным путем.
     *
     * @param fileName Имя файла
     * @return Полное имя файла
     */
    public static String createFileName ( String fileName )
    {
        if ( fileName == null ) return null;

        if ( fileName.startsWith ( "/" ) || ( fileName.indexOf ( ':' ) > 0 ) )
        {
            // Это абсолютный путь - взять как есть
            return fileName;
        }
        else
        {
            // Добавить абс путь
            String path = Par.MODULE_HOME;

            // добавить разделитель - если необходимо
            String sep = File.separator;
            if ( path.endsWith ( "/" ) || path.endsWith ( "\\" ) ) sep = "";
            return path + sep + fileName;
        }
    }

    public static String createFileName ( String path, String fileName )
    {
        if ( fileName == null ) return null;

        if ( fileName.startsWith ( "/" ) || ( fileName.indexOf ( ':' ) > 0 ) )
        {
            // Это абсолютный путь - взять как есть
            return fileName;
        }
        else
        {
            // Добавить абс путь

            // добавить разделитель - если необходимо
            String sep = File.separator;    // "/"
            if ( path.endsWith ( "/" ) || path.endsWith ( "\\" ) ) sep = "";
            return path + sep + fileName;
        }
    }

    /**
     * Прочитать файл с текстом. Выдать результат.
     * Если ошибка - Exception.
     */
    public static String loadFile ( String fileName ) throws WEditException
    {
        return loadFile ( fileName, null );
    }


    /**
     * Прочитать файл с текстом. Выдать результат.
     * Если ошибка - Exception.
     * @param fileName    имя файла
     * @param codePage    кодировка файла
     * @return            Текстовое содержимое файла
     * @throws WEditException ошибка чтения файла
     */
    public static String loadFile ( String fileName, String codePage ) throws WEditException
    {
        Log.file.debug ( "Start loadFile: Name = " + fileName );
        String result = "";

        try
        {
            // Загрузить файл
            File f = new File ( fileName );
            FileInputStream fis = new FileInputStream ( f );
            byte[] buf = new byte[( int ) f.length ()];
            fis.read ( buf );
            //
            if ( codePage == null )
                result = new String ( buf );
            else
                result = new String ( buf, codePage );

        } catch ( Exception e )        {
            Log.file.error ( Convert.concatObj ( "Load file ERROR. codePage = '", codePage, "', fileName = '", fileName, "'" ), e);
            throw new WEditException ( e, "Ошибка чтения файла '", fileName, "' с кодировкой '", codePage, "'" );
        }
        Log.file.debug ( "load: Finish" );

        return result;
    }

    public static Properties loadProperties ( String fileName ) throws WEditException
    {
        Properties result = new Properties ();
        FileInputStream fis;
        try
        {
            fis = new FileInputStream ( fileName );
            if ( fileName.endsWith ( "xml" ) )
            {
                // XML файл
                result.loadFromXML ( fis );
            }
            else
            {
                // Текстовый файл
                result.load ( fis );
            }
        } catch ( Exception e )         {
            //Log.file.error ()
            throw new WEditException ( "Load file '" + fileName + "' properties error :\n" + e, e );
        }
        return result;
    }

    /**
     * Прочитать ява-объект из файла.
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public static Object loadObject ( String fileName ) throws Exception
    {
        Object result;
        FileInputStream fis;
        ObjectInputStream ois;

        fis = new FileInputStream ( fileName );
        ois = new ObjectInputStream ( fis );
        result = ois.readObject ();
        ois.close ();

        return result;
    }

    /**
     * Сохранить ява-объект в файле.
     *
     * @param fileName
     * @param object
     * @throws Exception
     */
    public static void save ( String fileName, Object object ) throws Exception
    {
        FileOutputStream fos;
        ObjectOutputStream oos;

        fos = new FileOutputStream ( fileName );
        oos = new ObjectOutputStream ( fos );

        //oos.writeInt(12345);
        oos.writeObject ( object );
        oos.close ();
    }

    public static void save ( String fileName, byte[] bytes ) throws Exception
    {
        FileOutputStream fos;

        fos = new FileOutputStream ( fileName );

        //oos.writeInt(12345);
        fos.write ( bytes );
        fos.close ();
    }

    /**
     * Сохранить текст в файле.
     *
     * @param fileName
     * @param text
     * @throws Exception
     */
    public static void save ( String fileName, String text ) throws Exception
    {
        FileWriter fw;

        fw = new FileWriter ( fileName );
        // Записать в основной
        fw.write ( text );
        fw.flush ();
        fw.close ();
    }


    public static void saveProps ( String fileName, Properties props ) throws WEditException
    {
        saveProps ( fileName, props, null );
    }

    public static void saveProps ( String fileName, Properties props, String title ) throws WEditException
    {
        OutputStream    os;
        File            file, folder;
        String          str;

        Log.file.debug ("Start");

        os = null;
        try
        {
            file   = new File(fileName);

            // создать папку (рекурсивно) если не существует
            folder = file.getParentFile ();
            if ( ! folder.exists() )          createFolder ( folder );

            os = new FileOutputStream (file);

            if ( fileName.endsWith ( "xml" ) )
            {
                // XML файл
                str = " User XML props";
                if ( title != null )  str = str + ": " + title;
                props.storeToXML ( os, str );
            }
            else
            {
                // Текстовый файл
                str = " User TXT props";
                if ( title != null )  str = str + ": " + title;
                props.store ( os, str );
            }
            os.flush ();
            os.close();
        } catch ( Exception e )         {
            FileTools.closeFileStream ( os, fileName );
            Log.file.error ("err", e );
            throw new WEditException ( "Системная ошибка сохранения данных в файле '" + fileName + "' :\n" + e, e );
        }
        Log.file.debug ("Finish");
    }

    public static void saveWithTmp ( File file, StringBuilder text ) throws Exception
    {
        FileWriter fw, tmp;
        String str;
        // Создать промежуточный
        str = file.getAbsoluteFile() + ".tmp";
        tmp = new FileWriter ( str );
        // Записать в промежуточный
        tmp.write ( text.toString () );
        tmp.close ();
        // Создать основной
        fw = new FileWriter ( file );
        // Записать в основной
        fw.write ( text.toString () );
        fw.close ();
        // TODO в конце работы промежуточный файл удалить
        //   - tempFile.deleteOnExit(); - удалится при закрытии java
    }

    /**
     * Запрашивает и осуществляет выборку имени файла и директории.
     *
     * @param frame            Родительский фрейм для диалогового окна. Может быть NULL.
     * @param currentDirectory Исходный файл. Может быть NULL.
     * @return File или NULL, если была отмена операции.
     */
    public static File selectFileName ( JFrame frame, File currentDirectory, JComponent additionalPanel )
    {
        File result;
        JFileChooser fc;
        int returnVal;

        result = null;
        if ( currentDirectory == null )
            fc = new JFileChooser ();
        else
            fc = new JFileChooser ( currentDirectory );

        if ( additionalPanel != null )  fc.setAccessory ( additionalPanel );

        returnVal = fc.showOpenDialog ( frame );

        if ( returnVal == JFileChooser.APPROVE_OPTION )
        {
            result = fc.getSelectedFile ();
        }

        return result;
    }

    public static File selectFileName ( JFrame frame, File currentDirectory )
    {
        return selectFileName ( frame, currentDirectory, null );
    }

    public static synchronized boolean createFolder ( final File folder )
    {
        if ( !folder.exists () && createFolder ( folder.getParentFile () ) )
            folder.mkdir ();

        return folder.exists ();
    }

    /**
     * Упаковать все файлы из заданной директории.
     *
     * @param sourceDir     Исходная директория
     * @param targetZipFile ЗИП файл. Например "/u/arhive/myfiles.zip"
     */
    public static synchronized void zipAllFiles ( String sourceDir, String targetZipFile )
    {
        BufferedInputStream origin = null;
        FileOutputStream dest;
        ZipOutputStream out = null;
        FileInputStream fi;
        ZipEntry entry;
        byte data[];
        File f;
        String files[];
        int i, size;

        size = 2048;

        try
        {
            dest = new FileOutputStream ( targetZipFile );
            out = new ZipOutputStream ( new BufferedOutputStream ( dest ) );
            //out.setMethod(ZipOutputStream.DEFLATED);
            data = new byte[size];
            // get a list of files from current directory
            f = new File ( sourceDir );
            files = f.list ();

            for ( i = 0; i < files.length; i++ )
            {
                //System.out.println ( "Adding: " + files[i] );
                fi = new FileInputStream ( files[ i ] );
                origin = new BufferedInputStream ( fi, size );
                entry = new ZipEntry ( files[ i ] );
                out.putNextEntry ( entry );
                int count;
                while ( ( count = origin.read ( data, 0, size ) ) != -1 )
                {
                    out.write ( data, 0, count );
                }
                origin.close ();
            }
            out.close ();
        } catch ( Exception e )
        {
            try
            {
                if ( origin != null ) origin.close ();
            } catch ( Exception ex1 )
            {
            }
            try
            {
                if ( out != null ) out.close ();
            } catch ( Exception ex1 )
            {
            }
            //e.printStackTrace ();
        }
    }

    /**
     * Архивировать заданный файл.
     * Файл зипуется в заданную директорию, после чего - удаляется.
     *
     * @param sourceFile Исходный файл
     * @param targetDir  Директория для хранения архивов
     * @throws java.io.IOException Ошибки работы
     */
    public static synchronized void archiveFile ( File sourceFile, String targetDir )
            throws IOException
    {
        BufferedInputStream origin = null;
        FileOutputStream dest;
        ZipOutputStream out = null;
        FileInputStream fi;
        ZipEntry entry;
        byte data[];
        File ft;
        String arhiveFileName, sourceFileName;
        int size;

        size = 2048;

        try
        {
            // Проверить директорию архивирования
            ft = new File ( targetDir );
            if ( !createFolder ( ft ) )
                throw new IOException ( "Ошибка архивирования файла '" + sourceFile + "'. Ошибка создания директории для архивов '" + targetDir + "'." );

            if ( sourceFile == null )
                throw new IOException ( "Ошибка архивирования файла '" + sourceFile + "'. Файл не задан." );
            if ( !sourceFile.exists () )
                throw new IOException ( "Ошибка архивирования файла '" + sourceFile + "'. Файл не существует." );
            if ( sourceFile.isDirectory () )
                throw new IOException ( "Ошибка архивирования файла '" + sourceFile + "'. Это директория." );

            sourceFileName = sourceFile.getCanonicalPath ();
            // Создать имя архивного файла
            arhiveFileName = targetDir + File.separator + sourceFile.getName () + ".zip";
            Log.file.info ( "Arhive file  '" + sourceFileName + "' to '" + arhiveFileName + "'." );
            dest = new FileOutputStream ( arhiveFileName );
            out = new ZipOutputStream ( new BufferedOutputStream ( dest ) );
            //out.setMethod(ZipOutputStream.DEFLATED);
            data = new byte[size];

            fi = new FileInputStream ( sourceFile );
            origin = new BufferedInputStream ( fi, size );
            entry = new ZipEntry ( sourceFileName );
            out.putNextEntry ( entry );

            int count;
            while ( ( count = origin.read ( data, 0, size ) ) != -1 )
            {
                out.write ( data, 0, count );
            }
            origin.close ();
            out.close ();

            // Удалить исходный файл
            sourceFile.delete ();

        } catch ( Exception e )         {
            try
            {
                if ( origin != null ) origin.close ();
            } catch ( Exception ex1 )            {
                Log.file.error ("Error", ex1 );
            }
            try
            {
                if ( out != null ) out.close ();
            } catch ( Exception ex1 )            {
                Log.file.error ( "Error", ex1 );
            }
            Log.file.error ( "Error", e );
            if ( e instanceof IOException )
                throw ( IOException ) e;
            else
                throw new IOException ( "Ошибка архивирования файла '" + sourceFile + "' в директорию '" + targetDir + "' : " + e.toString () );
            //e.printStackTrace ();
        } finally         {

        }
    }

    public static synchronized void archiveFile ( String sourceFile, String targetDir )
            throws IOException
    {
        File f = new File ( sourceFile );
        archiveFile ( f, targetDir );
    }

    public static List<String> getTextFromResource ( String fileName )
    {
        return null;
    }


    /**
     * Обратный процесс также реализуем нерекурсивным методом.
     * Проходим по всем entry в архиве, директории сразу создаём, файлы добавляем в очередь.
     * Потом проходим по очереди и создаём файлы, копируя их из ZipInputStream в FileOutputStream
     * @param path
     * @param dir_to
     * @throws IOException
     */
    public static void unpack ( String path, String dir_to ) throws IOException
    {
        ZipFile zip = new ZipFile ( path );
        Enumeration entries = zip.entries ();
        LinkedList<ZipEntry> zfiles = new LinkedList<ZipEntry> ();
        while ( entries.hasMoreElements () )
        {
            ZipEntry entry = ( ZipEntry ) entries.nextElement ();
            if ( entry.isDirectory () )
            {
                new File ( dir_to + "/" + entry.getName () ).mkdir ();
            }
            else
            {
                zfiles.add ( entry );
            }
        }
        for ( ZipEntry entry : zfiles )
        {
            InputStream in = zip.getInputStream ( entry );
            OutputStream out = new FileOutputStream ( dir_to + "/" + entry.getName () );
            byte[] buffer = new byte[1024];
            int len;
            while ( ( len = in.read ( buffer ) ) >= 0 )
                out.write ( buffer, 0, len );
            in.close ();
            out.close ();
        }
        zip.close ();
    }

    public static boolean isValid ( final File file )
    {
        ZipFile zipfile = null;
        try
        {
            zipfile = new ZipFile ( file );
            return true;
        } catch ( ZipException e )       {
            return false;
        } catch ( IOException e )        {
            return false;
        } finally        {
            try
            {
                if ( zipfile != null )
                {
                    zipfile.close();
                    zipfile = null;
                }
            } catch ( IOException e )             {
            }
        }
    }

}
