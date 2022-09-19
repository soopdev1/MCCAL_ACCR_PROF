function isNumeric(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}



function specialchar(fieldid) {
    var stringToReplace = document.getElementById(fieldid).value;
    var specialChars = "~`'k§!#$%^&*+=-_[];,/{}|\":<>?£,.àáâãäçèéêëìíîïñòóôõöùúûüýÿÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ°";
    for (var i = 0; i < specialChars.length; i++) {
        stringToReplace = stringToReplace.replace(new RegExp("\\" + specialChars[i], 'gi'), '');
    }
    stringToReplace = stringToReplace.replace(/\\/g, "");
    stringToReplace = stringToReplace.replace(new RegExp("[0-9]", "g"), "");
    document.getElementById(fieldid).value = stringToReplace;

}
function specialchar2(fieldid) {
    var stringToReplace = document.getElementById(fieldid).value;
    var specialChars = "~`'k€§!#$%^&*+=-_[];,/{}|\":<>?£,.àáâãäçèéêëìíîïñòóôõöùúûüýÿÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ°";
    for (var i = 0; i < specialChars.length; i++) {
        stringToReplace = stringToReplace.replace(new RegExp("\\" + specialChars[i], 'gi'), '');
    }
    stringToReplace = stringToReplace.replace(/\\/g, "");
    // stringToReplace = stringToReplace.replace(new RegExp("[0-9]", "g"), "");
    document.getElementById(fieldid).value = stringToReplace;

}


function fieldNoEuro(fieldid) {

    var stringToReplace = document.getElementById(fieldid).value;
    var specialChars = "~`§!#$%^&*+=_@[]{}|\"<>?£âãäëîïñôõö€ûüýÿÀÁÂÃÄÇçÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ°";
    for (var i = 0; i < specialChars.length; i++) {
        stringToReplace = stringToReplace.replace(new RegExp("\\" + specialChars[i], 'gi'), '');
    }
    stringToReplace = stringToReplace.replace(/\\/g, "");

    //  var stringToReplace = document.getElementById(fieldid).value;
    //  alert(stringToReplace);                
    // stringToReplace = stringToReplace.replace("€", '');
    document.getElementById(fieldid).value = stringToReplace;

}


function fieldNoEuroMail(fieldid) {

    var stringToReplace = document.getElementById(fieldid).value;
    var specialChars = "~`§!#$%^&*+€=[](){}|\"<>?£âãäëîïñôõö€ûüýÿÀÁÂÃÄÇçÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ°";
    for (var i = 0; i < specialChars.length; i++) {
        stringToReplace = stringToReplace.replace(new RegExp("\\" + specialChars[i], 'gi'), '');
    }
    stringToReplace = stringToReplace.replace(/\\/g, "");

    //  var stringToReplace = document.getElementById(fieldid).value;
    //  alert(stringToReplace);                
    // stringToReplace = stringToReplace.replace("€", '');
    document.getElementById(fieldid).value = stringToReplace;

}





function checkCF(cf) {
    var esito = true;
    var validi, i, s, set1, set2, setpari, setdisp;
    if (cf === '')
        esito = false;
    cf = cf.toUpperCase();
    if (cf.length === 16) {
        if (cf.length !== 16) {
            esito = false;
        } else {
            validi = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            for (i = 0; i < 16; i++) {
                if (validi.indexOf(cf.charAt(i)) === -1)
                    esito = false;
            }
            set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
            setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";
            s = 0;
            for (i = 1; i <= 13; i += 2)
                s += setpari.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));
            for (i = 0; i <= 14; i += 2)
                s += setdisp.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));
            if (s % 26 !== cf.charCodeAt(15) - 'A'.charCodeAt(0)) {
                esito = false;
            }
            return esito;
        }
    } else {
        return false;
    }
}

function checkMail(mail) {
    return mail.includes("@");
//                var filter = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/;
//                return filter.test(mail);
}

function checkIva(pi) {
    var esito = true;
    var validi, i, s, c;
    if (pi.length !== 11) {
        esito = false;
    } else {
        validi = "0123456789";
        for (i = 2; i < 11; i++) {
            if (validi.indexOf(pi.charAt(i)) === -1)
                esito = false;
        }
        s = 0;
        for (i = 0; i <= 9; i += 2)
            s += pi.charCodeAt(i) - '0'.charCodeAt(0);
        for (i = 1; i <= 9; i += 2) {
            c = 2 * (pi.charCodeAt(i) - '0'.charCodeAt(0));
            if (c > 9)
                c = c - 9;
            s += c;
        }
        if ((10 - s % 10) % 10 !== pi.charCodeAt(10) - '0'.charCodeAt(0))
            esito = false;
    }
    return esito;
}

function checkDate(dt) {
    var re = /^\d{1,2}\/\d{1,2}\/\d{4}$/;
    return dt.match(re);
}

function dismiss(name) {
    document.getElementById(name).className = "modal fade";
    document.getElementById(name).style.display = "none";
}

function containsNumeric(val) {
    var matches = val.match(/\d+/g);
    return matches !== null;
}

function containsSpecial(val) {
    var specialChars = "<>@!#$%^&*()_+[]{}?:;|'\"\\,./~`-=";
    for (var i = 0; i < specialChars.length; i++) {
        if (val.indexOf(specialChars[i]) > -1) {
            return true;
        }
    }
    return false;
}


function parseIntRaf(valueint) {
    valueint = valueint + "";
    valueint = valueint.replace(/\./g, '');
    valueint = valueint.replace(/,/g, '');
    return parseInt(valueint);
}
function parseFloatRaf(valuefloat) {
    if (valuefloat.indexOf(",") > -1) {
        valuefloat = valuefloat.replace(/\./g, '');
        valuefloat = valuefloat.replace(/,/g, '.');
    } else {
        if (valuefloat.indexOf(".") === -1) {
            valuefloat = valuefloat + ".00";
        }
    }
    return parseFloat(valuefloat);
}


function formatDouble(fieldid) {
    document.getElementById(fieldid).value = accounting.formatNumber(parseFloatRaf(document.getElementById(fieldid).value), 2, ".", ",");
}
function fieldOnlyNumber(fieldid) {
    var stringToReplace = document.getElementById(fieldid).value;
    stringToReplace = stringToReplace.replace(/\D/g, '');
    document.getElementById(fieldid).value = stringToReplace;
}

function formatDoubleMax(fieldid, max) {
    if(parseFloatRaf(document.getElementById(fieldid).value)>= parseFloatRaf(max)){
        document.getElementById(fieldid).value = max;
    }
    document.getElementById(fieldid).value = accounting.formatNumber(parseFloatRaf(document.getElementById(fieldid).value), 2, ".", ",");
}