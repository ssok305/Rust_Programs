<?php

    // Setup functions that can be used on any page

    // Start session on every page
    session_start();

    // Sets cart if one does not exist
    if (!isset($_SESSION['cart']) && !isset($_SESSION['guest_cart'])){
        $_SESSION['guest_cart'] = array();
    }

    // Flash message helper
    function flash($name = '', $message = '', $class = 'alert alert-success text-center'){
        if(!empty($name)){
            if(!empty($message) && empty($_SESSION[$name])){
                if(!empty($_SESSION[$name])){
                    unset($_SESSION[$name]);
                }
                if(!empty($_SESSION[$name . '_class'])){
                    unset($_SESSION[$name . '_class']);
                }
                $_SESSION[$name] = $message;
                $_SESSION[$name . '_class'] = $class;
            } elseif (empty($message) && !empty($_SESSION[$name])) {
                $class = !empty($_SESSION[$name . '_class']) ? $_SESSION[$name . '_class'] : '';
                echo '<div class="'.$class.'" id="msg-flash">'.$_SESSION[$name].'</div>';
                unset($_SESSION[$name]);
                unset($_SESSION[$name . '_class']);

            }
        }
    }

    // Function to use to restrict pages or functions to a logged in user
    function isLoggedIn(){
        if (isset($_SESSION['user_id'])){
            return true;
        } else {
            return false;
        }
    }

    // Returns number of items in cart
    function cartItemCount(){
        $items = 0;
        if (!isset($_SESSION['cart'])){
            if (!empty($_SESSION['guest_cart'])){
                foreach ($_SESSION['guest_cart'] as $cartItem){
                    $items += $cartItem[1];
                }
                return $items;
            } else {
                return $items;
            }
        } else {
            if (!empty($_SESSION['cart'])){
                foreach ($_SESSION['cart'] as $cartItem){
                    $items += $cartItem[1];
                }
                return $items;
            } else {
                return $items;
            }
        }
    }