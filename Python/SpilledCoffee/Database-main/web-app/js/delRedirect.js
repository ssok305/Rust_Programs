'use strict';

function delRedirect (cp) {
    // (B1) APPEND THE JS VARIABLES AS QUERY STRING
    var src = "includes/delete-from-cart-inc.php";
    src += "?cp=" + cp;
    src = encodeURI(src);
    
    // (B2) REDIRECTION
    window.location.href = src;
    return true;
}