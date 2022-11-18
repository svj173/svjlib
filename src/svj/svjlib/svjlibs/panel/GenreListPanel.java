package svj.svjlib.svjlibs.panel;

import svj.svjlib.WCons;
import svj.svjlib.gui.label.WLabel;
import svj.svjlib.gui.panel.WPanel;
import svj.svjlib.obj.BookTitle;
import svj.svjlib.svjlibs.SLCons;
import svj.svjlib.svjlibs.listener.GenreListMouseListener;
import svj.svjlib.svjlibs.obj.Author;
import svj.svjlib.svjlibs.obj.Genre;
import svj.svjlib.svjlibs.obj.GlobalGenre;

import javax.swing.*;

import java.awt.*;
import java.util.*;

/**
 * Список Жанров
 * Состав: Две горизонт панели
 * 1) Список жаноров с поджанрами
 * 2) Список книг выбранного поджанра - BooksPanel
 *
 * <BR/>
 */
public class GenreListPanel extends WPanel {

    // список жанров и кол-во их книг - левая панель
    //private  WPanel genreListPanel;
    // список книг выбранного ;fyhf
    private  final BooksPanel bookListPanel = new BooksPanel();


    public GenreListPanel() {

        String name = "GenresPanel";
        setName ( name );
        setId ( name );

        setLayout ( new BorderLayout(5,5) );

        // 1) Левая панель - список жанров
        JComponent genreListPanel = createGenreListPanel();
        add(new JScrollPane(genreListPanel), BorderLayout.WEST);


        // 2) Правая панель - список книг выбранного атвора
        add(bookListPanel, BorderLayout.CENTER);
    }


    // здесь лучше Автора сразу приводить к полной строке - ФИО и с ней уже работать - чем
    // анализирвоать возможность пропусков.
    private Author findBook(Collection<Author> authors, String authorName, boolean byFirst) {
        boolean b;
        String str;
        for (Author author : authors) {
            str = author.getSimple();
            if (byFirst) {
                b = str.startsWith(authorName);
                if (b) return author;
            } else {
                b = str.contains(authorName);
                if (b) return author;
            }
        }
        return null;
    }

    private JComponent createGenreListPanel() {
        //WPanel panel = new WPanel();
        WLabel label;
        Map<Genre, Collection<BookTitle>> maps;
        String str;
        Font font, font2;

        //panel.setLayout ( new GridLayout( 0, 2, 5, 5 ) );

        Box contents = new Box(BoxLayout.Y_AXIS);
      	//contents.add(new JScrollPane(bookListPanel));
      	//contents.add(bookListPanel);


        // Наполнить панель данными
        for (Map.Entry<GlobalGenre, Map<Genre, Collection<BookTitle>>> entry : SLCons.BOOKS_MANAGERS.getBookMap().entrySet()) {
            label = new WLabel(entry.getKey().getTitle());
            //label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setOpaque(true);
            label.setForeground(WCons.BLUE_5);
            contents.add(label);
            // поджанры
            maps = entry.getValue();
            for (Map.Entry<Genre, Collection<BookTitle>> ent : maps.entrySet()) {
                str = createGenreText(ent.getValue().size(), ent.getKey().getTitle());
                label = new WLabel(str, ent.getValue());
                //label.setFont(Font.MONOSPACED);
                font = label.getFont();
                font2 = new Font (Font.MONOSPACED, font.getStyle(), font.getSize());
                label.setFont(font2);
                contents.add(label);
            }
        }

        // Пропуск внизу
        contents.add(Box.createVerticalGlue());


        /*
        // Наполнить панель данными
        for (Map.Entry<GlobalGenre, Map<Genre, Collection<BookTitle>>> entry : SLCons.BOOKS_MANAGERS.getBookMap().entrySet()) {
            label = new WLabel(entry.getKey().getTitle());
            panel.add(label);
            //label = new WLabel(Integer.toString(entry.getValue().size()), entry.getKey());
            label = new WLabel("");
            panel.add(label);
            // поджанры
            maps = entry.getValue();
            for (Map.Entry<Genre, Collection<BookTitle>> ent : maps.entrySet()) {
                label = new WLabel(ent.getKey().getTitle(), ent.getValue());
                panel.add(label);
                label = new WLabel(Integer.toString(ent.getValue().size()), ent.getValue());
                panel.add(label);
            }
        }
        */

        GenreListMouseListener action = new GenreListMouseListener(contents, bookListPanel);
        //GenreListMouseListener action = new GenreListMouseListener(panel, bookListPanel);
        //panel.addMouseListener(action);
        contents.addMouseListener(action);

        //panel.repaint();
        //panel.revalidate();

        return contents;
    }

    private String createGenreText(int size, String title) {

        String prefix = "          ";
        String strSize = Integer.toString(size);
        String str1 = prefix.substring(strSize.length());

        String result = str1 + strSize + "    " + title;

        return result;
    }

}
