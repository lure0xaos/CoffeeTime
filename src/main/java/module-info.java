module CoffeeTime {
    requires kotlin.stdlib;
    requires kotlin.stdlib.jdk8;
    requires kotlin.reflect;
    requires java.desktop;
    requires java.logging;
    requires java.prefs;
    requires org.jetbrains.annotations;

    exports gargoyle.ct.convert.impl to kotlin.reflect;

}
