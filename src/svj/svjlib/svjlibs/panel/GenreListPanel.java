package svj.svjlib.svjlibs.panel;

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
    private  WPanel genreListPanel;
    // список книг выбранного ;fyhf
    private  final BooksPanel bookListPanel = new BooksPanel();


    public GenreListPanel() {

        String name = "GenresPanel";
        setName ( name );
        setId ( name );

        setLayout ( new BorderLayout(5,5) );

        // 1) Левая панель - список жанров
        genreListPanel = createGenreListPanel();
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

    private WPanel createGenreListPanel() {
        WPanel panel = new WPanel();
        WLabel label;
        Map<Genre, Collection<BookTitle>> maps;

        panel.setLayout ( new GridLayout( 0, 2, 5, 5 ) );

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

        GenreListMouseListener action = new GenreListMouseListener(panel, bookListPanel);
        panel.addMouseListener(action);

        //panel.repaint();
        //panel.revalidate();

        return panel;
    }

    public void initAuthors(Map<Author, Collection<BookTitle>> initObject)  {

        // левая панель - список авторов (simpleName) и кол-во книг у них

        // - очистить старые данные по авторам
        genreListPanel.removeAll();

        if (initObject == null) return;

        WLabel label;
        for (Map.Entry<Author, Collection<BookTitle>> entry : initObject.entrySet()) {
            label = new WLabel(entry.getKey().getSimple(), entry.getKey());
            genreListPanel.add(label);
            label = new WLabel(Integer.toString(entry.getValue().size()), entry.getKey());
            genreListPanel.add(label);
        }
        bookListPanel.setAuthorList(initObject);

        genreListPanel.repaint();
        genreListPanel.revalidate();

        // - панель в центре вверху - список книг выраного автора
        // todo Формируем таблицу с галочками в первой позиции и с возможностью натсройки полей
        // - убирать лишние, переставлять местами, сортирвоки по полям.

        // todo Кнопки - Выход, Экспорт отмеченных
        // - по умочланию экспортирует в рабочую директорию Редактора, в поддиректорию books - в алфавитном порядке

        // панель в центре внизу - подробно про автора - анотация,

        // Формирование интерфейса

    }

}
