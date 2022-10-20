package svj.svjlib.svjlibs.obj;

/**
 * <BR/>
 */
public enum Genre {

    // Военное дело
    military_arts("Боевые искусства", GlobalGenre.Military),
    nonf_military("Военная документалистика и аналитика", GlobalGenre.Military),
    military_history("Военная история", GlobalGenre.Military),
    military_weapon("Военная техника и вооружение", GlobalGenre.Military),
    military("Военное дело", GlobalGenre.Military),
    military_memoirs("Военные мемуары", GlobalGenre.Military),
    military_special("Спецслужбы", GlobalGenre.Military),


    // Деловая литература
    banking("Банковское дело", GlobalGenre.Economy),
    accounting("Бухучет и аудит", GlobalGenre.Economy),
    global_economy("Внешняя торговля", GlobalGenre.Economy),
    sci_business("Деловая литература: прочее", GlobalGenre.Economy),
    paper_work("Делопроизводство", GlobalGenre.Economy),
    org_behavior("Корпоративная культура", GlobalGenre.Economy),
    personal_finance("Личные финансы", GlobalGenre.Economy),
    small_business("Малый бизнес", GlobalGenre.Economy),
    marketing("Маркетинг, PR, реклама", GlobalGenre.Economy),
    real_estate("Недвижимость", GlobalGenre.Economy),
    popular_business("О бизнесе популярно", GlobalGenre.Economy),
    industries("Отраслевые издания", GlobalGenre.Economy),
    job_hunting("Поиск работы, карьера", GlobalGenre.Economy),
    economics_ref("Справочная деловая литература", GlobalGenre.Economy),
    trade("Торговля", GlobalGenre.Economy),
    management("Управление, подбор персонала", GlobalGenre.Economy),
    stock("Ценные бумаги, инвестиции", GlobalGenre.Economy),
    sci_economy("Экономика", GlobalGenre.Economy),


    // Детективы и Триллеры
    thriller_archaeological("Археологический триллер", GlobalGenre.Detective),
    det_action("Боевик", GlobalGenre.Detective),
    det_cozy("Дамский детективный роман", GlobalGenre.Detective),
    detective("Детектив", GlobalGenre.Detective),
    foreign_detective("Зарубежный детектив", GlobalGenre.Detective),
    det_irony("Иронический детектив", GlobalGenre.Detective),
    det_history("Исторический детектив", GlobalGenre.Detective),
    det_classic("Классический детектив", GlobalGenre.Detective),
    det_crime("Криминальный детектив", GlobalGenre.Detective),
    det_hard("Крутой детектив", GlobalGenre.Detective),
    det_maniac("Маньяки", GlobalGenre.Detective),
    thriller_medical("Медицинский триллер", GlobalGenre.Detective),
    det_political("Политический детектив", GlobalGenre.Detective),
    det_police("Полицейский детектив", GlobalGenre.Detective),
    thriller_psychology("Психологический триллер", GlobalGenre.Detective),
    det_rus("Российский детектив", GlobalGenre.Detective),
    det_su("Советский детектив", GlobalGenre.Detective),
    thriller_techno("Техно триллер", GlobalGenre.Detective),
    thriller("Триллер", GlobalGenre.Detective),
    det_espionage("Шпионский детектив", GlobalGenre.Detective),
    thriller_legal("Юридический триллер", GlobalGenre.Detective),


    // Детская литература
    children("Детская литература: прочее", GlobalGenre.Children),
    child_education("Детская образовательная литература", GlobalGenre.Children),
    child_prose("Детская проза", GlobalGenre.Children),
    child_sf("Детская фантастика", GlobalGenre.Children),
    child_det("Детские остросюжетные", GlobalGenre.Children),
    child_adv("Детские приключения", GlobalGenre.Children),
    child_verse("Детские стихи", GlobalGenre.Children),
    child_horror("Детские ужастики", GlobalGenre.Children),
    child_folklore("Детский фольклор", GlobalGenre.Children),
    child_fantasy("Детское фэнтези", GlobalGenre.Children),
    child_baby("Для самых маленьких", GlobalGenre.Children),
    foreign_children("Зарубежная литература для детей", GlobalGenre.Children),
    child_classical("Классическая детская литература", GlobalGenre.Children),
    ya("Подростковая литература", GlobalGenre.Children),
    child_rus("Русская детская литература", GlobalGenre.Children),
    child_tale_rus("Русские сказки для детей", GlobalGenre.Children),
    child_tale("Сказки для детей", GlobalGenre.Children),
    child_su("Советская детская литература", GlobalGenre.Children),


    // Документальная литература
    interview("Беседы и интервью", GlobalGenre.Nonfiction),
    nonf_biography("Биографии и Мемуары", GlobalGenre.Nonfiction),
    nonfiction("Документальная литература", GlobalGenre.Nonfiction),
    naturalist_notes("Заметки натуралиста", GlobalGenre.Nonfiction),
    travel_notes("Записки путешественника", GlobalGenre.Nonfiction),
    diaries("Записки, дневники", GlobalGenre.Nonfiction),
    letters("Письма, переписки", GlobalGenre.Nonfiction),
    nonf_publicism("Публицистика", GlobalGenre.Nonfiction),
    speech("Речи, выступления, доклады", GlobalGenre.Nonfiction),

