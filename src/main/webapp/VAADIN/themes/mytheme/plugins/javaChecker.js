var warnBrowser;
var warnOS;
var warnJava;
var notStable;
var load;
var bit;
var os;
var nosup;
var gotoInfo;
var parser;
var result;

var isWindows = navigator.platform.indexOf("Win") == 0;
var isInitialized = false;
var language = navigator.languages ? navigator.languages[0] : (navigator.language || navigator.userLanguage);

var JavaChecker = {
    isInitialized : false, version : "1.0", name : "JavaChecker", getJavaVersion : function () {
        if (!isInitialized)
            this.init(null);
        //получить все установленные плагины Java
        try {
            var v = deployJava.getJREs();
            if (v.length > 0) {            
              versions = v[0];
              for (k=1; v.length > k; k++) {
                versions += ";" + v[k];
              }
              return versions;
          }
        }
        catch (e) {
            ;
        }
        return null;
    },
    init : function (lang) {
        parser = new UAParser();
        result = parser.getResult();

        var lang_ = lang;
         if (lang) {
            lang = lang.toLowerCase();
            if (lang.indexOf("ru") != -1 || lang.indexOf("uk") != -1 || lang.indexOf("en") != -1) {
                lang_ = lang;
            }
        } else {
            lang_ = language;
        }
        try {
            dhtmlx.setLanguage(lang_);
        } catch (ex) {
            ;
        }
        if (lang_.indexOf("ru") !=  - 1) {
            language = "ru";
            warnBrowser = " не поддерживается для работы с Java.\nИспользуйте другой браузер (Интернет-навигатор).\n";
            warnJava = "Java не установлена или не разрешена в браузере.\n";
            bit = "Необходимо загрузить и/или разрешить Java.\n";
            load = "Загрузить Java?\n";
            os = "Ваша операционная система ";
            nosup = " не поддерживается.\nНеобходимо обновить до более новой версии.";
            gotoInfo = "\n\nПерейти на страницу детальной информации?";
            notStable = "Так как работает нестабильно при операциях Java более 10-12 сек.\n";
        }
        else if (lang_.indexOf("uk") !=  - 1) {
            language = "uk";
            warnBrowser = " не підтримується для роботи з Java.\nВикористовуйте інший браузер (Інтернет-навігатор).\n";
            warnJava = "Java не встановлена або не дозволена в браузері.\n";
            bit = "Необхідно завантажити та/або дозволити Java.\n";
            load = "Завантажити Java?\n";
            os = "Ваша операційна система ";
            nosup = " не підтримується.\nНеобхідно оновити до більш нової версії.";
            gotoInfo = "\n\nПерейти на сторінку детальної інформації?";
            notStable = "Так як працює нестабільно при операціях Java більше 10-12 сек.\n";
        }
        else {
            language = "en";
            warnBrowser = " is not supported with Java.\nUse a different browser.\n";
            warnJava = "Java is not installed or is not allowed in browser.\n";
            bit = bit = "You need to download and/or allow Java.\n";
            load = "Download Java?\n";
            os = "Your operating system ";
            nosup = " is not supported.\nPlease update to a newer version.";
            gotoInfo = "\n\nGo to the details page?";
            notStable = "As the computer becomes unstable when Java operations more than 10-12 seconds.\n";
        }
        isInitialized = true;
    },
    isIE : function () {
        var br = result.browser.name;
        if (br.indexOf("IE") != -1)
          return true;
        else
          return false;        
    },
    getMajorVersion : function (version_) {
        var ind = version_.indexOf(",");
        if (ind ==  - 1)
            ind = version_.indexOf(".");

        if (ind !=  - 1)
            ind = parseInt(version_.substring(0, ind));
        else 
            ind = parseInt(version_);

        return ind;
    },
    getBrowserInfo : function () {
        if (!isInitialized)
            this.init(null);
        return (result.browser.name + " " + result.browser.version);
    },
    getBrowserVersion : function () {
        if (!isInitialized)
            this.init(null);
        return this.getMajorVersion(result.browser.version);
    },
    getBrowserWarning : function () {
        if (!isInitialized)
            this.init(null);
        var os = this.getOS();
        var nm = result.browser.name;
        var mver = this.getMajorVersion(result.browser.version);
        var warning = "";
        if (nm.indexOf("Chrome") !=  - 1) {
            if (mver > 42) {
                warning = this.getBrowserInfo() + warnBrowser;
            }
        }
        else if (nm.indexOf("Firefox") !=  - 1) {
            if (mver > 41 && isWindows) {
                //warning = this.getBrowserInfo() + warnBrowser + notStable;
            }
        }  
        else if (nm.indexOf("Safari") !=  - 1 && isWindows) {            
            warning = this.getBrowserInfo() + warnBrowser;
        }
        else if (nm.indexOf("Safari") !=  - 1) {
            if (6 > mver) {
                warning = this.getBrowserInfo() + warnBrowser;
            }
        }      
        else if (nm.indexOf("Edge") !=  - 1) {
            warning = this.getBrowserInfo() + warnBrowser;
        }
        else if (nm.indexOf("Opera") !=  - 1) {
            if (mver > 33) {
                if (os.indexOf("Windows 7") != -1)
                    warning = this.getBrowserInfo() + warnBrowser;
            }
        }
        else if (nm.indexOf("Yandex") !=  - 1) {
            if (mver > 15) {                
                warning = this.getBrowserInfo() + warnBrowser;
            }
        }
        return warning;
    },
    verifyJava : function () {
        if (!isInitialized)
            this.init(null);

        var win;
        var browserWarning = this.getBrowserWarning();
        var le = language;
        if (le.indexOf("uk") !=  - 1)
            le = "ua";

        if (browserWarning) {
            //нельзя работать с Java            
            userInput = confirm(browserWarning + gotoInfo);
            if (userInput) {
                win = window.open("http://www.itsway.kiev.ua/index.php?language=" + le + "&main_managemen=prodaction&managemen=faq_info#q_2", "_self");
                win.focus();
            }
            return false;
        }

        if (warnOS) {
            //нельзя работать            
            userInput = confirm(warnOS + gotoInfo);
            if (userInput) {
                win = window.open("http://www.itsway.kiev.ua/index.php?language=" + le + "&main_managemen=prodaction&managemen=faq_info#q_1", "_self");
                win.reload();
                win.focus();
            }
            return false;
        }
        else {
            var javaVersion = this.getJavaVersion();                            
            if (!javaVersion || javaVersion.indexOf("undefined") !=  - 1) {                
                return false;
            }
            else {
                //alert("Java установлена и разрешена.");
                return true;
            }
        }
    },
    getOS : function () {
        warnOS = "";
        if (!isInitialized)
            this.init(null);

        OSName = result.os.name + " " + result.os.version;
        var nm = result.os.name;
        var ver = result.os.version;

        var ua = window.navigator ? navigator.userAgent || "" : "";
        //"Windows NT 5.1; SV1;"
        var pos;
        if (ua.indexOf("Windows NT 5.1") !=  - 1) {
            OSName = "Windows XP SP3";
            if (ua.indexOf("SV1;") !=  - 1) {
                OSName = "Windows XP SP2";
                //warnOS = os + OSName + nosup;//обновить ОС
                //если SP2 через update обновлена до SP3, то все равно остается "метка" SV1, т.е. что это SP2                
            }
        }
        else if (nm.indexOf("Mac OS") !=  - 1) {
            ver1 = this.getMajorVersion(ver);
            if (ver1 == 10) {
                pos = ver.indexOf(".");
                if (pos !=  - 1) {
                    ver = ver.substring(pos + 1, ver.length);
                    ver1 = this.getMajorVersion(ver);
                    if (7 > ver1) {
                        warnOS = OSName + nosup;
                    }                    
                }
            }
        }

        ua = ua.toLowerCase();
        if (OSName.indexOf("Win") !=  - 1) {
            if (ua.indexOf("wow64") !=  - 1 || ua.indexOf("win64") !=  - 1) {
                OSName = OSName + " (64-bit)";
            }
            else {
                OSName = OSName + " (32-bit)";
            }
        }
        else {
            if (ua.indexOf("x86_64") !=  - 1 || ua.indexOf("amd64") !=  - 1 || ua.indexOf("intel64") !=  - 1 || ua.indexOf("em64t") !=  - 1 || ua.indexOf("ia64") !=  - 1) {
                OSName = OSName + " (64-bit)";
            }
            else if (ua.indexOf("x86") !=  - 1 || ua.indexOf("i286") !=  - 1 || ua.indexOf("i386") !=  - 1 || ua.indexOf("i486") !=  - 1 || ua.indexOf("i586") !=  - 1 || ua.indexOf("i686") !=  - 1) {
                OSName = OSName + " (32-bit)";
            }
        }
        return OSName;
    }

};

