'use strict';

$( document ).ready(function() {
    var timeout = 100;
    var items = $( ".fade-1s").toArray();
    for (let i = 0; i < items.length ; i++) {
        setTimeout(() => {
            console.log(items[i]);
            $(items[i]).removeClass("remove-fade");
            $(items[i]).removeClass("remove-fade-slide-bottom");
        }, timeout);
        timeout += 200;
    }
    console.log('Done!');
});