    // Домоводство (Дом и семья)
    home_survival("Выживание и личная безопасность", GlobalGenre.Home),
    home_child("Дети. Книги для родителей", GlobalGenre.Home),
    home("Дом и семья: прочее", GlobalGenre.Home),
    home_pets("Домашние животные", GlobalGenre.Home),
    home_housekeeping("Домоводство", GlobalGenre.Home),
    home_health("Здоровье", GlobalGenre.Home),
    home_entertain("Игры и развлечения", GlobalGenre.Home),
    cats("Кошки", GlobalGenre.Home),
    home_beauty("Красота", GlobalGenre.Home),
    home_cooking("Кулинария", GlobalGenre.Home),
    family("Семейные отношения", GlobalGenre.Home),
    dogs("Собаки", GlobalGenre.Home),
    home_sex("Эротика, Секс", GlobalGenre.Home),

    // Драматургия
    drama_antique("Античная драма", GlobalGenre.Drama),
    vaudeville("Водевиль, буффонада", GlobalGenre.Drama),
    drama("Драма", GlobalGenre.Drama),
    dramaturgy("Драматургия", GlobalGenre.Drama),
    dramaturgy_for_classic("Зарубежная классическая драматургия", GlobalGenre.Drama),
    foreign_dramaturgy("Зарубежная современная драматургия", GlobalGenre.Drama),
    screenplays("Киносценарии", GlobalGenre.Drama),
    comedy("Комедия", GlobalGenre.Drama),
    mystery("Мистерия", GlobalGenre.Drama),
    dramaturgy_rus_classic("Русская классическая драматургия", GlobalGenre.Drama),
    dramaturgy_rus("Русская современная драматургия", GlobalGenre.Drama),
    dramaturgy_su("Советская драматургия", GlobalGenre.Drama),
    scenarios("Сценарии", GlobalGenre.Drama),
    tragedy("Трагедия", GlobalGenre.Drama),


    // История и археология
    sci_archeology("Археология", GlobalGenre.History),
    history_australia("История Австралии и Океании", GlobalGenre.History),
    history_asia("История Азии", GlobalGenre.History),
    history_america("История Америки", GlobalGenre.History),
    history_africa("История Африки", GlobalGenre.History),
    sci_history_4("История Древнего мира", GlobalGenre.History),
    history_europe("История Европы", GlobalGenre.History),
    sci_history_18("История Нового времени", GlobalGenre.History),
    sci_history_0("История первобытного общества", GlobalGenre.History),
    history_russia("История России и СССР", GlobalGenre.History),
    sci_history_15("История Средних веков", GlobalGenre.History),
    sci_history("История: прочее", GlobalGenre.History),
    history_world("Мировая история", GlobalGenre.History),
    sci_history_20("Новейшая история", GlobalGenre.History),
    sci_history_21("Современная история", GlobalGenre.History),


    // Компьютеры и Интернет
    comp_www_design("Web-дизайн", GlobalGenre.Computer),
    comp_hard("Аппаратное обеспечение, компьютерное железо", GlobalGenre.Computer),
    comp_db("Базы данных", GlobalGenre.Computer),
    comp_design("Графика. Дизайн. Мультимедиа", GlobalGenre.Computer),
    comp_www("Интернет", GlobalGenre.Computer),
    comp_history("История информатики и вычислительной техники", GlobalGenre.Computer),
    comp_security("Компьютерная безопасность", GlobalGenre.Computer),
    computers("Околокомпьютерная литература", GlobalGenre.Computer),
    comp_soft_office("Офисные приложения", GlobalGenre.Computer),
    comp_soft("Программы", GlobalGenre.Computer),
    comp_soft_cad("САПР", GlobalGenre.Computer),
    comp_osnet("Сети", GlobalGenre.Computer),
    tbg_computers("Учебники и самоучители по компьютеру", GlobalGenre.Computer),
    comp_hacking("Хакерство", GlobalGenre.Computer),
    comp_dsp("Цифровая обработка сигналов", GlobalGenre.Computer),


    // Компьютеры: Операционные системы
    comp_os_android("Android", GlobalGenre.CompOS),
    comp_os_linux("Linux", GlobalGenre.CompOS),
    comp_os_macos("MacOS", GlobalGenre.CompOS),
    comp_os_msdos("MS-DOS, FreeDOS", GlobalGenre.CompOS),
    comp_os_os2("OS/2", GlobalGenre.CompOS),
    comp_os_unix("Unix", GlobalGenre.CompOS),
    comp_os_windows("Windows", GlobalGenre.CompOS),
    comp_os_admin("ОС: администрирование, мониторинг, диагностика", GlobalGenre.CompOS),
    comp_os_theory("ОС: теоретические вопросы", GlobalGenre.CompOS),
    comp_os("Прочие ОС", GlobalGenre.CompOS),


