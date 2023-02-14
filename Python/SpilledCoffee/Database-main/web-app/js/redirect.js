'use strict';

function redirect (product_id) {
    // (B1) APPEND THE JS VARIABLES AS QUERY STRING
    var src = "includes/add-to-cart-inc.php";
    src += "?product_id=" + product_id;
    var id = document.getElementById(product_id + "quantity").value
    src += "&quantity=" + id;
    src = encodeURI(src);
    
    // (B2) REDIRECTION
    window.location.href = src;
    return true;
}