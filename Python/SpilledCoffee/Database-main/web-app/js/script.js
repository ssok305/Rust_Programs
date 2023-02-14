'use strict';

$(document).ready(function(){

    const userMessage = document.querySelector('.user-message');
    const btnCloseUserMessage = document.querySelector('.close-user-message');
    console.log(btnCloseUserMessage);

    if (btnCloseUserMessage) {
        btnCloseUserMessage.addEventListener('click', function() {
            console.log('Button clicked');
            userMessage.classList.add('hidden');
        })
    }

})

function showModal(type) {

    console.log('Button clicked');

    const overlay = document.querySelector('.overlay');
    const modal = document.querySelector('.modal');

    if (type === 1) {
        $('#modal-contents').load('login-form.php');
        var done = true;
    } else if (type === 2) {
        $('#modal-contents').load('signup-form.php');
        var done = true;
    } else if (type === 3) {
        $('#modal-contents').load('includes/cancel-confirm-inc.php');
        var done = true;
    }

    $('#modal').css({position: 'fixed'})

    // if (type === 1) {
    //     $(document).ready(function(){
    //         $('#modal-contents').load('login-form.php');
    //     });
    // } else if (type === 2) {
    //     $(document).ready(function(){
    //         $('#modal-contents').load('signup-form.php');
    //     });
    // } else if (type === 3) {
    //     $(document).ready(function(){
    //         $('#modal-contents').load('includes/cancel-confirm-inc.php');
    //     });
    // }

    if (done = true) {
        overlay.classList.remove('hidden');
        modal.classList.remove('hidden');
        overlay.style.opacity = 1;
        modal.style.opacity = 1;
    }

    // $('body').classList.add('fixed');
    // document.body.classList.add('fixed');
    // document.body.style.position = 'fixed';
    // document.body.style.top = '0px';

}

function closeModal() {

    console.log('Button clicked');

    const overlay = document.querySelector('.overlay');
    const modal = document.querySelector('.modal');
    overlay.style.opacity = 0;
    modal.style.opacity = 0;
    setTimeout(() => { 
        overlay.classList.add('hidden');
        modal.classList.add('hidden');
        $('#modal-contents').empty();
    }, 800);
    
}

function submitLogin() {
    console.log('submitLogin called');
    $("#login-message").empty();
    $("#login-message").load("includes/login-inc-test.php", {
        submit: true,
        uid: $("#uid").val(),
        pass: $("#psw").val(),
    }, function(data) {
        if (data == '') {
            location.reload();
        }
    });
}

function submitSignUp() {
    console.log('submitSignUp called');
    $("#signup-message").empty();
    $("#signup-message").load("includes/signup-inc-test.php", {
        submit: true,
        fname: $("#fname").val(),
        email: $("#email").val(),
        uname: $("#uname").val(),
        psw: $("#psw").val(),
        pswrep: $("#pswrep").val()
    }, function(data) {
        if (data == '') {
            location.reload();
        }
    });
}

function showCancelConfirm(order_id) {

    console.log('Button clicked');

    const overlay = document.querySelector('.overlay');
    const modal = document.querySelector('.modal');
    overlay.classList.remove('hidden');
    modal.classList.remove('hidden');
    overlay.style.opacity = 1;
    modal.style.opacity = 1;

    $(document).ready(function(){
        $('#modal-contents').load('includes/cancel-confirm-inc.php');
        $('input[name="modal-data"]').val(order_id);
    });

    // const func = 'confirmCancel(' + order_id + ')';
    // document.getElementById('cancel-confirm').setAttribute('onclick', func);

}

function confirmCancel() {
    console.log('confirmCancel called');
    $("#modal-contents").load("includes/cancel-order-inc.php", {
        submit: true,
        order_id: $("#modal-data").val()
    }, function() {
        location.reload();
    });
}