    // Компьютеры: Разработка ПО
    comp_soft_dev_alg("Алгоритмы и структуры данных", GlobalGenre.CompSowt),
    comp_db_exp("Базы знаний и экспертные системы", GlobalGenre.CompSowt),
    comp_dv_ai("Искусственный интеллект", GlobalGenre.CompSowt),
    comp_soft_dev_craking("Крэкинг и реверсинжиниринг", GlobalGenre.CompSowt),
    comp_soft_dev_man("Менеджмент ПО", GlobalGenre.CompSowt),
    comp_soft_dev_oop("Объектно-ориентированное программирование", GlobalGenre.CompSowt),
    comp_soft_dev_debug("Отладка, тестирование и оптимизация ПО", GlobalGenre.CompSowt),
    comp_soft_dev_parallel("Параллельное и распределенное программирование", GlobalGenre.CompSowt),
    comp_soft_dev_graphic("Программирование графики", GlobalGenre.CompSowt),
    comp_soft_dev_games("Программирование игр", GlobalGenre.CompSowt),
    comp_soft_dev("Программирование: прочее", GlobalGenre.CompSowt),
    comp_soft_dev_system("Системное программирование", GlobalGenre.CompSowt),


    // Компьютеры: Языки и системы программирования
    comp_prog_dotnet(".NET Framework", GlobalGenre.CompLang),
    comp_prog_ada("Ada", GlobalGenre.CompLang),
    comp_prog_assembler("Assembler", GlobalGenre.CompLang),
    comp_prog_basic("Basic, Visual Basic, VB Script, VBA и т.п.", GlobalGenre.CompLang),
    comp_prog_c("C, C++, C#", GlobalGenre.CompLang),
    comp_prog_forth("Forth", GlobalGenre.CompLang),
    comp_prog_fortran("Fortran", GlobalGenre.CompLang),
    comp_prog_java("Java, Java Script", GlobalGenre.CompLang),
    comp_prog_lisp("Lisp, Scheme", GlobalGenre.CompLang),
    comp_prog_mfc("MFC", GlobalGenre.CompLang),
    comp_prog_oberon("Modula-2, Modula-3, Oberon, Oberon-2, Component Pascal", GlobalGenre.CompLang),
    comp_prog_pascal("Pascal, Delphi, Lazarus и т.п.", GlobalGenre.CompLang),
    comp_prog_php("PHP", GlobalGenre.CompLang),
    comp_prog_prolog("Prolog", GlobalGenre.CompLang),
    comp_prog_python("Python", GlobalGenre.CompLang),
    comp_prog_qt("Qt", GlobalGenre.CompLang),
    comp_prog_ror("Ruby", GlobalGenre.CompLang),
    comp_prog_winapi("Windows API", GlobalGenre.CompLang),
    comp_programming("Другие языки и системы программирования", GlobalGenre.CompLang),


    // Культура и искусство
    architecture_book("Архитектура и скульптура", GlobalGenre.Culture),
    painting("Живопись, альбомы, иллюстрированные каталоги", GlobalGenre.Culture),
    visual_arts("Изобразительное искусство, фотография", GlobalGenre.Culture),
    design("Искусство и Дизайн", GlobalGenre.Culture),
    art_criticism("Искусствоведение", GlobalGenre.Culture),
    art_history("История искусства", GlobalGenre.Culture),
    cine("Кино", GlobalGenre.Culture),
    nonf_criticism("Критика", GlobalGenre.Culture),
    sci_culture("Культурология", GlobalGenre.Culture),
    art_world_culture("Мировая художественная культура", GlobalGenre.Culture),
    fashion_style("Мода и стиль", GlobalGenre.Culture),
    music("Музыка", GlobalGenre.Culture),
    radio_tv("Радио и телевидение", GlobalGenre.Culture),
    art_dance("Танцы и хореография", GlobalGenre.Culture),
    theatre("Театр", GlobalGenre.Culture),


    // Литература по изданиям
    old_foreign_publication("Дореволюционные зарубежные издания", GlobalGenre.Publication),
    old_rus_publication("Дореволюционные российские издания", GlobalGenre.Publication),
    foreign_su_publication("Зарубежные издания советского периода", GlobalGenre.Publication),
    rarity("Раритетные издания", GlobalGenre.Publication),
    network_literature("Самиздат, сетевая литература", GlobalGenre.Publication),
    su_publication("Советские издания", GlobalGenre.Publication),
    foreign_publication("Современные зарубежные издания", GlobalGenre.Publication),
    ex_su_publication("Современные издания стран бывшего СССР", GlobalGenre.Publication),
    rus_publication("Современные российские издания", GlobalGenre.Publication),


    // Литература по эпохам
    literature_4("Литература IV века и ранее (эпоха Древнего мира)", GlobalGenre.Literature),
    foreign_antique("Литература V-XIII веков (эпоха Средневековья)", GlobalGenre.Literature),
    literature_16("Литература XIV-XVI веков (эпоха Возрождения)", GlobalGenre.Literature),
    literature_18("Литература XVII-XVIII веков (эпоха Просвещения)", GlobalGenre.Literature),
    literature_19("Литература ХIX века (эпоха Промышленной революции)", GlobalGenre.Literature),
    literature_20("Литература ХX века (эпоха Социальных революций)", GlobalGenre.Literature),
    literature_21("Литература ХXI века (эпоха Глобализации экономики)", GlobalGenre.Literature),

