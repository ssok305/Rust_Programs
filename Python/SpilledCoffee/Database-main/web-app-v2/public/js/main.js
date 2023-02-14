'use strict';

$( document ).ready(function() {

    // console.log(document.location.pathname);
    var fadeSlideClasses = "remove-fade-slide-right remove-fade-slide-left remove-fade-slide-top remove-fade-slide-bottom";
    var fadeWidthClasses = "remove-fade-x remove-fade";
    var fadeOnlyClasses = "remove-fade";

    if (document.location.pathname === "/spilledcoffee-mvc/") {
        $(".fade-1s").removeClass(fadeSlideClasses + " " + fadeWidthClasses);
    }

    if (document.URL.indexOf("products") >= 0){
        var timeout = 100;
        var items = $( ".fade-1s").toArray();
        for (let i = 0; i < items.length ; i++) {
            setTimeout(() => {
                console.log(items[i]);
                $(items[i]).removeClass(fadeSlideClasses + " " + fadeWidthClasses);
            }, timeout);
            timeout += 150;
        }
        console.log('Done!');
    }

    if (document.URL.indexOf("cart") >= 0) {
        $(".fade-1s").removeClass(fadeSlideClasses + " " + fadeWidthClasses);
    }

    if (document.URL.indexOf("orders") >= 0) {
        $(".fade-1s").removeClass(fadeSlideClasses + " " + fadeWidthClasses);
    }

    if (document.URL.indexOf("checkout") >= 0) {
        $(".fade-1s").removeClass(fadeSlideClasses + " " + fadeWidthClasses);
    }

    if (document.URL.indexOf("about") >= 0) {
        $(".fade-1s").removeClass(fadeSlideClasses + " " + fadeWidthClasses);
    } 

    // if (document.URL.indexOf("reports") >= 0) {
    //     $(window).resize(function(){
    //         drawTopCustomers();
    //         drawTopProducts();
    //     });
    // } 

});

// function exportReports() {
//     console.log("Exporting...");
//     html2canvas(document.querySelector("#capture")).then(canvas => {
//         document.body.appendChild(canvas)
//     });
// }

if (document.URL.indexOf("reports") >= 0) {
    document.getElementById("exportReports").addEventListener("click", function() {
        // window.scrollTo(0,0);
        html2canvas(document.querySelector('#capture'), {scrollY: -window.scrollY}).then(function(canvas) {
            saveAs(canvas.toDataURL(), 'report-export.png');
        });
    });
}


function saveAs(uri, filename) {

    var link = document.createElement('a');

    if (typeof link.download === 'string') {

        link.href = uri;
        link.download = filename;

        //Firefox requires the link to be in the body
        document.body.appendChild(link);

        //simulate click
        link.click();

        //remove the link when done
        document.body.removeChild(link);

    } else {

        window.open(uri);

    }
}