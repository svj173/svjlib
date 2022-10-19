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


    // Психология и психиатрия
    sci_hypnosis("Гипноз, внушение и самовнушение", GlobalGenre.Psychology),
    psy_childs("Детская психология", GlobalGenre.Psychology),
    sci_psychiatry("Психиатрия и наркология", GlobalGenre.Psychology),
    sci_psychology("Психология", GlobalGenre.Psychology),
    sci_tech_ergonomics("Психология труда, инженерная психология и эргономика", GlobalGenre.Psychology),
    psy_theraphy("Психотерапия и консультирование", GlobalGenre.Psychology),
    psy_sex_and_family("Секс и семейная психология", GlobalGenre.Psychology),


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


    /*
Античная литература (antique_ant) - 446
Древневосточная литература (antique_east) - 534 [1]
Древнеевропейская литература (antique_european) - 605

Древнерусская литература (antique_russian) - 170
Старинная литература (antique) - 538
     */
    // Старинное
    sf_history("Альтернативная история", GlobalGenre.Antique),


    sf_history("Альтернативная история", GlobalGenre.Fantastic),
    sf_action("Боевая Фантастика", GlobalGenre.Fantastic),
    sf_epic("Эпическая Фантастика", GlobalGenre.Fantastic),
    ;

    private final GlobalGenre globalGenre; // enum - Детективы, Фантастика...

    private final String title;

    Genre(String title, GlobalGenre globalGenre) {
        this.title = title;
        this.globalGenre = globalGenre;
    }

    public String getTitle() {
        return title;
    }

    public GlobalGenre getGlobalGenre() {
        return globalGenre;
    }

    /*

sf_heroic               Героическая фантастика
sf_detective            Детективная Фантастика
sf_cyberpunk            Киберпанк
sf_space                Космическая Фантастика
sf_social               Социальная фантастика
sf_horror               Ужасы и Мистика
sf_humor                Юмористическая фантастика
sf_fantasy              Фэнтези
sf                      Научная Фантастика
child_sf                Детская Фантастика

det_classic             Классический Детектив
det_police              Полицейский Детектив
det_action              Боевики
det_irony               Иронический Детектив
det_history             Исторический Детектив
det_espionage           Шпионский Детектив
det_crime               Криминальный Детектив
det_political           Политический Детектив
det_maniac              Маньяки
det_hard                Крутой Детектив
thriller                Триллеры
detective               Детектив
sf_detective            Детективная Фантастика

child_det               Детские Остросюжетные
love_detective          Остросюжетные Любовные Романы
prose                   Проза
prose_classic           Классическая Проза
prose_history           Историческая Проза
prose_contemporary      Современная Проза
prose_counter           Контркультура
prose_rus_classic       Русская Классика
prose_su_classics       Советская Классика
humor_prose             Юмористическая Проза
child_prose             Детская Проза
love                    Любовные романы
love_contemporary       Современные Любовные Романы
love_history            Исторические Любовные Романы
love_detective          Остросюжетные Любовные Романы
love_short              Короткие Любовные Романы
love_erotica            Эротика
adv_western             Вестерны
adv_history             Исторические Приключения
adv_indian              Приключения: Индейцы
adv_maritime            Морские Приключения
adv_geo                 Путешествия и География
adv_animal              Природа и Животные
adventure               Приключения: Прочее
child_adv               Детские Приключения
children                Детское
child_tale              Сказки
child_verse             Детские Стихи
child_prose             Детская Проза
child_sf                Детская Фантастика
child_det               Детские Остросюжетные
child_adv               Детские Приключения
child_education         Детская Образовательная литература
children                Детское: Прочее
poetry                  Поэзия
dramaturgy              Драматургия
humor_verse             Юмористические Стихи
child_verse             Детские Стихи
antique_ant             Античная Литература
antique_european        Европейская Старинная Литература
antique_russian         Древнерусская Литература
antique_east            Древневосточная Литература
antique_myths           Мифы. Легенды. Эпос
antique                 Старинная Литература: Прочее
sci_history             История
sci_psychology          Психология
sci_culture             Культурология
sci_religion            Религиоведение
sci_philosophy          Философия
sci_politics            Политика
sci_business            Деловая литература
sci_juris               Юриспруденция
sci_linguistic          Языкознание
sci_medicine            Медицина
sci_phys                Физика
sci_math                Математика
sci_chem                Химия
sci_biology             Биология
sci_tech                Технические
science                 Научно-образовательная: Прочее
adv_animal              Природа и Животные
comp_www                Интернет
comp_programming        Программирование
comp_hard               Компьютерное Железо
comp_soft               Программы
comp_db                 Базы Данных
comp_osnet              ОС и Сети
computers               Компьютеры: Прочее
ref_encyc               Энциклопедии
ref_dict                Словари
ref_ref                 Справочники
ref_guide               Руководства
reference               Справочная Литература: Прочее
nonf_biography          Биографии и Мемуары
nonf_publicism          Публицистика
nonf_criticism          Критика
nonfiction              Документальное: Прочее
design                  Искусство, Дизайн
adv_animal              Природа и Животные
religion                Религия
religion_rel            Религия
religion_esoterics      Эзотерика
religion_self           Самосовершенствование
religion                Религия и духовность: Прочее
sci_religion            Религиоведение
humor_anecdote          Анекдоты
humor_prose             Юмористическая Проза
humor_verse             Юмористические Стихи
humor                   Юмор: Прочее
home_cooking            Кулинария
home_pets               Домашние Животные
home_crafts             Хобби, Ремесла
home_entertain          Развлечения
home_health             Здоровье
home_garden             Сад и Огород
home_diy                Сделай Сам
home_sport              Спорт
home_sex                Эротика, Секс
home                    Дом и Семья: Прочее

     */
}