    // Любовные романы
    foreign_love("Зарубежная литература о любви", GlobalGenre.Love),
    love_history("Исторические любовные романы", GlobalGenre.Love),
    love_short("Короткие любовные романы", GlobalGenre.Love),
    love_sf("Любовная фантастика", GlobalGenre.Love),
    love_fantasy("Любовное фэнтези", GlobalGenre.Love),
    love("О любви", GlobalGenre.Love),
    love_detective("Остросюжетные любовные романы", GlobalGenre.Love),
    love_hard("Порно", GlobalGenre.Love),
    love_rus("Русская литература о любви", GlobalGenre.Love),
    sexual_perversion("Сексуальные извращения", GlobalGenre.Love),
    love_slash("Слэш", GlobalGenre.Love),
    love_su("Советская литература о любви", GlobalGenre.Love),
    love_contemporary("Современные любовные романы", GlobalGenre.Love),
    love_femslash("Фемслеш", GlobalGenre.Love),
    love_erotica("Эротика", GlobalGenre.Love),


    // Наука, Образование: прочее
    sci_medicine_alternative("Альтернативная медицина", GlobalGenre.Science ),
    science_history("История науки", GlobalGenre.Science ),
    science("Научная литература", GlobalGenre.Science ),
    sci_popular("Научно-популярная и научно-художественная литература", GlobalGenre.Science ),
    sci_theories("Паранаука, псевдонаука, альтернативные теории", GlobalGenre.Science ),


    // Науки естественные
    sci_cosmos("Астрономия и Космос", GlobalGenre.SciEst),
    sci_veterinary("Ветеринария", GlobalGenre.SciEst),
    sci_geography("География и другие науки о Земле", GlobalGenre.SciEst),
    sci_geo("Геологические науки и горное дело", GlobalGenre.SciEst),
    sci_math("Математика", GlobalGenre.SciEst),
    sci_medicine("Медицина", GlobalGenre.SciEst),
    sci_phys("Физика", GlobalGenre.SciEst),
    sci_chem("Химия", GlobalGenre.SciEst),


    // Науки о живой природе
    sci_biology("Биология", GlobalGenre.SciNature),
    sci_biophys("Биофизика", GlobalGenre.SciNature),
    sci_biochem("Биохимия", GlobalGenre.SciNature),
    sci_botany("Ботаника", GlobalGenre.SciNature),
    sci_zoo("Зоология", GlobalGenre.SciNature),
    sci_paleontology("Палеонтология", GlobalGenre.SciNature),
    sci_evolutionism("Эволюционизм", GlobalGenre.SciNature),
    sci_ecology("Экология и защита природы", GlobalGenre.SciNature),


    // Науки общественные и гуманитарные
    sci_oriental("Востоковедение", GlobalGenre.SciSocial),
    sci_state("Государство и право", GlobalGenre.SciSocial),
    foreign_language("Иностранные языки", GlobalGenre.SciSocial),
    local_lore_study("Краеведение", GlobalGenre.SciSocial),
    sci_philology("Литературоведение", GlobalGenre.SciSocial),
    sci_pedagogy("Педагогика", GlobalGenre.SciSocial),
    sci_politics("Политика и дипломатия", GlobalGenre.SciSocial),
    sci_social_studies("Социология", GlobalGenre.SciSocial),
    sci_philosophy("Философия", GlobalGenre.SciSocial),
    sci_juris("Юриспруденция", GlobalGenre.SciSocial),
    sci_linguistic("Языкознание", GlobalGenre.SciSocial),


    // Поэзия
    fable("Басни", GlobalGenre.Poetry),
    in_verse("в стихах", GlobalGenre.Poetry),
    vers_libre("Верлибры", GlobalGenre.Poetry),
    visual_poetry("Визуальная поэзия", GlobalGenre.Poetry),
    poetry_for_classical("Классическая зарубежная поэзия", GlobalGenre.Poetry),
    poetry_classical("Классическая поэзия", GlobalGenre.Poetry),
    poetry_rus_classical("Классическая русская поэзия", GlobalGenre.Poetry),
    lyrics("Лирика", GlobalGenre.Poetry),
    palindromes("Палиндромы", GlobalGenre.Poetry),
    song_poetry("Песенная поэзия", GlobalGenre.Poetry),
    poetry("Поэзия", GlobalGenre.Poetry),
    poetry_east("Поэзия Востока", GlobalGenre.Poetry),
    poem("Поэма", GlobalGenre.Poetry),
    poetry_su("Советская поэзия", GlobalGenre.Poetry),
    poetry_for_modern("Современная зарубежная поэзия", GlobalGenre.Poetry),
    poetry_modern("Современная поэзия", GlobalGenre.Poetry),
    poetry_rus_modern("Современная русская поэзия", GlobalGenre.Poetry),
    poetry_military("Стихи о войне", GlobalGenre.Poetry),
    experimental_poetry("Экспериментальная поэзия", GlobalGenre.Poetry),
    epic_poetry("Эпическая поэзия", GlobalGenre.Poetry),


