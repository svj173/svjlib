package svj.svjlib.svjlibs.obj;

/**
 * <BR/>
 */
public enum GlobalGenre {
    // todo взять из myrulib
    Fantastic("Фантастика"),
    Detektiv("Детективы"),
    ;

    private final String title;

    GlobalGenre(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
