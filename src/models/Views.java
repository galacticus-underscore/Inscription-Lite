package models;
public enum Views {
    START("path_1"),
    OPTIONS("path_2"),
    LOADING("path_3"),
    CONFIRM("path_4"),
    GAME("path_5");

    private String path;

    private Views(String p) {
        this.path = p;
    }

    public String getPath() {
        return this.path;
    }
}