    // Приключения
    adv_story("Авантюрный роман", GlobalGenre.Adventure),
    adv_western("Вестерн", GlobalGenre.Adventure),
    adv_military("Военные приключения", GlobalGenre.Adventure),
    foreign_adventure("Зарубежная приключенческая литература", GlobalGenre.Adventure),
    adv_history("Исторические приключения", GlobalGenre.Adventure),
    adv_maritime("Морские приключения", GlobalGenre.Adventure),
    adventure("Приключения", GlobalGenre.Adventure),
    adv_modern("Приключения в современном мире", GlobalGenre.Adventure),
    adv_indian("Приключения про индейцев", GlobalGenre.Adventure),
    adv_animal("Природа и животные", GlobalGenre.Adventure),
    adv_geo("Путешествия и география", GlobalGenre.Adventure),
    adv_rus("Русская приключенческая литература", GlobalGenre.Adventure),
    tale_chivalry("Рыцарский роман", GlobalGenre.Adventure),
    adv_su("Советская приключенческая литература", GlobalGenre.Adventure),


    // Проза
    aphorisms("Афоризмы и цитаты", GlobalGenre.Prose),
    in_prose("В прозе", GlobalGenre.Prose),
    prose_military("Военная проза", GlobalGenre.Prose),
    foreign_prose("Зарубежная классическая проза", GlobalGenre.Prose),
    foreign_contemporary("Зарубежная современная проза", GlobalGenre.Prose),
    prose_history("Историческая проза", GlobalGenre.Prose),
    prose_classic("Классическая проза", GlobalGenre.Prose),
    prose_counter("Контркультура", GlobalGenre.Prose),
    prose_magic("Магический реализм", GlobalGenre.Prose),
    prose("Проза", GlobalGenre.Prose),
    prose_rus_classic("Русская классическая проза", GlobalGenre.Prose),
    russian_contemporary("Русская современная проза", GlobalGenre.Prose),
    sagas("Семейный роман/Семейная сага", GlobalGenre.Prose),
    prose_sentimental("Сентиментальная проза", GlobalGenre.Prose),
    prose_su_classics("Советская проза", GlobalGenre.Prose),
    prose_contemporary("Современная проза", GlobalGenre.Prose),
    prose_abs("Фантасмагория, абсурдистская проза", GlobalGenre.Prose),
    extravaganza("Феерия", GlobalGenre.Prose),
    prose_neformatny("Экспериментальная, неформатная проза", GlobalGenre.Prose),
    epistolary_fiction("Эпистолярная проза", GlobalGenre.Prose),
    prose_epic("Эпопея", GlobalGenre.Prose),

    // Прочее
    dissident("Антисоветская литература", GlobalGenre.Other),
    bestseller("Бестселлеры", GlobalGenre.Other),
    in_retelling("В пересказе, в лит. обработке", GlobalGenre.Other),
    in_reduction("В сокращении", GlobalGenre.Other),
    diafilm("Диафильм", GlobalGenre.Other),
    adult("Для взрослых", GlobalGenre.Other),
    invalid("Для людей с ограниченными возможностями", GlobalGenre.Other),
    addition("Дополнительные материалы к книге", GlobalGenre.Other),
    prose_game("Книга-игра", GlobalGenre.Other),
    comics("Комикс", GlobalGenre.Other),
    crossover("Кроссовер", GlobalGenre.Other),
    literary_fairy_tale("Литературные сказки", GlobalGenre.Other),
    fan_translation("Любительские переводы", GlobalGenre.Other),
    beginning_authors("Начинающие авторы", GlobalGenre.Other),
    unfinished("Недописанное", GlobalGenre.Other),
    other("Неотсортированное", GlobalGenre.Other),
    novelization("Новеллизации", GlobalGenre.Other),
    fragment("Отрывок, ознакомительный фрагмент", GlobalGenre.Other),
    presentation("Презентация", GlobalGenre.Other),
    outdated("Устаревшие материалы", GlobalGenre.Other),
    fanfiction("Фанфик", GlobalGenre.Other),

    // есть такие жанры, но не нашел их описания, поэтому добавил сюда
    foreign_home("foreign_home", GlobalGenre.Other),
    foreign_psychology("foreign_psychology", GlobalGenre.Other),
    upbringing_book("upbringing_book", GlobalGenre.Other),
    geography_book("geography_book", GlobalGenre.Other),


    // Психология и психиатрия
    sci_hypnosis("Гипноз, внушение и самовнушение", GlobalGenre.Psychology),
    psy_childs("Детская психология", GlobalGenre.Psychology),
    sci_psychiatry("Психиатрия и наркология", GlobalGenre.Psychology),
    sci_psychology("Психология", GlobalGenre.Psychology),
    sci_tech_ergonomics("Психология труда, инженерная психология и эргономика", GlobalGenre.Psychology),
    psy_theraphy("Психотерапия и консультирование", GlobalGenre.Psychology),
    psy_sex_and_family("Секс и семейная психология", GlobalGenre.Psychology),
    psy_personal("Персональная психология", GlobalGenre.Psychology),
    psy_social("Соционика (?)", GlobalGenre.Psychology),


