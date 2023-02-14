<?php

    // User page
    // Handles register, login, sessions, user cart and guest cart

    class Users extends Controller {

        // Constructor
        public function __construct(){
            $this->userModel = $this->model('User');
            $this->productModel = $this->model('Product');
            $this->orderModel = $this->model('Order');
        }

        // Index page, not used
        public function index(){
            $data = [
                'title' => 'WELCOME',
            ];

            $this->view('pages/index', $data);

        }

        // Register page
        public function register(){
            // Check for post
            if($_SERVER['REQUEST_METHOD'] == 'POST'){
                // Process form

                // Sanitize POST data
                $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);

                // Init data
                $data = [
                    'name' => trim($_POST['name']),
                    'email' => trim($_POST['email']),
                    'password' => trim($_POST['password']),
                    'confirm_password' => trim($_POST['confirm_password']),
                    'name_err' => '',
                    'email_err' => '',
                    'password_err' => '',
                    'confirm_password_err' => ''
                ];

                // Validate email
                if (empty($data['email'])) {
                    $data['email_err'] = 'Please enter email';
                } else {
                    // Check email
                    if ($this->userModel->findUserByEmail($data['email'])){
                        $data['email_err'] = 'Email is already taken';
                    }
                }

                // Validate name
                if (empty($data['name'])) {
                    $data['name_err'] = 'Please enter name';
                }

                // Validate password
                if (empty($data['password'])) {
                    $data['password_err'] = 'Please enter password';
                } elseif(strlen($data['password']) < 6) {
                    $data['password_err'] = 'Password must be at least 6 characters';
                }

                // Validate confirm password
                if (empty($data['confirm_password'])) {
                    $data['confirm_password_err'] = 'Please confirm password';
                } else {
                    if($data['password'] != $data['confirm_password']){
                        $data['confirm_password_err'] = 'Passwords do not match';
                    }
                }

                // Confirm empty errors
                if (empty($data['email_err']) && empty($data['name_err']) && empty($data['password_err']) && empty($data['confirm_password_err'])){
                    // Validated

                    // Hash password
                    $data['password'] = password_hash($data['password'], PASSWORD_DEFAULT);

                    // Register user
                    if ($this->userModel->register($data)) {
                        flash('register_success', 'You are now registered!');
                        redirect('users/login');
                    } else {
                        die('Something went wrong!');
                    }
                } else {
                    // Load view with errors
                    $this->view('users/register', $data);
                }

            } else {
                // Init data
                $data = [
                    'name' => '',
                    'email' => '',
                    'password' => '',
                    'confirm_password' => '',
                    'name_err' => '',
                    'email_err' => '',
                    'password_err' => '',
                    'confirm_password_err' => ''
                ];

                $this->view('users/register', $data);
            }
        }

        // Login page
        public function login(){
            // Check for post
            if($_SERVER['REQUEST_METHOD'] == 'POST'){
                // Process form

                // Sanitize POST data
                $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);

                // Init data
                $data = [
                    'email' => trim($_POST['email']),
                    'password' => trim($_POST['password']),
                    'email_err' => '',
                    'password_err' => '',
                ];

                // Validate email
                if (empty($data['email'])) {
                    $data['email_err'] = 'Please enter email';
                }

                // Validate password
                if (empty($data['password'])) {
                    $data['password_err'] = 'Please enter password';
                }

                // Check for user email
                if ($this->userModel->findUserByEmail($data['email'])){
                    // User found
                } else {
                    // User not found
                    $data['email_err'] = 'User not found';
                }

                // Confirm empty errors
                if (empty($data['email_err']) && empty($data['password_err'])){
                    // Validated
                    // Check and set logged in user
                    $loggedInUser = $this->userModel->login($data['email'], $data['password']);
                    if($loggedInUser){
                        // Create session
                        $this->createUserSession($loggedInUser);
                    } else {
                        $data['password_err'] = 'Password incorrect';
                        $this->view('users/login', $data);
                    }
                } else {
                    // Load view with errors
                    $this->view('users/login', $data);
                }

            } else {
                // Init data
                $data = [
                    'email' => '',
                    'password' => '',
                    'email_err' => '',
                    'password_err' => '',
                ];

                $this->view('users/login', $data);
            }
        }

        // Create user session
        public function createUserSession($user){
            $_SESSION['user_id'] = $user->id;
            $_SESSION['user_email'] = $user->email;
            $_SESSION['user_name'] = $user->name;
            if (!empty($_SESSION['guest_cart'])) {
                $_SESSION['cart'] = $_SESSION['guest_cart'];
                unset($_SESSION['guest_cart']);
            } else {
                $_SESSION['cart'] = array();
            }
            if ($_SESSION['user_email'] == 'admin@spilledcoffee.net') {
                $_SESSION['ADMIN'] = true;
            }
            redirect('products');
        }

        // Logout, destroy session
        public function logout(){
            unset($_SESSION['user_id']);
            unset($_SESSION['user_email']);
            unset($_SESSION['user_name']);
            unset($_SESSION['cart']);
            session_destroy();
            redirect('users/login');
        }

        // Cart page
        public function cart(){
            if(empty($_SESSION['cart']) && empty($_SESSION['guest_cart'])){
                $data = [
                    'product_title' => '',
                    'sale_price' => '',
                    'reserved' => ''
                ];

                $this->view('users/cart', $data);
            } else {

                if (!isset($_SESSION['cart'])) {
                    $cart = $_SESSION['guest_cart'];
                } else {
                    $cart = $_SESSION['cart'];
                }
                
                $data = array();

                foreach ($cart as $cartItem) {
                    $item = $this->productModel->getProductById($cartItem[0]);
                    $data[] = array('product_title' => $item->product_title, 'sale_price' => $item->sale_price, 'reserved' => $cartItem[1]);
                }

                $this->view('users/cart', $data);
            }
            
        }

        // Function that deletes current cart and creates a new one
        // Used after checkout for new cart
        public function emptyCart(){
            unset($_SESSION['cart']);
            $_SESSION['cart'] = array();
        }

        // Remove item from cart
        public function removeCartItem($position){
            if (!isset($_SESSION['cart'])){
                $cart = $_SESSION['guest_cart'];
                $product_id = $_SESSION['guest_cart'][$position][0];
                $quantity = $_SESSION['guest_cart'][$position][1];
                $this->productModel->restoreQuantity($product_id, $quantity);
                \array_splice($_SESSION['guest_cart'], $position, 1);
                flash('cart_message', 'Item removed from cart');
                redirect('users/cart');
            } else {
                $cart = $_SESSION['cart'];
                $product_id = $_SESSION['cart'][$position][0];
                $quantity = $_SESSION['cart'][$position][1];
                $this->productModel->restoreQuantity($product_id, $quantity);
                \array_splice($_SESSION['cart'], $position, 1);
                flash('cart_message', 'Item removed from cart');
                redirect('users/cart');
            }
        }

        // Checkout page
        public function checkout(){
            if(!isLoggedIn()){
                redirect('users/login');
            }
            if($_SERVER['REQUEST_METHOD'] == 'POST'){
                // Process form

                // Sanitize POST data
                $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_SPECIAL_CHARS);

                $data = [
                    'cart' => $_SESSION['cart'],
                    'user_id' => $_SESSION['user_id'],
                    'street' => trim($_POST['street']),
                    'city' => trim($_POST['city']),
                    'state' => trim($_POST['state']),
                    'zip' => trim($_POST['zip']),
                    'order_quantity' => $_POST['total_quantity'],
                    'order_total' => $_POST['total_price']
                ];

                $order_id = $this->orderModel->place($data);
                $data['order_id'] = $order_id; 
                $this->orderModel->storeOrderItems($data);
                $this->emptyCart();

                $orderInfo = $this->orderModel->getOrderById($order_id);
                $orderItems = $this->orderModel->getOrderItemsById($order_id);
                $ordered_at = $orderInfo->ordered_at;
                $order_date = new DateTime($ordered_at);
                $order_date = $order_date->format('d-m-Y');

                foreach ($orderItems as $orderItem){
                    $details = $this->productModel->getProductById($orderItem->product_id);
                    $orderItem->product_title = $details->product_title;
                    $orderItem->sale_price = $details->sale_price;
                }
                
                $emailOK = true;
                ob_start();
                include APPROOT . '/views/inc/confirm_email.php';
                $message = ob_get_clean();
                $to = $_SESSION['user_email'];
                $from = "noreply@spilledcoffee.net";
                $this->sendOrderConfirmationEmail($to, $from, $message);
                $this->sendRecommendationEmail();

                flash('order_message', 'Your order was successfully placed!<br />You will receive a confirmation email shortly');
                redirect('orders/index');

            } else {
                $cart = $_SESSION['cart'];
                $data = array();

                foreach ($cart as $cartItem) {
                    $item = $this->productModel->getProductById($cartItem[0]);
                    $data[] = array('product_title' => $item->product_title, 'sale_price' => $item->sale_price, 'reserved' => $cartItem[1]);
                }

                $this->view('users/checkout', $data);
            }
        }
        
        // Sends order confirmation email to logged in user
        public function sendOrderConfirmationEmail($to, $from, $message){
            $subject = "Order Confimation Details";
            $headers[] = "MIME-Version: 1.0";
            $headers[] = "Content-type: text/html; charset=iso-8859-1";
            $headers[] = 'From: <'.$from.'>';

            mail($to,$subject,$message,implode("\r\n", $headers));
        }

        // Sends order recommendation email to logged in user
        public function sendRecommendationEmail(){
            $data = [
                'cart' => $_SESSION['cart'],
                'user_id' => $_SESSION['user_id'],
            ];

            $orderItems = $this->orderModel->getAllOrderItems();
            $products = $this->productModel->getAllProducts();
            $popularList = array();
            foreach ($products as $product) {
                $popularList[$product->product_id] = 0;
            }
            foreach ($orderItems as $item) {
                foreach ($popularList as $key => $value) {
                    if ($item->product_id == $key) {
                        $popularList[$key] += $item->quantity;
                    }
                }
            }
            arsort($popularList);

            $data['product_list'] = array();

            foreach ($popularList as $key => $value) { 
                $data['product_list'][] = $this->productModel->getProductById($key);
            }

            $emailOK = true;
            ob_start();
            include APPROOT . '/views/inc/recommend_email.php';
            $message = ob_get_clean();
            $to = $_SESSION['user_email'];
            $from = "noreply@spilledcoffee.net";

            $subject = "We found a couple of things you might like...";
            $headers[] = "MIME-Version: 1.0";
            $headers[] = "Content-type: text/html; charset=iso-8859-1";
            $headers[] = 'From: <'.$from.'>';

            mail($to,$subject,$message,implode("\r\n", $headers));
        }

        // Page to test email formatting
        public function emailTest() {
            if(!isLoggedIn()){
                redirect('users/login');
            }
            if(!$_SESSION['ADMIN']){
                redirect('users/login');
            }

            $data = [
                'cart' => $_SESSION['cart'],
                'user_id' => $_SESSION['user_id'],
            ];

            $orderItems = $this->orderModel->getAllOrderItems();
            $products = $this->productModel->getAllProducts();
            $popularList = array();
            foreach ($products as $product) {
                $popularList[$product->product_id] = 0;
            }
            foreach ($orderItems as $item) {
                foreach ($popularList as $key => $value) {
                    if ($item->product_id == $key) {
                        $popularList[$key] += $item->quantity;
                    }
                }
            }
            arsort($popularList);

            $data['product_list'] = array();

            foreach ($popularList as $key => $value) { 
                $data['product_list'][] = $this->productModel->getProductById($key);
            }

            $this->view('users/emailTest', $data);
        }

    }