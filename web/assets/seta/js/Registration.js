/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var context = document.getElementById("registration").getAttribute("data-context");
var g_key = document.getElementById("registration").getAttribute("data-gKey");

grecaptcha.ready(function () {
//    showLoadTitle('Stiamo verificando la tua identità');
    grecaptcha.execute(g_key).then(function (token) {
        $.ajax({
            type: "POST",
            async: false,
            url: context + '/Operazioni',
            data: {'action': "botAreU", 'g-recaptcha-response': token},
            success: function (data) {
//                closeSwal();
                var json = JSON.parse(data);
                if (json.result) {
//                    $('#g-recaptcha-response').val(token);
                    $('#submit_change').css('display', '');
                } else {
                    $("#kt_form").remove();
                    $('#submit_change').remove();
                    fastSwalElementResponsive('<h4>Ci dispiace ma Google ti ha rilevato come bot</h4>', 'OK');
                }
            }
        });
    });
});