package me.nexadn.unitedshops;

public class RichString {
    private String string;

    public RichString(String base) {
        this.string = base;
    }

    public RichString arg(String arg, String content) {
        this.string = this.string.replaceAll("\\$\\{" + arg + "\\}", content);
        return this;
    }

    public String str() {
        this.string = this.string.replaceAll("\\$\\{.+\\}", "EMPTY");
        return this.string;
    }

    @Override
    public String toString() {
        return this.str();
    }
}
