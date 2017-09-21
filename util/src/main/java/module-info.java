module CoffeeTime.util {
    requires kotlin.stdlib;
    requires kotlin.reflect;
    requires java.desktop;
    requires java.logging;
    requires java.prefs;

    exports gargoyle.ct.util.args;
    exports gargoyle.ct.util.args.impl;
    exports gargoyle.ct.util.convert;
    exports gargoyle.ct.util.log;
    exports gargoyle.ct.util.messages;
    exports gargoyle.ct.util.messages.impl;
    exports gargoyle.ct.util.mutex;
    exports gargoyle.ct.util.mutex.impl;
    exports gargoyle.ct.util.pref;
    exports gargoyle.ct.util.pref.impl;
    exports gargoyle.ct.util.pref.prop;
    exports gargoyle.ct.util.prop;
    exports gargoyle.ct.util.prop.impl;
    exports gargoyle.ct.util.resource;
    exports gargoyle.ct.util.resource.internal;
    exports gargoyle.ct.util.sound;
    exports gargoyle.ct.util.util;
    exports gargoyle.ct.util.ver;
    exports gargoyle.ct.util.ver.impl;

    exports gargoyle.ct.util.log.jul to kotlin.reflect;
}
