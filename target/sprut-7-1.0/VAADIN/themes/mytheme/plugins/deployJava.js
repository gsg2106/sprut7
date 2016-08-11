var deployJava = function () {
    var l = {
        core : ["id", "class", "title", "style"], i18n : ["lang", "dir"], events : ["onclick", "ondblclick", "onmousedown", "onmouseup", "onmouseover", "onmousemove", "onmouseout", "onkeypress", "onkeydown", "onkeyup"], applet : ["codebase", "code", "name", "archive", "object", "width", "height", "alt", "align", "hspace", "vspace"], object : ["classid", "codebase", "codetype", "data", "type", "archive", "declare", "standby", "height", "width", "usemap", "name", "tabindex", "align", "border", "hspace", "vspace"]
    };
    var b = l.object.concat(l.core, l.i18n, l.events);
    var m = l.applet.concat(l.core);
    function g(n) {
        if (!d.debug) {
            return 
        }
        if (console.log) {
            console.log(n)
        }
        else {
            alert(n)
        }
    }
    function k(o, n) {
        if (o == null || o.length == 0) {
            return true
        }
        var q = o.charAt(o.length - 1);
        if (q != "+" && q != "*" && (o.indexOf("_") !=  - 1 && q != "_")) {
            o = o + "*";
            q = "*"
        }
        o = o.substring(0, o.length - 1);
        if (o.length > 0) {
            var p = o.charAt(o.length - 1);
            if (p == "." || p == "_") {
                o = o.substring(0, o.length - 1)
            }
        }
        if (q == "*") {
            return (n.indexOf(o) == 0)
        }
        else {
            if (q == "+") {
                return o <= n
            }
        }
        return false
    }
    function e() {
        var n = "//java.com/js/webstart.png";
        try {
            return document.location.protocol.indexOf("http") !=  - 1 ? n : "http:" + n
        }
        catch (o) {
            return "http:" + n
        }
    }
    function j(p, o) {
        var n = p.length;
        for (var q = 0;q < n;q++) {
            if (p[q] === o) {
                return true
            }
        }
        return false
    }
    function c(n) {
        return j(m, n.toLowerCase())
    }
    function i(n) {
        return j(b, n.toLowerCase())
    }
    function a(n) {
        if ("MSIE" != deployJava.browserName) {
            return true
        }
        if (deployJava.compareVersionToPattern(deployJava.getPlugin().version, ["10", "0", "0"], false, true)) {
            return true
        }
        if (n == null) {
            return false
        }
        return !k("1.6.0_33+", n)
    }
    var d = {
        debug : null, version : "20120801", firefoxJavaVersion : null, myInterval : null, preInstallJREList : null, returnPage : null, brand : null, locale : null, installType : null, EAInstallEnabled : false, EarlyAccessURL : null, getJavaURL : "http://jdl.sun.com/webapps/getjava/BrowserRedirect?host=java.com", oldMimeType : "application/npruntime-scriptable-plugin;DeploymentToolkit", mimeType : "application/java-deployment-toolkit", browserName : null, browserName2 : null, getJREs : function () {
            var r = new Array();
            if (this.isPluginInstalled()) {
                var q = this.getPlugin();
                var n = q.jvms;
                for (var p = 0;p < n.getLength();p++) {
                    r[p] = n.get(p).version
                }
            }
            else {
                var o = this.getBrowser();
                if ((o.indexOf("MSIE")!=-1)||(o.indexOf("Trident")!=-1)) {
                    if (this.testUsingActiveX("1.9.0")) {
                        r[0] = "1.9.0"
                    } else if (this.testUsingActiveX("1.8.0")) {
                        r[0] = "1.8.0"
                    }
                    else if (this.testUsingActiveX("1.7.0")) {
                        r[0] = "1.7.0"
                    }
                    else {
                        if (this.testUsingActiveX("1.6.0")) {
                            r[0] = "1.6.0"
                        }
                        else {
                            if (this.testUsingActiveX("1.5.0")) {
                                r[0] = "1.5.0"
                            }
                            else {
                                if (this.testUsingActiveX("1.4.2")) {
                                    r[0] = "1.4.2"
                                }
                                else {
                                    if (this.testForMSVM()) {
                                        r[0] = "1.1"
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    if (o == "Netscape Family") {
                        this.getJPIVersionUsingMimeType();
                        if (this.firefoxJavaVersion != null) {
                            r[0] = this.firefoxJavaVersion
                        }
                        else {
                            if (this.testUsingActiveX("1.9.0")) {
                                r[0] = "1.9.0"
                            } else if (this.testUsingActiveX("1.8.0")) {
                                r[0] = "1.8.0"
                            }
                            else if (this.testUsingMimeTypes("1.7")) {
                                r[0] = "1.7.0"
                            }
                            else {
                                if (this.testUsingMimeTypes("1.6")) {
                                    r[0] = "1.6.0"
                                }
                                else {
                                    if (this.testUsingMimeTypes("1.5")) {
                                        r[0] = "1.5.0"
                                    }
                                    else {
                                        if (this.testUsingMimeTypes("1.4.2")) {
                                            r[0] = "1.4.2"
                                        }
                                        else {
                                            if (this.browserName2 == "Safari") {
                                                if (this.testUsingPluginsArray("1.7.0")) {
                                                    r[0] = "1.7.0"
                                                }
                                                else {
                                                    if (this.testUsingPluginsArray("1.6")) {
                                                        r[0] = "1.6.0"
                                                    }
                                                    else {
                                                        if (this.testUsingPluginsArray("1.5")) {
                                                            r[0] = "1.5.0"
                                                        }
                                                        else {
                                                            if (this.testUsingPluginsArray("1.4.2")) {
                                                                r[0] = "1.4.2"
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (this.debug) {
                for (var p = 0;p < r.length;++p) {
                    g("[getJREs()] We claim to have detected Java SE " + r[p])
                }
            }
            return r
        },        
        isAutoInstallEnabled : function (n) {
            if (!this.isPluginInstalled()) {
                return false
            }
            if (typeof n == "undefined") {
                n = null
            }
            return a(n)
        },              
        versionCheck : function (o) {
            var u = 0;
            var w = "^(\\d+)(?:\\.(\\d+)(?:\\.(\\d+)(?:_(\\d+))?)?)?(\\*|\\+)?$";
            var x = o.match(w);
            if (x != null) {
                var q = false;
                var t = false;
                var p = new Array();
                for (var r = 1;r < x.length;++r) {
                    if ((typeof x[r] == "string") && (x[r] != "")) {
                        p[u] = x[r];
                        u++
                    }
                }
                if (p[p.length - 1] == "+") {
                    t = true;
                    q = false;
                    p.length--
                }
                else {
                    if (p[p.length - 1] == "*") {
                        t = false;
                        q = true;
                        p.length--
                    }
                    else {
                        if (p.length < 4) {
                            t = false;
                            q = true
                        }
                    }
                }
                var v = this.getJREs();
                for (var r = 0;r < v.length;++r) {
                    if (this.compareVersionToPattern(v[r], p, q, t)) {
                        return true
                    }
                }
                return false
            }
            else {
                var n = "Invalid versionPattern passed to versionCheck: " + o;
                g("[versionCheck()] " + n);
                alert(n);
                return false
            }
        },
        isWebStartInstalled : function (q) {
            var p = this.getBrowser();
            if (p == "?") {
                return true
            }
            if (q == "undefined" || q == null) {
                q = "1.4.2"
            }
            var o = false;
            var r = "^(\\d+)(?:\\.(\\d+)(?:\\.(\\d+)(?:_(\\d+))?)?)?$";
            var n = q.match(r);
            if (n != null) {
                o = this.versionCheck(q + "+")
            }
            else {
                g("[isWebStartInstaller()] Invalid minimumVersion argument to isWebStartInstalled(): " + q);
                o = this.versionCheck("1.4.2+")
            }
            return o
        },
        getJPIVersionUsingMimeType : function () {
            for (var o = 0;o < navigator.mimeTypes.length;++o) {
                var p = navigator.mimeTypes[o].type;
                var n = p.match(/^application\/x-java-applet;jpi-version=(.*)$/);
                if (n != null) {
                    this.firefoxJavaVersion = n[1];
                    if ("Opera" != this.browserName2) {
                        break 
                    }
                }
            }
        },        
        isPluginInstalled : function () {
            var n = this.getPlugin();
            if (n && n.jvms) {
                return true
            }
            else {
                return false
            }
        },
        isAutoUpdateEnabled : function () {
            if (this.isPluginInstalled()) {
                return this.getPlugin().isAutoUpdateEnabled()
            }
            return false
        },       
        isPlugin2 : function () {
            if (this.isPluginInstalled()) {
                if (this.versionCheck("1.6.0_10+")) {
                    try {
                        return this.getPlugin().isPlugin2()
                    }
                    catch (n) {
                    }
                }
            }
            return false
        },
        allowPlugin : function () {
            this.getBrowser();
            var n = ("Chrome" != this.browserName2 && "Opera" != this.browserName2);
            return n
        },
        getPlugin : function () {
            this.refresh();
            var n = null;
            if (this.allowPlugin()) {
                n = document.getElementById("deployJavaPlugin")
            }
            return n
        },
        compareVersionToPattern : function (u, o, q, r) {
            if (u == undefined || o == undefined) {
                return false
            }
            var v = "^(\\d+)(?:\\.(\\d+)(?:\\.(\\d+)(?:_(\\d+))?)?)?$";
            var w = u.match(v);
            if (w != null) {
                var t = 0;
                var x = new Array();
                for (var p = 1;p < w.length;++p) {
                    if ((typeof w[p] == "string") && (w[p] != "")) {
                        x[t] = w[p];
                        t++
                    }
                }
                var n = Math.min(x.length, o.length);
                if (r) {
                    for (var p = 0;p < n;++p) {
                        if (x[p].length < o[p].length) {
                            return false;
                        } else if (x[p].length > o[p].length) {
                            return true;
                        }
                        if (x[p] < o[p]) {
                            return false;
                        }
                        else {
                            if (x[p] > o[p]) {
                                return true;
                            }
                        }
                    }
                    return true;
                }
                else {
                    for (var p = 0;p < n;++p) {
                        if (x[p] != o[p]) {
                            return false;
                        }
                    }
                    if (q) {
                        return true;
                    }
                    else {
                        return (x.length == o.length)
                    }
                }
            }
            else {
                return false
            }
        },
        getBrowser : function () {
            if (this.browserName == null) {
                var n = navigator.userAgent.toLowerCase();
                g("[getBrowser()] navigator.userAgent.toLowerCase() -> " + n);
                if (((n.indexOf("msie")!=-1) || (n.indexOf("trident/")!=-1)) && (n.indexOf("opr/") ==  - 1)) {
                    this.browserName = "MSIE";
                    this.browserName2 = "MSIE"
                }
                else {
                    if (n.indexOf("iphone") !=  - 1) {
                        this.browserName = "Netscape Family";
                        this.browserName2 = "iPhone"
                    }
                    else {
                        if ((n.indexOf("firefox") !=  - 1) && (n.indexOf("opr/") ==  - 1)) {
                            this.browserName = "Netscape Family";
                            this.browserName2 = "Firefox"
                        }
                        else {
                            if ((n.indexOf("mozilla") !=  - 1) && (n.indexOf("opr/") ==  - 1)) {
                                        this.browserName = "Netscape Family";
                                        this.browserName2 = "Other"
                            }                            
                            else {
                                if (n.indexOf("opr/") !=  - 1) {
                                            this.browserName = "Netscape Family";
                                            this.browserName2 = "Opera"
                                }                                
                                else {
                                    if (n.indexOf("chrome") !=  - 1) {
                                        this.browserName = "Netscape Family";
                                        this.browserName2 = "Chrome"
                                    }
                                    else {
                                        if (n.indexOf("safari") !=  - 1) {
                                            this.browserName = "Netscape Family";
                                            this.browserName2 = "Safari"
                                        }
                                        else {
                                            this.browserName = "?";
                                            this.browserName2 = "unknown"
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                g("[getBrowser()] Detected browser name:" + this.browserName + ", " + this.browserName2)
            }
            return this.browserName
        },
        testUsingActiveX : function (n) {
            var p = "JavaWebStart.isInstalled." + n + ".0";
            if (typeof ActiveXObject == "undefined" || !ActiveXObject) {
                g("[testUsingActiveX()] Browser claims to be IE, but no ActiveXObject object?");
                return false
            }
            try {
                return (new ActiveXObject(p) != null)
            }
            catch (o) {
                return false
            }
        },
        testForMSVM : function () {
            var o = "{08B0E5C0-4FCB-11CF-AAA5-00401C608500}";
            if (typeof oClientCaps != "undefined") {
                var n = oClientCaps.getComponentVersion(o, "ComponentID");
                if ((n == "") || (n == "5,0,5000,0")) {
                    return false
                }
                else {
                    return true
                }
            }
            else {
                return false
            }
        },
        testUsingMimeTypes : function (o) {
            if (!navigator.mimeTypes) {
                g("[testUsingMimeTypes()] Browser claims to be Netscape family, but no mimeTypes[] array?");
                return false
            }
            for (var p = 0;p < navigator.mimeTypes.length;++p) {
                s = navigator.mimeTypes[p].type;
                var n = s.match(/^application\/x-java-applet\x3Bversion=(1\.8|1\.7|1\.6|1\.5|1\.4\.2)$/);
                if (n != null) {
                    if (this.compareVersions(n[1], o)) {
                        return true
                    }
                }
            }
            return false
        },
        testUsingPluginsArray : function (o) {
            if ((!navigator.plugins) || (!navigator.plugins.length)) {
                return false
            }
            var n = navigator.platform.toLowerCase();
            for (var p = 0;p < navigator.plugins.length;++p) {
                s = navigator.plugins[p].description;
                if (s.search(/^Java Switchable Plug-in (Cocoa)/) !=  - 1) {
                    if (this.compareVersions("1.5.0", o)) {
                        return true
                    }
                }
                else {
                    if (s.search(/^Java/) !=  - 1) {
                        if (n.indexOf("win") !=  - 1) {
                            if (this.compareVersions("1.5.0", o) || this.compareVersions("1.6.0", o)) {
                                return true
                            }
                        }
                    }
                }
            }
            if (this.compareVersions("1.5.0", o)) {
                return true
            }
            return false
        },
        IEInstall : function () {
            location.href = this.getJavaURL + ((this.returnPage != null) ? ("&returnPage=" + this.returnPage) : "") + ((this.locale != null) ? ("&locale=" + this.locale) : "") + ((this.brand != null) ? ("&brand=" + this.brand) : "");
            return false
        },
        done : function (o, n) {
        },
        FFInstall : function () {
            location.href = this.getJavaURL + ((this.returnPage != null) ? ("&returnPage=" + this.returnPage) : "") + ((this.locale != null) ? ("&locale=" + this.locale) : "") + ((this.brand != null) ? ("&brand=" + this.brand) : "") + ((this.installType != null) ? ("&type=" + this.installType) : "");
            return false
        },
        compareVersions : function (q, r) {
            var o = q.split(".");
            var n = r.split(".");
            for (var p = 0;p < o.length;++p) {
                o[p] = Number(o[p])
            }
            for (var p = 0;p < n.length;++p) {
                n[p] = Number(n[p])
            }
            if (o.length == 2) {
                o[2] = 0
            }
            if (o[0] > n[0]) {
                return true
            }
            if (o[0] < n[0]) {
                return false
            }
            if (o[1] > n[1]) {
                return true
            }
            if (o[1] < n[1]) {
                return false
            }
            if (o[2] > n[2]) {
                return true
            }
            if (o[2] < n[2]) {
                return false
            }
            return true
        },
        enableAlerts : function () {
            this.browserName = null;
            this.debug = true
        },
        poll : function () {
            this.refresh();
            var n = this.getJREs();
            if ((this.preInstallJREList.length == 0) && (n.length != 0)) {
                clearInterval(this.myInterval);
                if (this.returnPage != null) {
                    location.href = this.returnPage
                }
            }
            if ((this.preInstallJREList.length != 0) && (n.length != 0) && (this.preInstallJREList[0] != n[0])) {
                clearInterval(this.myInterval);
                if (this.returnPage != null) {
                    location.href = this.returnPage
                }
            }
        },
        writePluginTag : function () {
            var n = this.getBrowser();
            if ((n.indexOf("MSIE")!=-1)||(n.indexOf("Trident")!=-1)) {
                document.write('<object classid="clsid:CAFEEFAC-DEC7-0000-0001-ABCDEFFEDCBA" id="deployJavaPlugin" width="0" height="0"></object>')
            }
            else {
                if (n == "Netscape Family" && this.allowPlugin()) {
                    this.writeEmbedTag()
                }
            }
        },
        refresh : function () {
            navigator.plugins.refresh(false);
            var n = this.getBrowser();
            if (n == "Netscape Family" && this.allowPlugin()) {
                var o = document.getElementById("deployJavaPlugin");
                if (o == null) {
                    this.writeEmbedTag()
                }
            }
        },
        writeEmbedTag : function () {
            var n = false;
            var o;
            if (navigator.mimeTypes != null) {
                for (o = 0;o < navigator.mimeTypes.length;o++) {
                    if (navigator.mimeTypes[o].type == this.mimeType) {
                        if (navigator.mimeTypes[o].enabledPlugin) {
                            //document.write('<embed id="deployJavaPlugin" type="' + this.mimeType + '" hidden="true" />');
                            n = true;
                        }
                    }
                }
                if (!n) {
                    for (o = 0;o < navigator.mimeTypes.length;o++) {
                        if (navigator.mimeTypes[o].type == this.oldMimeType) {
                            if (navigator.mimeTypes[o].enabledPlugin) {
                                document.write('<embed id="deployJavaPlugin" type="' + this.oldMimeType + '" hidden="true" />')
                                n = true;
                            }
                        }
                    }
                }
            }
        }
    };
    d.writePluginTag();
    
    if (d.locale == null) {
        var h = null;
        if (h == null) {
            try {
                h = navigator.userLanguage
            }
            catch (f) {
                ;
            }
        }
        if (h == null) {
            try {
                h = navigator.systemLanguage
            }
            catch (f) {
                ;
            }
        }
        if (h == null) {
            try {
                h = navigator.language
            }
            catch (f) {
                ;
            }
        }
        if (h != null) {
            h.replace("-", "_");
            d.locale = h
        }
    }
    return d
}
();