    // Религия и духовность
    astrology("Астрология", GlobalGenre.Religion),
    atheism("Атеизм", GlobalGenre.Religion),
    religion_budda("Буддизм", GlobalGenre.Religion),
    religion_hinduism("Индуизм", GlobalGenre.Religion),
    religion_islam("Ислам", GlobalGenre.Religion),
    religion_judaism("Иудаизм", GlobalGenre.Religion),
    religion_catholicism("Католицизм", GlobalGenre.Religion),
    religion_orthodoxy("Православие", GlobalGenre.Religion),
    religion_protestantism("Протестантизм", GlobalGenre.Religion),
    sci_religion("Религиоведение", GlobalGenre.Religion),
    religion_rel("Религия", GlobalGenre.Religion),
    religion("Религия и духовность: прочее", GlobalGenre.Religion),
    religion_self("Самосовершенствование", GlobalGenre.Religion),
    palmistry("Хиромантия", GlobalGenre.Religion),
    religion_christianity("Христианство", GlobalGenre.Religion),
    religion_esoterics("Эзотерика, мистицизм, оккультизм", GlobalGenre.Religion),
    religion_paganism("Язычество", GlobalGenre.Religion),


    // Справочная литература
    geo_guides("Путеводители", GlobalGenre.Guide),
    ref_guide("Руководства", GlobalGenre.Guide),
    ref_self_tutor("Самоучители", GlobalGenre.Guide),
    ref_dict("Словари", GlobalGenre.Guide),
    reference("Справочная литература: прочее", GlobalGenre.Guide),
    ref_ref("Справочники", GlobalGenre.Guide),
    ref_encyc("Энциклопедии", GlobalGenre.Guide),


    // Старинное
    antique_ant("Античная литература", GlobalGenre.Antique),
    antique_east("Древневосточная литература", GlobalGenre.Antique),
    antique_european("Древнеевропейская литература", GlobalGenre.Antique),
    antique_russian("Древнерусская литература", GlobalGenre.Antique),
    antique("Старинная литература", GlobalGenre.Antique),


    // Техника - 33
    sci_tech_rockets("Авиация, ракетная и космическая техника", GlobalGenre.Technical),
    sci_tech_robot("Автоматизация, робототехника, мехатроника", GlobalGenre.Technical),
    auto_business("Автостроение, автодело, тракторная техника", GlobalGenre.Technical),
    sci_aerodynamics("Аэро-, газо- и гидродинамика", GlobalGenre.Technical),
    sci_tech_protection("Безопасность жизнедеятельности и охрана труда", GlobalGenre.Technical),
    sci_tech_hydraulics("Гидравлика, пневматика", GlobalGenre.Technical),
    sci_tech_machine_parts("Детали машин", GlobalGenre.Technical),
    sci_tech_reliability("Диагностика, неразрушающий контроль и надежность", GlobalGenre.Technical),
    equ_history("История техники", GlobalGenre.Technical),
    sci_engineering("Конструирование, изобретательство, рационализаторство", GlobalGenre.Technical),
    sci_tech_materials("Материаловедение, конструкционные и прочие материалы", GlobalGenre.Technical),
    sci_tech_machinery("Машиностроение и металлообработка", GlobalGenre.Technical),
    sci_metal("Металлургия", GlobalGenre.Technical),
    sci_tech_metrology("Метрология, стандартизация и сертификация", GlobalGenre.Technical),
    sci_tech_mech("Механика", GlobalGenre.Technical),
    sci_tech_nano("Наноматериалы и нанотехнологии", GlobalGenre.Technical),
    sci_tech_drawing("Начертательная геометрия, инженерная графика, черчение", GlobalGenre.Technical),
    sci_tech_oil("Нефтегазовая и угольная промышленности", GlobalGenre.Technical),
    sci_tech_standards("Нормативно-техническая документация", GlobalGenre.Technical),
    sci_tech_print("Полиграфическое и упаковочное производство", GlobalGenre.Technical),
    sci_instrumentation("Приборостроение", GlobalGenre.Technical),
    sci_radio("Радиоэлектроника, радиотехника, связь", GlobalGenre.Technical),
    sci_tech_assembling("Сборка, неразъемное соединение", GlobalGenre.Technical),
    sci_tech_sopromat("Строительная механика и сопромат", GlobalGenre.Technical),
    sci_build("Строительство и монтажные работы", GlobalGenre.Technical),
    sci_fleet("Судостроение, флот", GlobalGenre.Technical),
    sci_tech_theormech("Теория механизмов и машин", GlobalGenre.Technical),
    sci_thermodynamics("Термодинамика, теплопередача, теплотехника", GlobalGenre.Technical),
    sci_tech("Технические науки", GlobalGenre.Technical),
    sci_transport("Транспорт и спецтехника", GlobalGenre.Technical),
    sci_tech_chem("Химическая и нефтехимическая промышленности", GlobalGenre.Technical),
    sci_electronics("Электроника, микроэлектроника, схемотехника", GlobalGenre.Technical),
    sci_energy("Энергетика, электротехника", GlobalGenre.Technical),


