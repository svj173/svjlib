package svj.svjlib.svjlibs.obj;

/**
 * Списки жанров
 * 1) https://coollib.net/g#
 * - Всего жанров: 507
 * - по глобальным жанрам
 * - но не одним файлом
 * 2) http://www.fictionbook.org/index.php/%D0%96%D0%B0%D0%BD%D1%80%D1%8B_FictionBook_2.1
 * - гораздо меньше чем в coollib.net
 * <BR/>
 */
public enum GlobalGenre {
    // todo взять из myrulib
    Military("Военное дело"),
    Economy("Деловая литература"),
    Detective("Детективы и Триллеры"),
    Children("Детская литература"),
    Nonfiction("Документальная литература"),
    Home("Домоводство (Дом и семья)"),
    Drama("Драматургия"),
    History("История и археология"),
    Computer("Компьютеры и Интернет"),
    CompOS("Компьютеры: Операционные системы"),
    CompSowt("Компьютеры: Разработка ПО"),
    CompLang("Компьютеры: Языки и системы программирования"),
    Culture("Культура и искусство"),
    Publication("Литература по изданиям"),
    Literature("Литература по эпохам"),
    Love("Любовные романы"),
    Science("Наука, Образование: прочее"),
    SciEst("Науки естественные"),
    SciNature("Науки о живой природе"),
    SciSocial("Науки общественные и гуманитарные"),
    Poetry("Поэзия"),
    Adventure("Приключения"),
    Prose("Проза"),
    Other("Прочее"),
    Psychology("Психология и психиатрия"),
    Religion("Религия и духовность"),
    Guide("Справочная литература"),
    Antique("Старинное"),
    Technical("Техника"),
    SchoolBook("Учебники и пособия"),
    Fantastic("Фантастика"),
    Folklore("Фольклор"),
    StoryForm("Формы произведений"),
    Fantasy("Фэнтези"),
    Hobby("Хобби и ремесла"),
    Humor("Юмор"),

    Unknown("Неизвестный"),
    ;

    private final String title;

    GlobalGenre(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