var Base64 = {
    // private property
    _keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=", 

    // public method for encoding
    encode : function (input) {
        var output = "";
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
        var i = 0;

        input = Base64._utf8_encode(input);

        while (i < input.length) {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);

            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;

            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            }
            else if (isNaN(chr3)) {
                enc4 = 64;
            }

            output = output + Base64._keyStr.charAt(enc1) + Base64._keyStr.charAt(enc2) + Base64._keyStr.charAt(enc3) + Base64._keyStr.charAt(enc4);

        }

        return output;
    },

    // public method for decoding
    decode : function (input) {
        var output = "";
        var chr1, chr2, chr3;
        var enc1, enc2, enc3, enc4;
        var i = 0;

        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

        while (i < input.length) {
            enc1 = Base64._keyStr.indexOf(input.charAt(i++));
            enc2 = Base64._keyStr.indexOf(input.charAt(i++));
            enc3 = Base64._keyStr.indexOf(input.charAt(i++));
            enc4 = Base64._keyStr.indexOf(input.charAt(i++));

            chr1 = (enc1 << 2) | (enc2 >> 4);
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
            chr3 = ((enc3 & 3) << 6) | enc4;

            output = output + String.fromCharCode(chr1);

            if (enc3 != 64) {
                output = output + String.fromCharCode(chr2);
            }
            if (enc4 != 64) {
                output = output + String.fromCharCode(chr3);
            }

        }

        output = Base64._utf8_decode(output);

        return output;

    },

    // private method for UTF-8 encoding
    _utf8_encode : function (string) {
        string = string.replace(/\r\n/g, "\n");
        var utftext = "";

        for (var n = 0;n < string.length;n++) {

            var c = string.charCodeAt(n);

            if (c < 128) {
                utftext += String.fromCharCode(c);
            }
            else if ((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            }
            else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }

        }

        return utftext;
    },

    // private method for UTF-8 decoding
    _utf8_decode : function (utftext) {
        var string = "";
        var i = 0;
        var c = c1 = c2 = 0;

        while (i < utftext.length) {
            c = utftext.charCodeAt(i);

            if (c < 128) {
                string += String.fromCharCode(c);
                i++;
            }
            else if ((c > 191) && (c < 224)) {
                c2 = utftext.charCodeAt(i + 1);
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                i += 2;
            }
            else {
                c2 = utftext.charCodeAt(i + 1);
                c3 = utftext.charCodeAt(i + 2);
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }

        }
        return string;
    }
}