    // Учебники и пособия
    sci_thesis("Диссертации, дипломные, курсовые и прочие работы", GlobalGenre.SchoolBook),
    sci_textbook_su("Советские учебники и пособия", GlobalGenre.SchoolBook),
    sci_abstract("Статьи и рефераты", GlobalGenre.SchoolBook),
    tbg_higher("Учебники и пособия ВУЗов", GlobalGenre.SchoolBook),
    tbg_secondary("Учебники и пособия для среднего и специального образования", GlobalGenre.SchoolBook),
    sci_textbook("Учебники и пособия: прочее", GlobalGenre.SchoolBook),
    tbg_school("Школьные учебники и пособия", GlobalGenre.SchoolBook),
    sci_crib("Шпаргалки", GlobalGenre.SchoolBook),


    // Фантастика
    sf_history("Альтернативная история", GlobalGenre.Fantastic),
    dystopian("Антиутопия", GlobalGenre.Fantastic),
    sf_action("Боевая фантастика", GlobalGenre.Fantastic),
    sf_heroic("Героическая фантастика", GlobalGenre.Fantastic),
    sf_detective("Детективная фантастика", GlobalGenre.Fantastic),
    foreign_sf("Зарубежная фантастика", GlobalGenre.Fantastic),
    sf_irony("Ироническая фантастика", GlobalGenre.Fantastic),
    sf_cyberpunk("Киберпанк", GlobalGenre.Fantastic),
    sf_space("Космическая фантастика", GlobalGenre.Fantastic),
    sf_space_opera("Космоопера", GlobalGenre.Fantastic),
    sf_litrpg("ЛитРПГ", GlobalGenre.Fantastic),
    sf_mystic("Мистика", GlobalGenre.Fantastic),
    sf("Научная Фантастика", GlobalGenre.Fantastic),
    nsf("Ненаучная Фантастика", GlobalGenre.Fantastic),
    sf_paleontological("Палеонтологическая фантастика", GlobalGenre.Fantastic),
    popadanec("Попаданцы", GlobalGenre.Fantastic),
    sf_postapocalyptic("Постапокалипсис", GlobalGenre.Fantastic),
    sf_realrpg("РеалРПГ", GlobalGenre.Fantastic),
    sf_rus("Российская фантастика", GlobalGenre.Fantastic),
    sf_su("Советская фантастика", GlobalGenre.Fantastic),
    sf_social("Социальная фантастика", GlobalGenre.Fantastic),
    sf_stimpank("Стимпанк", GlobalGenre.Fantastic),
    sf_horror("Ужасы", GlobalGenre.Fantastic),
    sf_etc("Фантастика: прочее", GlobalGenre.Fantastic),
    hronoopera("Хроноопера", GlobalGenre.Fantastic),
    sf_epic("Эпическая фантастика", GlobalGenre.Fantastic),
    sf_humor("Юмористическая фантастика", GlobalGenre.Fantastic),


    // Фольклор
    epic("Былины", GlobalGenre.Folklore),
    riddles("Загадки", GlobalGenre.Folklore),
    antique_myths("Мифы. Легенды. Эпос", GlobalGenre.Folklore),
    folk_songs("Народные песни", GlobalGenre.Folklore),
    folk_traditions("Народные приметы, обряды, традиции", GlobalGenre.Folklore),
    folk_tale("Народные сказки", GlobalGenre.Folklore),
    proverbs("Пословицы, поговорки", GlobalGenre.Folklore),
    folklore_rus("Русский фольклор", GlobalGenre.Folklore),
    folklore("Фольклор: прочее", GlobalGenre.Folklore),
    limerick("Частушки, прибаутки, потешки", GlobalGenre.Folklore),


    // Формы произведений
    autor_collection("Авторские сборники, собрания сочинений", GlobalGenre.StoryForm),
    periodic("Газеты и журналы", GlobalGenre.StoryForm),
    compilation("Компиляции", GlobalGenre.StoryForm),
    postcards("Наборы открыток, календари", GlobalGenre.StoryForm),
    story("Новелла", GlobalGenre.StoryForm),
    notes("Партитуры", GlobalGenre.StoryForm),
    great_story("Повесть", GlobalGenre.StoryForm),
    short_story("Рассказ", GlobalGenre.StoryForm),
    roman("Роман", GlobalGenre.StoryForm),
    collection("Сборники, альманахи, антологии", GlobalGenre.StoryForm),
    article("Статья", GlobalGenre.StoryForm),
    essay("Эссе, очерк, этюд, набросок", GlobalGenre.StoryForm),
    essays("Эссе", GlobalGenre.StoryForm),


