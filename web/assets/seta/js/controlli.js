function convertDate_0(dataing) {
    var dd = dataing.split("-")[2].trim();
    var mm = dataing.split("-")[1].trim();
    var aa = dataing.split("-")[0].trim();
    var out = dd + "/" + mm + "/" + aa;
    return out;
}

function linkAE() {
    var win = window.open('https://telematici.agenziaentrate.gov.it/VerificaCF/Scegli.do?parameter=verificaCf', '_blank');
    win.focus();
}

function getRandom(length) {
    return Math.floor(Math.pow(10, length - 1) + Math.random() * 9 * Math.pow(10, length - 1));
}

function formatNumber_RAF(num) {
    return num.toString().replace(/[&\/\\#,+()$~%'":*?<>{}]/g, '').replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
}

//arrotonda per difetto ita centesimi
function round_and_split_ita(value) {
    var output = new Array();
    if (value.lastIndexOf(".") > -1) {
        var int_1 = value.substring(0, value.lastIndexOf("."));
        var dec = value.substring(value.lastIndexOf(".") + 1);
        var round = dec % 5; //resto 5 cent
        var int = dec - round;
        if (int < 10) {
            output[0] = int_1 + ".0" + int;
        } else {
            output[0] = int_1 + "." + int;
        }
        if (round < 10) {
            output[1] = "0.0" + round;
        } else {
            output[1] = "0." + round;
        }
        return output;
    }
    output[0] = value;
    output[1] = "0.00";
    return output;
}

//arrotonda per eccesso ita centesimi
function round_and_split_ita_up(value) {
    var output = new Array();
    if (value.lastIndexOf(".") > -1) {
        var int_1 = value.substring(0, value.lastIndexOf("."));
        var dec = value.substring(value.lastIndexOf(".") + 1);
        var r1 = parseInt(dec % 5);
        var round = r1;
        if (r1 > 0) {
            round = 5 - r1;
        }
        var int = parseInt(dec) + parseInt(round);
        if (int >= 100) {
            int_1 = parseInt(int_1) + 1;
            int = int - 100;
        }
        if (int < 10) {
            output[0] = int_1 + ".0" + int;
        } else {
            output[0] = int_1 + "." + int;
        }
        if (round < 10) {
            output[1] = "0.0" + round;
        } else {
            output[1] = "0." + round;
        }
        return output;
    }
    output[0] = value;
    output[1] = "0.00";
    return output;
}


//arrotonda double
function round_value(value, decimals, up) {
    var y = new BigNumber(value);
    if (up) {
        return  new BigNumber(value).round(decimals, BigNumber.ROUND_HALF_UP);
    } else {
        return y.round(decimals, BigNumber.ROUND_HALF_EVEN);
    }
}

//FORMATTAZIONE NUMERI TABELLA
function formatValueDecimal_1(value, thousand, decimal) {
    value.value = accounting.formatNumber(parseFloatRaf(value.value), 2, thousand, decimal);
}
function formatValueDecimal_length(value, thousand, decimal, length) {
    value.value = accounting.formatNumber(parseFloatRaf(value.value), length, thousand, decimal);
}

function formatValueDecimal_2(value, thousand, decimal, maxlength) {
    var count1 = (value.value.match(/\./g) || []).length;
    var count2 = (value.value.match(/,/g) || []).length;
    var sum = count1 + count2;
    if ((value.value.length - sum) > maxlength) {
        value.value = value.value.substring((value.value.length - sum) - maxlength);
    }
    value.value = accounting.formatNumber(parseFloatRaf(value.value), 2, thousand, decimal);
}

function modR(a, n) {
    var a1 = new BigNumber(a);
    var n1 = new BigNumber(n);
    return a1.modulo(n1);
}


function formatValueDecimalMax(value, max, thousand, decimal) {
    value.value = accounting.formatNumber(parseFloatRaf(value.value), 2, thousand, decimal);
    if (parseFloatRaf(replacestringparam(value.value, thousand)) >= parseFloatRaf(max)) {
        value.value = accounting.formatNumber(parseFloatRaf(max), 2, thousand, decimal);
    }
}

function formatValueDecimalMaxlength(value, max, thousand, decimal, length) {
    value.value = accounting.formatNumber(parseFloatRaf(value.value), length, thousand, decimal);
    if (parseFloatRaf(replacestringparam(value.value, thousand)) >= parseFloatRaf(max)) {
        value.value = accounting.formatNumber(parseFloatRaf(max), length, thousand, decimal);
    }
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

function parseIntRaf(valueint) {
    valueint = valueint + "";
    valueint = valueint.replace(/\./g, '');
    valueint = valueint.replace(/,/g, '');
    return parseInt(valueint);
}

function replacestringparam(value, thousand) {
    if (thousand === ",") {
        return value.replace(/[&\/\\#+()$~%'":*?<>{}],/g, '');
    }
    if (thousand === ".") {
        return value.replace(/[&\/\\#+()\.$~%'":*?<>{}]/g, '');
    }
    return value;
}

//visualizza un modal con eventuale redirect
function showmod1(modal, page) {
    document.getElementById(modal).className = document.getElementById(modal).className + " in";
    document.getElementById(modal).style.display = "block";
    //cambiare
    document.getElementById('checkpasswordmodal').action = page;
}

//chiude un modal
function dismiss(modal) {
    document.getElementById(modal).className = "modal fade";
    document.getElementById(modal).style.display = "none";
}

//restituisce il numero del mese partendo dalla lettera del cf
function formatMonthcf(read) {
    read = read.trim().toUpperCase();
    if (read === "A") {
        return "01";
    }
    if (read === "B") {
        return "02";
    }
    if (read === "C") {
        return "03";
    }
    if (read === "D") {
        return "04";
    }
    if (read === "E") {
        return "05";
    }
    if (read === "H") {
        return "06";
    }
    if (read === "L") {
        return "07";
    }
    if (read === "M") {
        return "08";
    }
    if (read === "P") {
        return "09";
    }
    if (read === "R") {
        return "10";
    }
    if (read === "S") {
        return "11";
    }
    if (read === "T") {
        return "12";
    }
    return "00";
}

// visualizza lista agenzie cliccando su agency royalty
function checkver(sel, val) {
    var verified = document.getElementById(sel);
    if (verified.checked) {
        document.getElementById(val).style.display = "block";
    } else {
        document.getElementById(val).style.display = "none";
    }
}




function convertDate_1(dataing) {
    var dd = dataing.split("-")[0].trim();
    var mm = dataing.split("-")[1].trim();
    var aa = dataing.split("-")[2].trim();
    var out = dd + "/" + mm + "/19" + aa;
    return out;
}

function convertDate_2(dataing) {

    if (dataing === "-01--01--01") {
        return "";
    } else {
        var dd = dataing.split("-")[0].trim();
        var mm = dataing.split("-")[1].trim();
        var aa = dataing.split("-")[2].trim();
        var out = dd + "/" + mm + "/20" + aa;
        return out;
    }
}

//  ACCOUNTING JS
(function (p, z) {
    function q(a) {
        return!!("" === a || a && a.charCodeAt && a.substr)
    }
    function m(a) {
        return u ? u(a) : "[object Array]" === v.call(a)
    }
    function r(a) {
        return"[object Object]" === v.call(a)
    }
    function s(a, b) {
        var d, a = a || {}, b = b || {};
        for (d in b)
            b.hasOwnProperty(d) && null == a[d] && (a[d] = b[d]);
        return a
    }
    function j(a, b, d) {
        var c = [], e, h;
        if (!a)
            return c;
        if (w && a.map === w)
            return a.map(b, d);
        for (e = 0, h = a.length; e < h; e++)
            c[e] = b.call(d, a[e], e, a);
        return c
    }
    function n(a, b) {
        a = Math.round(Math.abs(a));
        return isNaN(a) ? b : a
    }
    function x(a) {
        var b = c.settings.currency.format;
        "function" === typeof a && (a = a());
        return q(a) && a.match("%v") ? {pos: a, neg: a.replace("-", "").replace("%v", "-%v"), zero: a} : !a || !a.pos || !a.pos.match("%v") ? !q(b) ? b : c.settings.currency.format = {pos: b, neg: b.replace("%v", "-%v"), zero: b} : a
    }
    var c = {version: "0.4.1", settings: {currency: {symbol: "$", format: "%s%v", decimal: ".", thousand: ",", precision: 2, grouping: 3}, number: {precision: 0, grouping: 3, thousand: ",", decimal: "."}}}, w = Array.prototype.map, u = Array.isArray, v = Object.prototype.toString, o = c.unformat = c.parse = function (a, b) {
        if (m(a))
            return j(a, function (a) {
                return o(a, b)
            });
        a = a || 0;
        if ("number" === typeof a)
            return a;
        var b = b || ".", c = RegExp("[^0-9-" + b + "]", ["g"]), c = parseFloat(("" + a).replace(/\((.*)\)/, "-$1").replace(c, "").replace(b, "."));
        return!isNaN(c) ? c : 0
    }, y = c.toFixed = function (a, b) {
        var b = n(b, c.settings.number.precision), d = Math.pow(10, b);
        return(Math.round(c.unformat(a) * d) / d).toFixed(b)
    }, t = c.formatNumber = c.format = function (a, b, d, i) {
        if (m(a))
            return j(a, function (a) {
                return t(a, b, d, i)
            });
        var a = o(a), e = s(r(b) ? b : {precision: b, thousand: d, decimal: i}, c.settings.number), h = n(e.precision), f = 0 > a ? "-" : "", g = parseInt(y(Math.abs(a || 0), h), 10) + "", l = 3 < g.length ? g.length % 3 : 0;
        return f + (l ? g.substr(0, l) + e.thousand : "") + g.substr(l).replace(/(\d{3})(?=\d)/g, "$1" + e.thousand) + (h ? e.decimal + y(Math.abs(a), h).split(".")[1] : "")
    }, A = c.formatMoney = function (a, b, d, i, e, h) {
        if (m(a))
            return j(a, function (a) {
                return A(a, b, d, i, e, h)
            });
        var a = o(a), f = s(r(b) ? b : {symbol: b, precision: d, thousand: i, decimal: e, format: h}, c.settings.currency), g = x(f.format);
        return(0 < a ? g.pos : 0 > a ? g.neg : g.zero).replace("%s", f.symbol).replace("%v", t(Math.abs(a), n(f.precision), f.thousand, f.decimal))
    };
    c.formatColumn = function (a, b, d, i, e, h) {
        if (!a)
            return[];
        var f = s(r(b) ? b : {symbol: b, precision: d, thousand: i, decimal: e, format: h}, c.settings.currency), g = x(f.format), l = g.pos.indexOf("%s") < g.pos.indexOf("%v") ? !0 : !1, k = 0, a = j(a, function (a) {
            if (m(a))
                return c.formatColumn(a, f);
            a = o(a);
            a = (0 < a ? g.pos : 0 > a ? g.neg : g.zero).replace("%s", f.symbol).replace("%v", t(Math.abs(a), n(f.precision), f.thousand, f.decimal));
            if (a.length > k)
                k = a.length;
            return a
        });
        return j(a, function (a) {
            return q(a) && a.length < k ? l ? a.replace(f.symbol, f.symbol + Array(k - a.length + 1).join(" ")) : Array(k - a.length + 1).join(" ") + a : a
        })
    };
    if ("undefined" !== typeof exports) {
        if ("undefined" !== typeof module && module.exports)
            exports = module.exports = c;
        exports.accounting = c
    } else
        "function" === typeof define && define.amd ? define([], function () {
            return c
        }) : (c.noConflict = function (a) {
            return function () {
                p.accounting = a;
                c.noConflict = z;
                return c
            }
        }(p.accounting), p.accounting = c)
})(this);

//  BIGNUMBER
!function (e) {
    "use strict";
    function n(e) {
        function E(e, n) {
            var t, r, i, o, u, s, f = this;
            if (!(f instanceof E))
                return j && L(26, "constructor call without new", e), new E(e, n);
            if (null != n && H(n, 2, 64, M, "base")) {
                if (n = 0 | n, s = e + "", 10 == n)
                    return f = new E(e instanceof E ? e : s), U(f, P + f.e + 1, k);
                if ((o = "number" == typeof e) && 0 * e != 0 || !new RegExp("^-?" + (t = "[" + N.slice(0, n) + "]+") + "(?:\\." + t + ")?$", 37 > n ? "i" : "").test(s))
                    return h(f, s, o, n);
                o ? (f.s = 0 > 1 / e ? (s = s.slice(1), -1) : 1, j && s.replace(/^0\.0*|\./, "").length > 15 && L(M, v, e), o = !1) : f.s = 45 === s.charCodeAt(0) ? (s = s.slice(1), -1) : 1, s = D(s, 10, n, f.s)
            } else {
                if (e instanceof E)
                    return f.s = e.s, f.e = e.e, f.c = (e = e.c) ? e.slice() : e, void(M = 0);
                if ((o = "number" == typeof e) && 0 * e == 0) {
                    if (f.s = 0 > 1 / e ? (e = -e, -1) : 1, e === ~~e) {
                        for (r = 0, i = e; i >= 10; i /= 10, r++)
                            ;
                        return f.e = r, f.c = [e], void(M = 0)
                    }
                    s = e + ""
                } else {
                    if (!g.test(s = e + ""))
                        return h(f, s, o);
                    f.s = 45 === s.charCodeAt(0) ? (s = s.slice(1), -1) : 1
                }
            }
            for ((r = s.indexOf(".")) > - 1 && (s = s.replace(".", "")), (i = s.search(/e/i)) > 0?(0 > r && (r = i), r += + s.slice(i + 1), s = s.substring(0, i)):0 > r && (r = s.length), i = 0; 48 === s.charCodeAt(i); i++)
                ;
            for (u = s.length; 48 === s.charCodeAt(--u); )
                ;
            if (s = s.slice(i, u + 1))
                if (u = s.length, o && j && u > 15 && (e > y || e !== d(e)) && L(M, v, f.s * e), r = r - i - 1, r > z)
                    f.c = f.e = null;
                else if (G > r)
                    f.c = [f.e = 0];
                else {
                    if (f.e = r, f.c = [], i = (r + 1) % O, 0 > r && (i += O), u > i) {
                        for (i && f.c.push( + s.slice(0, i)), u -= O; u > i; )
                            f.c.push(+s.slice(i, i += O));
                        s = s.slice(i), i = O - s.length
                    } else
                        i -= u;
                    for (; i--; s += "0")
                        ;
                    f.c.push(+s)
                }
            else
                f.c = [f.e = 0];
            M = 0
        }
        function D(e, n, t, i) {
            var o, u, f, c, a, h, g, p = e.indexOf("."), d = P, m = k;
            for (37 > t && (e = e.toLowerCase()), p >= 0 && (f = J, J = 0, e = e.replace(".", ""), g = new E(t), a = g.pow(e.length - p), J = f, g.c = s(l(r(a.c), a.e), 10, n), g.e = g.c.length), h = s(e, t, n), u = f = h.length; 0 == h[--f]; h.pop())
                ;
            if (!h[0])
                return"0";
            if (0 > p ? --u : (a.c = h, a.e = u, a.s = i, a = C(a, g, d, m, n), h = a.c, c = a.r, u = a.e), o = u + d + 1, p = h[o], f = n / 2, c = c || 0 > o || null != h[o + 1], c = 4 > m ? (null != p || c) && (0 == m || m == (a.s < 0 ? 3 : 2)) : p > f || p == f && (4 == m || c || 6 == m && 1 & h[o - 1] || m == (a.s < 0 ? 8 : 7)), 1 > o || !h[0])
                e = c ? l("1", -d) : "0";
            else {
                if (h.length = o, c)
                    for (--n; ++h[--o] > n; )
                        h[o] = 0, o || (++u, h.unshift(1));
                for (f = h.length; !h[--f]; )
                    ;
                for (p = 0, e = ""; f >= p; e += N.charAt(h[p++]))
                    ;
                e = l(e, u)
            }
            return e
        }
        function F(e, n, t, i) {
            var o, u, s, c, a;
            if (t = null != t && H(t, 0, 8, i, w) ? 0 | t : k, !e.c)
                return e.toString();
            if (o = e.c[0], s = e.e, null == n)
                a = r(e.c), a = 19 == i || 24 == i && B >= s ? f(a, s) : l(a, s);
            else if (e = U(new E(e), n, t), u = e.e, a = r(e.c), c = a.length, 19 == i || 24 == i && (u >= n || B >= u)) {
                for (; n > c; a += "0", c++)
                    ;
                a = f(a, u)
            } else if (n -= s, a = l(a, u), u + 1 > c) {
                if (--n > 0)
                    for (a += "."; n--; a += "0")
                        ;
            } else if (n += u - c, n > 0)
                for (u + 1 == c && (a += "."); n--; a += "0")
                    ;
            return e.s < 0 && o ? "-" + a : a
        }
        function _(e, n) {
            var t, r, i = 0;
            for (u(e[0]) && (e = e[0]), t = new E(e[0]); ++i < e.length; ) {
                if (r = new E(e[i]), !r.s) {
                    t = r;
                    break
                }
                n.call(t, r) && (t = r)
            }
            return t
        }
        function x(e, n, t, r, i) {
            return(n > e || e > t || e != c(e)) && L(r, (i || "decimal places") + (n > e || e > t ? " out of range" : " not an integer"), e), !0
        }
        function I(e, n, t) {
            for (var r = 1, i = n.length; !n[--i]; n.pop())
                ;
            for (i = n[0]; i >= 10; i /= 10, r++)
                ;
            return(t = r + t * O - 1) > z ? e.c = e.e = null : G > t ? e.c = [e.e = 0] : (e.e = t, e.c = n), e
        }
        function L(e, n, t) {
            var r = new Error(["new BigNumber", "cmp", "config", "div", "divToInt", "eq", "gt", "gte", "lt", "lte", "minus", "mod", "plus", "precision", "random", "round", "shift", "times", "toDigits", "toExponential", "toFixed", "toFormat", "toFraction", "pow", "toPrecision", "toString", "BigNumber"][e] + "() " + n + ": " + t);
            throw r.name = "BigNumber Error", M = 0, r
        }
        function U(e, n, t, r) {
            var i, o, u, s, f, l, c, a = e.c, h = S;
            if (a) {
                e:{
                    for (i = 1, s = a[0]; s >= 10; s /= 10, i++)
                        ;
                    if (o = n - i, 0 > o)
                        o += O, u = n, f = a[l = 0], c = f / h[i - u - 1] % 10 | 0;
                    else if (l = p((o + 1) / O), l >= a.length) {
                        if (!r)
                            break e;
                        for (; a.length <= l; a.push(0))
                            ;
                        f = c = 0, i = 1, o %= O, u = o - O + 1
                    } else {
                        for (f = s = a[l], i = 1; s >= 10; s /= 10, i++)
                            ;
                        o %= O, u = o - O + i, c = 0 > u ? 0 : f / h[i - u - 1] % 10 | 0
                    }
                    if (r = r || 0 > n || null != a[l + 1] || (0 > u ? f : f % h[i - u - 1]), r = 4 > t ? (c || r) && (0 == t || t == (e.s < 0 ? 3 : 2)) : c > 5 || 5 == c && (4 == t || r || 6 == t && (o > 0 ? u > 0 ? f / h[i - u] : 0 : a[l - 1]) % 10 & 1 || t == (e.s < 0 ? 8 : 7)), 1 > n || !a[0])
                        return a.length = 0, r ? (n -= e.e + 1, a[0] = h[(O - n % O) % O], e.e = -n || 0) : a[0] = e.e = 0, e;
                    if (0 == o ? (a.length = l, s = 1, l--) : (a.length = l + 1, s = h[O - o], a[l] = u > 0 ? d(f / h[i - u] % h[u]) * s : 0), r)
                        for (; ; ) {
                            if (0 == l) {
                                for (o = 1, u = a[0]; u >= 10; u /= 10, o++)
                                    ;
                                for (u = a[0] += s, s = 1; u >= 10; u /= 10, s++)
                                    ;
                                o != s && (e.e++, a[0] == b && (a[0] = 1));
                                break
                            }
                            if (a[l] += s, a[l] != b)
                                break;
                            a[l--] = 0, s = 1
                        }
                    for (o = a.length; 0 === a[--o]; a.pop())
                        ;
                }
                e.e > z ? e.c = e.e = null : e.e < G && (e.c = [e.e = 0])
            }
            return e
        }
        var C, M = 0, T = E.prototype, q = new E(1), P = 20, k = 4, B = -7, $ = 21, G = -1e7, z = 1e7, j = !0, H = x, V = !1, W = 1, J = 100, X = {decimalSeparator: ".", groupSeparator: ",", groupSize: 3, secondaryGroupSize: 0, fractionGroupSeparator: " ", fractionGroupSize: 0};
        return E.another = n, E.ROUND_UP = 0, E.ROUND_DOWN = 1, E.ROUND_CEIL = 2, E.ROUND_FLOOR = 3, E.ROUND_HALF_UP = 4, E.ROUND_HALF_DOWN = 5, E.ROUND_HALF_EVEN = 6, E.ROUND_HALF_CEIL = 7, E.ROUND_HALF_FLOOR = 8, E.EUCLID = 9, E.config = function () {
            var e, n, t = 0, r = {}, i = arguments, s = i[0], f = s && "object" == typeof s ? function () {
                return s.hasOwnProperty(n) ? null != (e = s[n]) : void 0
            } : function () {
                return i.length > t ? null != (e = i[t++]) : void 0
            };
            return f(n = "DECIMAL_PLACES") && H(e, 0, A, 2, n) && (P = 0 | e), r[n] = P, f(n = "ROUNDING_MODE") && H(e, 0, 8, 2, n) && (k = 0 | e), r[n] = k, f(n = "EXPONENTIAL_AT") && (u(e) ? H(e[0], -A, 0, 2, n) && H(e[1], 0, A, 2, n) && (B = 0 | e[0], $ = 0 | e[1]) : H(e, -A, A, 2, n) && (B = -($ = 0 | (0 > e ? -e : e)))), r[n] = [B, $], f(n = "RANGE") && (u(e) ? H(e[0], -A, -1, 2, n) && H(e[1], 1, A, 2, n) && (G = 0 | e[0], z = 0 | e[1]) : H(e, -A, A, 2, n) && (0 | e ? G = -(z = 0 | (0 > e ? -e : e)) : j && L(2, n + " cannot be zero", e))), r[n] = [G, z], f(n = "ERRORS") && (e === !!e || 1 === e || 0 === e ? (M = 0, H = (j = !!e) ? x : o) : j && L(2, n + m, e)), r[n] = j, f(n = "CRYPTO") && (e === !!e || 1 === e || 0 === e ? (V = !(!e || !a), e && !V && j && L(2, "crypto unavailable", a)) : j && L(2, n + m, e)), r[n] = V, f(n = "MODULO_MODE") && H(e, 0, 9, 2, n) && (W = 0 | e), r[n] = W, f(n = "POW_PRECISION") && H(e, 0, A, 2, n) && (J = 0 | e), r[n] = J, f(n = "FORMAT") && ("object" == typeof e ? X = e : j && L(2, n + " not an object", e)), r[n] = X, r
        }, E.max = function () {
            return _(arguments, T.lt)
        }, E.min = function () {
            return _(arguments, T.gt)
        }, E.random = function () {
            var e = 9007199254740992, n = Math.random() * e & 2097151 ? function () {
                return d(Math.random() * e)
            } : function () {
                return 8388608 * (1073741824 * Math.random() | 0) + (8388608 * Math.random() | 0)
            };
            return function (e) {
                var t, r, i, o, u, s = 0, f = [], l = new E(q);
                if (e = null != e && H(e, 0, A, 14) ? 0 | e : P, o = p(e / O), V)
                    if (a && a.getRandomValues) {
                        for (t = a.getRandomValues(new Uint32Array(o *= 2)); o > s; )
                            u = 131072 * t[s] + (t[s + 1] >>> 11), u >= 9e15 ? (r = a.getRandomValues(new Uint32Array(2)), t[s] = r[0], t[s + 1] = r[1]) : (f.push(u % 1e14), s += 2);
                        s = o / 2
                    } else if (a && a.randomBytes) {
                        for (t = a.randomBytes(o *= 7); o > s; )
                            u = 281474976710656 * (31 & t[s]) + 1099511627776 * t[s + 1] + 4294967296 * t[s + 2] + 16777216 * t[s + 3] + (t[s + 4] << 16) + (t[s + 5] << 8) + t[s + 6], u >= 9e15 ? a.randomBytes(7).copy(t, s) : (f.push(u % 1e14), s += 7);
                        s = o / 7
                    } else
                        j && L(14, "crypto unavailable", a);
                if (!s)
                    for (; o > s; )
                        u = n(), 9e15 > u && (f[s++] = u % 1e14);
                for (o = f[--s], e %= O, o && e && (u = S[O - e], f[s] = d(o / u) * u); 0 === f[s]; f.pop(), s--)
                    ;
                if (0 > s)
                    f = [i = 0];
                else {
                    for (i = - 1; 0 === f[0]; f.shift(), i -= O)
                        ;
                    for (s = 1, u = f[0]; u >= 10; u /= 10, s++)
                        ;
                    O > s && (i -= O - s)
                }
                return l.e = i, l.c = f, l
            }
        }(), C = function () {
            function e(e, n, t) {
                var r, i, o, u, s = 0, f = e.length, l = n % R, c = n / R | 0;
                for (e = e.slice(); f--; )
                    o = e[f] % R, u = e[f] / R | 0, r = c * o + u * l, i = l * o + r % R * R + s, s = (i / t | 0) + (r / R | 0) + c * u, e[f] = i % t;
                return s && e.unshift(s), e
            }
            function n(e, n, t, r) {
                var i, o;
                if (t != r)
                    o = t > r ? 1 : -1;
                else
                    for (i = o = 0; t > i; i++)
                        if (e[i] != n[i]) {
                            o = e[i] > n[i] ? 1 : -1;
                            break
                        }
                return o
            }
            function r(e, n, t, r) {
                for (var i = 0; t--; )
                    e[t] -= i, i = e[t] < n[t] ? 1 : 0, e[t] = i * r + e[t] - n[t];
                for (; !e[0] && e.length > 1; e.shift())
                    ;
            }
            return function (i, o, u, s, f) {
                var l, c, a, h, g, p, m, w, v, N, y, S, R, A, D, F, _, x = i.s == o.s ? 1 : -1, I = i.c, L = o.c;
                if (!(I && I[0] && L && L[0]))
                    return new E(i.s && o.s && (I ? !L || I[0] != L[0] : L) ? I && 0 == I[0] || !L ? 0 * x : x / 0 : NaN);
                for (w = new E(x), v = w.c = [], c = i.e - o.e, x = u + c + 1, f || (f = b, c = t(i.e / O) - t(o.e / O), x = x / O | 0), a = 0; L[a] == (I[a] || 0); a++)
                    ;
                if (L[a] > (I[a] || 0) && c--, 0 > x)
                    v.push(1), h = !0;
                else {
                    for (A = I.length, F = L.length, a = 0, x += 2, g = d(f / (L[0] + 1)), g > 1 && (L = e(L, g, f), I = e(I, g, f), F = L.length, A = I.length), R = F, N = I.slice(0, F), y = N.length; F > y; N[y++] = 0)
                        ;
                    _ = L.slice(), _.unshift(0), D = L[0], L[1] >= f / 2 && D++;
                    do {
                        if (g = 0, l = n(L, N, F, y), 0 > l) {
                            if (S = N[0], F != y && (S = S * f + (N[1] || 0)), g = d(S / D), g > 1)
                                for (g >= f && (g = f - 1), p = e(L, g, f), m = p.length, y = N.length; 1 == n(p, N, m, y); )
                                    g--, r(p, m > F ? _ : L, m, f), m = p.length, l = 1;
                            else
                                0 == g && (l = g = 1), p = L.slice(), m = p.length;
                            if (y > m && p.unshift(0), r(N, p, y, f), y = N.length, -1 == l)
                                for (; n(L, N, F, y) < 1; )
                                    g++, r(N, y > F ? _ : L, y, f), y = N.length
                        } else
                            0 === l && (g++, N = [0]);
                        v[a++] = g, N[0] ? N[y++] = I[R] || 0 : (N = [I[R]], y = 1)
                    } while ((R++ < A || null != N[0]) && x--);
                    h = null != N[0], v[0] || v.shift()
                }
                if (f == b) {
                    for (a = 1, x = v[0]; x >= 10; x /= 10, a++)
                        ;
                    U(w, u + (w.e = a + c * O - 1) + 1, s, h)
                } else
                    w.e = c, w.r = +h;
                return w
            }
        }(), h = function () {
            var e = /^(-?)0([xbo])(?=\w[\w.]*$)/i, n = /^([^.]+)\.$/, t = /^\.([^.]+)$/, r = /^-?(Infinity|NaN)$/, i = /^\s*\+(?=[\w.])|^\s+|\s+$/g;
            return function (o, u, s, f) {
                var l, c = s ? u : u.replace(i, "");
                if (r.test(c))
                    o.s = isNaN(c) ? null : 0 > c ? -1 : 1;
                else {
                    if (!s && (c = c.replace(e, function (e, n, t) {
                        return l = "x" == (t = t.toLowerCase()) ? 16 : "b" == t ? 2 : 8, f && f != l ? e : n
                    }), f && (l = f, c = c.replace(n, "$1").replace(t, "0.$1")), u != c))
                        return new E(c, l);
                    j && L(M, "not a" + (f ? " base " + f : "") + " number", u), o.s = null
                }
                o.c = o.e = null, M = 0
            }
        }(), T.absoluteValue = T.abs = function () {
            var e = new E(this);
            return e.s < 0 && (e.s = 1), e
        }, T.ceil = function () {
            return U(new E(this), this.e + 1, 2)
        }, T.comparedTo = T.cmp = function (e, n) {
            return M = 1, i(this, new E(e, n))
        }, T.decimalPlaces = T.dp = function () {
            var e, n, r = this.c;
            if (!r)
                return null;
            if (e = ((n = r.length - 1) - t(this.e / O)) * O, n = r[n])
                for (; n % 10 == 0; n /= 10, e--)
                    ;
            return 0 > e && (e = 0), e
        }, T.dividedBy = T.div = function (e, n) {
            return M = 3, C(this, new E(e, n), P, k)
        }, T.dividedToIntegerBy = T.divToInt = function (e, n) {
            return M = 4, C(this, new E(e, n), 0, 1)
        }, T.equals = T.eq = function (e, n) {
            return M = 5, 0 === i(this, new E(e, n))
        }, T.floor = function () {
            return U(new E(this), this.e + 1, 3)
        }, T.greaterThan = T.gt = function (e, n) {
            return M = 6, i(this, new E(e, n)) > 0
        }, T.greaterThanOrEqualTo = T.gte = function (e, n) {
            return M = 7, 1 === (n = i(this, new E(e, n))) || 0 === n
        }, T.isFinite = function () {
            return!!this.c
        }, T.isInteger = T.isInt = function () {
            return!!this.c && t(this.e / O) > this.c.length - 2
        }, T.isNaN = function () {
            return!this.s
        }, T.isNegative = T.isNeg = function () {
            return this.s < 0
        }, T.isZero = function () {
            return!!this.c && 0 == this.c[0]
        }, T.lessThan = T.lt = function (e, n) {
            return M = 8, i(this, new E(e, n)) < 0
        }, T.lessThanOrEqualTo = T.lte = function (e, n) {
            return M = 9, -1 === (n = i(this, new E(e, n))) || 0 === n
        }, T.minus = T.sub = function (e, n) {
            var r, i, o, u, s = this, f = s.s;
            if (M = 10, e = new E(e, n), n = e.s, !f || !n)
                return new E(NaN);
            if (f != n)
                return e.s = -n, s.plus(e);
            var l = s.e / O, c = e.e / O, a = s.c, h = e.c;
            if (!l || !c) {
                if (!a || !h)
                    return a ? (e.s = -n, e) : new E(h ? s : NaN);
                if (!a[0] || !h[0])
                    return h[0] ? (e.s = -n, e) : new E(a[0] ? s : 3 == k ? -0 : 0)
            }
            if (l = t(l), c = t(c), a = a.slice(), f = l - c) {
                for ((u = 0 > f)?(f = - f, o = a):(c = l, o = h), o.reverse(), n = f; n--; o.push(0))
                    ;
                o.reverse()
            } else
                for (i = (u = (f = a.length) < (n = h.length))?f:n, f = n = 0; i > n; n++)
                    if (a[n] != h[n]) {
                        u = a[n] < h[n];
                        break
                    }
            if (u && (o = a, a = h, h = o, e.s = -e.s), n = (i = h.length) - (r = a.length), n > 0)
                for (; n--; a[r++] = 0)
                    ;
            for (n = b - 1; i > f; ) {
                if (a[--i] < h[i]) {
                    for (r = i; r && !a[--r]; a[r] = n)
                        ;
                    --a[r], a[i] += b
                }
                a[i] -= h[i]
            }
            for (; 0 == a[0]; a.shift(), --c)
                ;
            return a[0] ? I(e, a, c) : (e.s = 3 == k ? -1 : 1, e.c = [e.e = 0], e)
        }, T.modulo = T.mod = function (e, n) {
            var t, r, i = this;
            return M = 11, e = new E(e, n), !i.c || !e.s || e.c && !e.c[0] ? new E(NaN) : !e.c || i.c && !i.c[0] ? new E(i) : (9 == W ? (r = e.s, e.s = 1, t = C(i, e, 0, 3), e.s = r, t.s *= r) : t = C(i, e, 0, W), i.minus(t.times(e)))
        }, T.negated = T.neg = function () {
            var e = new E(this);
            return e.s = -e.s || null, e
        }, T.plus = T.add = function (e, n) {
            var r, i = this, o = i.s;
            if (M = 12, e = new E(e, n), n = e.s, !o || !n)
                return new E(NaN);
            if (o != n)
                return e.s = -n, i.minus(e);
            var u = i.e / O, s = e.e / O, f = i.c, l = e.c;
            if (!u || !s) {
                if (!f || !l)
                    return new E(o / 0);
                if (!f[0] || !l[0])
                    return l[0] ? e : new E(f[0] ? i : 0 * o)
            }
            if (u = t(u), s = t(s), f = f.slice(), o = u - s) {
                for (o > 0?(s = u, r = l):(o = - o, r = f), r.reverse(); o--; r.push(0))
                    ;
                r.reverse()
            }
            for (o = f.length, n = l.length, 0 > o - n && (r = l, l = f, f = r, n = o), o = 0; n; )
                o = (f[--n] = f[n] + l[n] + o) / b | 0, f[n] %= b;
            return o && (f.unshift(o), ++s), I(e, f, s)
        }, T.precision = T.sd = function (e) {
            var n, t, r = this, i = r.c;
            if (null != e && e !== !!e && 1 !== e && 0 !== e && (j && L(13, "argument" + m, e), e != !!e && (e = null)), !i)
                return null;
            if (t = i.length - 1, n = t * O + 1, t = i[t]) {
                for (; t % 10 == 0; t /= 10, n--)
                    ;
                for (t = i[0]; t >= 10; t /= 10, n++)
                    ;
            }
            return e && r.e + 1 > n && (n = r.e + 1), n
        }, T.round = function (e, n) {
            var t = new E(this);
            return(null == e || H(e, 0, A, 15)) && U(t, ~~e + this.e + 1, null != n && H(n, 0, 8, 15, w) ? 0 | n : k), t
        }, T.shift = function (e) {
            var n = this;
            return H(e, -y, y, 16, "argument") ? n.times("1e" + c(e)) : new E(n.c && n.c[0] && (-y > e || e > y) ? n.s * (0 > e ? 0 : 1 / 0) : n)
        }, T.squareRoot = T.sqrt = function () {
            var e, n, i, o, u, s = this, f = s.c, l = s.s, c = s.e, a = P + 4, h = new E("0.5");
            if (1 !== l || !f || !f[0])
                return new E(!l || 0 > l && (!f || f[0]) ? NaN : f ? s : 1 / 0);
            if (l = Math.sqrt(+s), 0 == l || l == 1 / 0 ? (n = r(f), (n.length + c) % 2 == 0 && (n += "0"), l = Math.sqrt(n), c = t((c + 1) / 2) - (0 > c || c % 2), l == 1 / 0 ? n = "1e" + c : (n = l.toExponential(), n = n.slice(0, n.indexOf("e") + 1) + c), i = new E(n)) : i = new E(l + ""), i.c[0])
                for (c = i.e, l = c + a, 3 > l && (l = 0); ; )
                    if (u = i, i = h.times(u.plus(C(s, u, a, 1))), r(u.c).slice(0, l) === (n = r(i.c)).slice(0, l)) {
                        if (i.e < c && --l, n = n.slice(l - 3, l + 1), "9999" != n && (o || "4999" != n)) {
                            (!+n || !+n.slice(1) && "5" == n.charAt(0)) && (U(i, i.e + P + 2, 1), e = !i.times(i).eq(s));
                            break
                        }
                        if (!o && (U(u, u.e + P + 2, 0), u.times(u).eq(s))) {
                            i = u;
                            break
                        }
                        a += 4, l += 4, o = 1
                    }
            return U(i, i.e + P + 1, k, e)
        }, T.times = T.mul = function (e, n) {
            var r, i, o, u, s, f, l, c, a, h, g, p, d, m, w, v = this, N = v.c, y = (M = 17, e = new E(e, n)).c;
            if (!(N && y && N[0] && y[0]))
                return!v.s || !e.s || N && !N[0] && !y || y && !y[0] && !N ? e.c = e.e = e.s = null : (e.s *= v.s, N && y ? (e.c = [0], e.e = 0) : e.c = e.e = null), e;
            for (i = t(v.e / O) + t(e.e / O), e.s *= v.s, l = N.length, h = y.length, h > l && (d = N, N = y, y = d, o = l, l = h, h = o), o = l + h, d = []; o--; d.push(0))
                ;
            for (m = b, w = R, o = h; --o >= 0; ) {
                for (r = 0, g = y[o] % w, p = y[o] / w | 0, s = l, u = o + s; u > o; )
                    c = N[--s] % w, a = N[s] / w | 0, f = p * c + a * g, c = g * c + f % w * w + d[u] + r, r = (c / m | 0) + (f / w | 0) + p * a, d[u--] = c % m;
                d[u] = r
            }
            return r ? ++i : d.shift(), I(e, d, i)
        }, T.toDigits = function (e, n) {
            var t = new E(this);
            return e = null != e && H(e, 1, A, 18, "precision") ? 0 | e : null, n = null != n && H(n, 0, 8, 18, w) ? 0 | n : k, e ? U(t, e, n) : t
        }, T.toExponential = function (e, n) {
            return F(this, null != e && H(e, 0, A, 19) ? ~~e + 1 : null, n, 19)
        }, T.toFixed = function (e, n) {
            return F(this, null != e && H(e, 0, A, 20) ? ~~e + this.e + 1 : null, n, 20)
        }, T.toFormat = function (e, n) {
            var t = F(this, null != e && H(e, 0, A, 21) ? ~~e + this.e + 1 : null, n, 21);
            if (this.c) {
                var r, i = t.split("."), o = +X.groupSize, u = +X.secondaryGroupSize, s = X.groupSeparator, f = i[0], l = i[1], c = this.s < 0, a = c ? f.slice(1) : f, h = a.length;
                if (u && (r = o, o = u, u = r, h -= r), o > 0 && h > 0) {
                    for (r = h % o || o, f = a.substr(0, r); h > r; r += o)
                        f += s + a.substr(r, o);
                    u > 0 && (f += s + a.slice(r)), c && (f = "-" + f)
                }
                t = l ? f + X.decimalSeparator + ((u = +X.fractionGroupSize) ? l.replace(new RegExp("\\d{" + u + "}\\B", "g"), "$&" + X.fractionGroupSeparator) : l) : f
            }
            return t
        }, T.toFraction = function (e) {
            var n, t, i, o, u, s, f, l, c, a = j, h = this, g = h.c, p = new E(q), d = t = new E(q), m = f = new E(q);
            if (null != e && (j = !1, s = new E(e), j = a, (!(a = s.isInt()) || s.lt(q)) && (j && L(22, "max denominator " + (a ? "out of range" : "not an integer"), e), e = !a && s.c && U(s, s.e + 1, 1).gte(q) ? s : null)), !g)
                return h.toString();
            for (c = r(g), o = p.e = c.length - h.e - 1, p.c[0] = S[(u = o % O) < 0?O + u:u], e = !e || s.cmp(p) > 0?o > 0?p:d:s, u = z, z = 1 / 0, s = new E(c), f.c[0] = 0; l = C(s, p, 0, 1), i = t.plus(l.times(m)), 1 != i.cmp(e); )
                t = m, m = i, d = f.plus(l.times(i = d)), f = i, p = s.minus(l.times(i = p)), s = i;
            return i = C(e.minus(t), m, 0, 1), f = f.plus(i.times(d)), t = t.plus(i.times(m)), f.s = d.s = h.s, o *= 2, n = C(d, m, o, k).minus(h).abs().cmp(C(f, t, o, k).minus(h).abs()) < 1 ? [d.toString(), m.toString()] : [f.toString(), t.toString()], z = u, n
        }, T.toNumber = function () {
            return +this
        }, T.toPower = T.pow = function (e, n) {
            var t, r, i, o = d(0 > e ? -e : +e), u = this;
            if (null != n && (M = 23, n = new E(n)), !H(e, -y, y, 23, "exponent") && (!isFinite(e) || o > y && (e /= 0) || parseFloat(e) != e && !(e = NaN)) || 0 == e)
                return t = Math.pow(+u, e), new E(n ? t % n : t);
            for (n ? e > 1 && u.gt(q) && u.isInt() && n.gt(q) && n.isInt() ? u = u.mod(n) : (i = n, n = null) : J && (t = p(J / O + 2)), r = new E(q); ; ) {
                if (o % 2) {
                    if (r = r.times(u), !r.c)
                        break;
                    t ? r.c.length > t && (r.c.length = t) : n && (r = r.mod(n))
                }
                if (o = d(o / 2), !o)
                    break;
                u = u.times(u), t ? u.c && u.c.length > t && (u.c.length = t) : n && (u = u.mod(n))
            }
            return n ? r : (0 > e && (r = q.div(r)), i ? r.mod(i) : t ? U(r, J, k) : r)
        }, T.toPrecision = function (e, n) {
            return F(this, null != e && H(e, 1, A, 24, "precision") ? 0 | e : null, n, 24)
        }, T.toString = function (e) {
            var n, t = this, i = t.s, o = t.e;
            return null === o ? i ? (n = "Infinity", 0 > i && (n = "-" + n)) : n = "NaN" : (n = r(t.c), n = null != e && H(e, 2, 64, 25, "base") ? D(l(n, o), 0 | e, 10, i) : B >= o || o >= $ ? f(n, o) : l(n, o), 0 > i && t.c[0] && (n = "-" + n)), n
        }, T.truncated = T.trunc = function () {
            return U(new E(this), this.e + 1, 1)
        }, T.valueOf = T.toJSON = function () {
            var e, n = this, t = n.e;
            return null === t ? n.toString() : (e = r(n.c), e = B >= t || t >= $ ? f(e, t) : l(e, t), n.s < 0 ? "-" + e : e)
        }, null != e && E.config(e), E
    }
    function t(e) {
        var n = 0 | e;
        return e > 0 || e === n ? n : n - 1
    }
    function r(e) {
        for (var n, t, r = 1, i = e.length, o = e[0] + ""; i > r; ) {
            for (n = e[r++] + "", t = O - n.length; t--; n = "0" + n)
                ;
            o += n
        }
        for (i = o.length; 48 === o.charCodeAt(--i); )
            ;
        return o.slice(0, i + 1 || 1)
    }
    function i(e, n) {
        var t, r, i = e.c, o = n.c, u = e.s, s = n.s, f = e.e, l = n.e;
        if (!u || !s)
            return null;
        if (t = i && !i[0], r = o && !o[0], t || r)
            return t ? r ? 0 : -s : u;
        if (u != s)
            return u;
        if (t = 0 > u, r = f == l, !i || !o)
            return r ? 0 : !i ^ t ? 1 : -1;
        if (!r)
            return f > l ^ t ? 1 : -1;
        for (s = (f = i.length) < (l = o.length)?f:l, u = 0; s > u; u++)
            if (i[u] != o[u])
                return i[u] > o[u] ^ t ? 1 : -1;
        return f == l ? 0 : f > l ^ t ? 1 : -1
    }
    function o(e, n, t) {
        return(e = c(e)) >= n && t >= e
    }
    function u(e) {
        return"[object Array]" == Object.prototype.toString.call(e)
    }
    function s(e, n, t) {
        for (var r, i, o = [0], u = 0, s = e.length; s > u; ) {
            for (i = o.length; i--; o[i] *= n)
                ;
            for (o[r = 0] += N.indexOf(e.charAt(u++)); r < o.length; r++)
                o[r] > t - 1 && (null == o[r + 1] && (o[r + 1] = 0), o[r + 1] += o[r] / t | 0, o[r] %= t)
        }
        return o.reverse()
    }
    function f(e, n) {
        return(e.length > 1 ? e.charAt(0) + "." + e.slice(1) : e) + (0 > n ? "e" : "e+") + n
    }
    function l(e, n) {
        var t, r;
        if (0 > n) {
            for (r = "0."; ++n; r += "0")
                ;
            e = r + e
        } else if (t = e.length, ++n > t) {
            for (r = "0", n -= t; --n; r += "0")
                ;
            e += r
        } else
            t > n && (e = e.slice(0, n) + "." + e.slice(n));
        return e
    }
    function c(e) {
        return e = parseFloat(e), 0 > e ? p(e) : d(e)
    }
    var a, h, g = /^-?(\d+(\.\d*)?|\.\d+)(e[+-]?\d+)?$/i, p = Math.ceil, d = Math.floor, m = " not a boolean or binary digit", w = "rounding mode", v = "number type has more than 15 significant digits", N = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ$_", b = 1e14, O = 14, y = 9007199254740991, S = [1, 10, 100, 1e3, 1e4, 1e5, 1e6, 1e7, 1e8, 1e9, 1e10, 1e11, 1e12, 1e13], R = 1e7, A = 1e9;
    if ("undefined" != typeof crypto && (a = crypto), "function" == typeof define && define.amd)
        define(function () {
            return n()
        });
    else if ("undefined" != typeof module && module.exports) {
        if (module.exports = n(), !a)
            try {
                a = require("crypto")
            } catch (E) {
            }
    } else
        e || (e = "undefined" != typeof self ? self : Function("return this")()), e.BigNumber = n()
}(this);

//  controllo ONLINE OFFLINE
function online() {
//    var image = new Image();
//    image.src = "http://1.bp.blogspot.com/-y5G5_rDoXag/TcnuB6a8aYI/AAAAAAAARaw/ELKSsE0cprI/s200/google_immagini_virus.JPG";
//    var b1 = false;
//    if (image.complete && image.naturalWidth) {
//        b1 = true;
//    }
//    setTimeout(online, 5000);
    document.getElementById("onlineko").style.display = "none";
    document.getElementById("onlineok").style.display = "";
    $.ajax({
        async: false,
        type: "POST",
        url: "Time",
        success: function (result) {
            if (result === "true") {
                document.getElementById("onlineko").style.display = "none";
                document.getElementById("onlineok").style.display = "";
            } else {
                document.getElementById("onlineok").style.display = "none";
                document.getElementById("onlineko").style.display = "";
            }
        }
    });
//    setTimeout(online, 1000);


    //var b1 = navigator.onLine;
    //if (b1) {
    //    document.getElementById("onlineko").style.display = "none";
    //    document.getElementById("onlineok").style.display = "";
    //} else {
    //    document.getElementById("onlineok").style.display = "none";
    //    document.getElementById("onlineko").style.display = "";
    //}
}

//  controlla campo se corretto formalmente
function checkValueCorrect(value) {
    if (value !== null) {
        if (!isNaN(value)) {
            if (value !== "") {
                return true;
            }
        }
    }
    return false;
}

//show login
function showmodlogin(modal) {
    document.getElementById(modal).className = document.getElementById(modal).className + " in";
    document.getElementById(modal).style.display = "block";
}
//checkloginpassword
function checkvaluepass(psval, originalpass, modal, errmsg) {
    if (CryptoJS.MD5(document.getElementById(psval).value.trim()).toString() === originalpass) {
        document.getElementById(psval).value = '';
        $('#' + modal).modal('hide');
    } else {
        document.getElementById(psval).value = '';
        document.getElementById(errmsg).style.display = "";
    }
}
//checkevent
function checkkeysub(butclick, event) {
    if (event.keyCode === 13) {
        document.getElementById(butclick).click();
    }
}


function keysub(obj, e) {
    var e = (typeof event !== 'undefined') ? window.event : e;// IE : Moz 
    if (e.keyCode === 13) {
        document.getElementById('test1').style.display = '';
        document.getElementById('test1').focus();
        obj.focus();
        document.getElementById('test1').style.display = 'none';
        return false;
    }
}


function padDigits(number, digits) {
    return Array(Math.max(digits - String(number).length + 1, 0)).join(0) + number;
}

function fieldNameSurname(fieldid) {
    var stringToReplace = document.getElementById(fieldid).value;
    var specialChars = "~`!#$%^&*+=-[]();,/{}|\":<>?£,.àáâãäçèéêëìíîïñòóôõöùúûüýÿÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ°èéòàù§*ç@|!£$%&/()=?^€ì";
    for (var i = 0; i < specialChars.length; i++) {
        stringToReplace = stringToReplace.replace(new RegExp("\\" + specialChars[i], 'gi'), '');
    }
    stringToReplace = stringToReplace.replace(/\\/g, "");
    stringToReplace = stringToReplace.replace(new RegExp("[0-9]", "g"), "");
    document.getElementById(fieldid).value = stringToReplace;
}

function fieldOnlyNumber(fieldid) {
    var stringToReplace = document.getElementById(fieldid).value;
    stringToReplace = stringToReplace.replace(/\D/g, '');
    document.getElementById(fieldid).value = stringToReplace;
}

function fieldOnlyNumber_RA(fieldid) {
    var stringToReplace = document.getElementById(fieldid).value;
    stringToReplace = stringToReplace.replace(/\D/g, '');
    if (stringToReplace.trim() === "") {
        stringToReplace = "0";
    }
    if (parseIntRaf(stringToReplace) === 0) {
        stringToReplace = "1";
    }
    document.getElementById(fieldid).value = stringToReplace;
}



function fieldNOSPecial(fieldid) {
    var stringToReplace = document.getElementById(fieldid).value;

    stringToReplace = stringToReplace.replace(/[&\/\\#+()$~%'":*?<>{}],/g, '');
    document.getElementById(fieldid).value = stringToReplace;
}

function fieldNOSPecial_1(fieldid) {
    var stringToReplace = document.getElementById(fieldid).value;
    var specialChars = "~`!#$%^&*+=-[];,{}()|\":<>?£ààáâãäçèéêëìíîïñòóôõö€ùúûüýÿÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ°€";
    for (var i = 0; i < specialChars.length; i++) {
        stringToReplace = stringToReplace.replace(new RegExp("\\" + specialChars[i], 'gi'), '');
    }
    //stringToReplace = stringToReplace.replace(/\\/g, "");
//    stringToReplace = stringToReplace.replace(new RegExp("[0-9]", "g"), "");
    document.getElementById(fieldid).value = stringToReplace;
}

function fieldNOSPecial_2(fieldid) {
    var stringToReplace = document.getElementById(fieldid).value;
    var specialChars = "~`!#$%^&*+=-[];{}|\":<>?£€°§";
    for (var i = 0; i < specialChars.length; i++) {
        stringToReplace = stringToReplace.replace(new RegExp("\\" + specialChars[i], 'gi'), '');
    }
    //  stringToReplace = stringToReplace.replace(/\\/g, "");
//    stringToReplace = stringToReplace.replace(new RegExp("[0-9]", "g"), "");
    document.getElementById(fieldid).value = stringToReplace;
}


function onbottom() {
    $("html, body").animate({scrollTop: $(document).height()}, 1000);
}


function isValidDate(str) {
    var d = moment(str, 'D/M/YYYY');
    if (d === null || !d.isValid())
        return false;

    return str.indexOf(d.format('D/M/YYYY')) >= 0
            || str.indexOf(d.format('DD/MM/YYYY')) >= 0
            || str.indexOf(d.format('D/M/YY')) >= 0
            || str.indexOf(d.format('DD/MM/YY')) >= 0;
}

function checkpass(psval, originalpass) {
    if (CryptoJS.MD5(psval).toString() === originalpass.toString()) {
        return true;
    } else {
        return false;
    }
}

function encryptmd5(psval) {
    return CryptoJS.MD5(psval).toString();
}


function isempty(val) {
    if (val === "" || val === "None" || val === "..." || val === "---") {
        return true;
    }
    return false;
}

function isemptyField(field) {
    if (!field.disabled) {
        if (field.value.trim() === "" || field.value.trim() === "None" || field.value.trim() === "..." || field.value.trim() === "---") {
            return true;
        }
    }
    return false;
}



function replacethousand(value, sep) {
    if (sep === ",") {
        value = value.replace(/,/g, '');
    } else if (sep === ".") {
        value = value.replace(/\./g, '');
    }
    return value;
}


function isPhone(value, lenmin, lenmax) {
    value = value.trim();
    if (value.length >= lenmin && value.length <= lenmax) {
        var phoneno = /^([+|\d])+([\s|\d])+([\d])$/;
        //var phoneno = /^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/im;
        if (value.match(phoneno)) {
            return true;
        } else {
            return false;
        }
    }
    return false;
}

function disable_sel2(id, idform) {
    var $dropDown = $('#' + id), name = id, $form = $dropDown.parent('form');
    $dropDown.data('original-name', name);
    $('#' + idform).append('<input type="hidden" name="' + name + '" value="' + $dropDown.val() + '" />');
    $dropDown.addClass('disabled').prop({name: name + "_r", disabled: true});
}

function enable_sel2(id, idform) {
    var $dropDown = $('#' + id), name = id, $form = $dropDown.parent('form');
    $dropDown.data('original-name', name);
    $("input[type='hidden'][name=" + name + "]").remove();
    $form.find('input[type="hidden"][name=' + name + ']').remove();
    $dropDown.removeClass('disabled').prop({name: name, disabled: false});
}



function waitform() {
    document.getElementById("modalwaitbutton").click();
}


function getvalueofField(field) {
    if (field === null) {
        return "";
    } else if (field.tagName === "INPUT") {
        return field.value.trim();
    } else if (field.tagName === "TD") {
        return field.innerHTML.trim();
    } else if (field.tagName === "SPAN") {
        return field.innerHTML.trim();
    }

    return "";
}
function getvalueofFieldINT(field) {
    if (field === null) {
        return "0";
    } else if (field.tagName === "INPUT") {
        return field.value.trim();
    } else if (field.tagName === "SPAN") {
        return field.innerHTML.trim();
    }
    return "0";
}

function inputvirgola() {
    $(document).find('input').keydown(function (evt) {
        if (evt.which === 110) {
            $(this).val($(this).val() + ',');
            evt.preventDefault();
        }
    });
}

function replacedoublequotes(mystring) {
    mystring = mystring.replace(/"/g, '\'');
    return mystring;
}