    // Фэнтези - 22
    fantasy_fight("Боевое фэнтези", GlobalGenre.Fantasy),
    fantasy_heroic("Героическое фэнтези", GlobalGenre.Fantasy),
    sf_fantasy_city("Городское фэнтези", GlobalGenre.Fantasy),
    gothic_novel("Готический роман", GlobalGenre.Fantasy),
    foreign_fantasy("Зарубежное фэнтези", GlobalGenre.Fantasy),
    sf_fantasy_irony("Ироническое фэнтези", GlobalGenre.Fantasy),
    historical_fantasy("Историческое фэнтези", GlobalGenre.Fantasy),
    magician_book("Магическое фэнтези", GlobalGenre.Fantasy),
    vampire_book("О вампирах", GlobalGenre.Fantasy),
    dragon_fantasy("О драконах", GlobalGenre.Fantasy),
    adventure_fantasy("Приключенческое фэнтези", GlobalGenre.Fantasy),
    fantasy_rus("Российское фэнтези", GlobalGenre.Fantasy),
    fairy_fantasy("Сказочная фантастика", GlobalGenre.Fantasy),
    russian_fantasy("Славянское фэнтези", GlobalGenre.Fantasy),
    modern_tale("Современная сказка", GlobalGenre.Fantasy),
    fantasy_dark("Темное фэнтези", GlobalGenre.Fantasy),
    sf_technofantasy("Технофэнтези", GlobalGenre.Fantasy),
    fantasy_wuxia("Уся", GlobalGenre.Fantasy),
    sf_fantasy("Фэнтези: прочее", GlobalGenre.Fantasy),
    fantasy_epic("Эпическое фэнтези", GlobalGenre.Fantasy),
    erotic_fantasy("Эротическое фэнтези", GlobalGenre.Fantasy),
    humor_fantasy("Юмористическое фэнтези", GlobalGenre.Fantasy),


    // Хобби и ремесла - 27
    auto_regulations("Авто-, мото- и велотранспорт, ПДД", GlobalGenre.Hobby),
    home_aquarium("Аквариумистика", GlobalGenre.Hobby),
    home_mountain("Альпинизм и скалолазание", GlobalGenre.Hobby),
    publishing("Библиотечное и редакционно-издательское дело", GlobalGenre.Hobby),
    home_winemaking("Виноделие, спиртные напитки", GlobalGenre.Hobby),
    home_livestock("Животноводство и птицеводство", GlobalGenre.Hobby),
    home_furniture("Изготовление и ремонт мебели", GlobalGenre.Hobby),
    home_inventory("Инвентарь, инструменты", GlobalGenre.Hobby),
    home_building("Индивидуальное строительство и ремонт", GlobalGenre.Hobby),
    home_bookmaking("Книгоделие", GlobalGenre.Hobby),
    home_collecting("Коллекционирование", GlobalGenre.Hobby),
    home_modelling("Моделизм и диорамостроение", GlobalGenre.Hobby),
    home_marine("Морское дело, парусный спорт", GlobalGenre.Hobby),
    home_hunt("Охота и охотоведение", GlobalGenre.Hobby),
    home_writing_art("Писательское искусство", GlobalGenre.Hobby),
    home_beekeeping("Пчеловодство", GlobalGenre.Hobby),
    home_woodwork("Работа по дереву", GlobalGenre.Hobby),
    home_metalwork("Работа по металлу", GlobalGenre.Hobby),
    home_handiwork("Рукоделие", GlobalGenre.Hobby),
    home_fishing("Рыболовство и рыбоводство", GlobalGenre.Hobby),
    home_garden("Сад и огород", GlobalGenre.Hobby),
    home_mushrooms("Сбор и выращивание грибов", GlobalGenre.Hobby),
    home_diy("Сделай сам", GlobalGenre.Hobby),
    home_tourism("Туризм", GlobalGenre.Hobby),
    home_sport("Физкультура и спорт", GlobalGenre.Hobby),
    home_crafts("Хобби и ремесла: прочее", GlobalGenre.Hobby),
    home_floriculture("Цветоводство и комнатное садоводство", GlobalGenre.Hobby),


    // Юмор
    humor_anecdote("Анекдоты", GlobalGenre.Humor),
    humor_tales("Байки", GlobalGenre.Humor),
    humor_parody("Пародии, шаржи, эпиграммы, карикатуры", GlobalGenre.Humor),
    humor_satire("Сатира", GlobalGenre.Humor),
    humor("Юмор: прочее", GlobalGenre.Humor),
    humor_prose("Юмористическая проза", GlobalGenre.Humor),
    humor_verse("Юмористические стихи", GlobalGenre.Humor),

    // Неизвестный жанр или отсутствует
    unknown("Неизвестный", GlobalGenre.Unknown),
    ;

    private final GlobalGenre globalGenre; // enum - Детективы, Фантастика...

    private final String title;

    Genre(String title, GlobalGenre globalGenre) {
        this.title = title;
        this.globalGenre = globalGenre;
    }

    public static Genre getGenre(String genre) {
        if (genre != null) {
            for (Genre g: values()) {
                if (genre.equals(g.getTitle())) return g;
            }
        }
        return null;
    }

    public String getTitle() {
        return title;
    }

    public GlobalGenre getGlobalGenre() {
        return globalGenre;
    }

}
