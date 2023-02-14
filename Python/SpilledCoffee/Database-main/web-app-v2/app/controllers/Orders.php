<?php

    // Orders controller
    // Handles the order page, cancellation, and automatic email


    class Orders extends Controller {

        // Constructor
        public function __construct(){
            if(!isLoggedIn()){
                redirect('users/login');
            }

            $this->productModel = $this->model('Product');
            $this->userModel = $this->model('User');
            $this->orderModel = $this->model('Order');
        }

        // Dafault page
        public function index(){
            $user_id = $_SESSION['user_id'];
            $data = array();
            $orders = $this->orderModel->getOrdersByUserId($user_id);

            if (!$orders) {
                $data = '';
            } else {
                foreach ($orders as $order){
                    $data[] = array(
                        'order_id' => $order->order_id,
                        'ordered_at' => $order->ordered_at,
                        'order_status' => $order->order_status,
                        'order_quantity' => $order->order_quantity,
                        'order_total' => $order->order_total,
                    );
                }
            }
            $this->view('orders/index', $data);
        }

        // Show an individual order
        public function show($order_id){
            if(!isLoggedIn()){
                redirect('users/login');
            }

            $user_id = $_SESSION['user_id'];
            $orderInfo = $this->orderModel->getOrderById($order_id);
            
            if($orderInfo->user_id != $user_id){
                redirect('orders/index');
            }

            $ordered_at = $orderInfo->ordered_at;
            date_default_timezone_set('America/Denver');
            $time = new DateTime($ordered_at);
            $time->add(new DateInterval('PT23H'));
            $now = new DateTime();
            $cancel_window = $time->diff($now);
            $timer_hrs = $cancel_window->h;
            $timer_min = $cancel_window->i;

            $data = [
                'time' => $time,
                'now' => $now,
                'timer_hrs' => $timer_hrs,
                'timer_min' => $timer_min,
                'cancel_window' => $cancel_window,
                'order_id' => $orderInfo->order_id,
                'ordered_at' => $orderInfo->ordered_at,
                'order_status' => $orderInfo->order_status,
                'order_quantity' => $orderInfo->order_quantity,
                'order_total' => $orderInfo->order_total,
                'shipping_street' => $orderInfo->shipping_street,
                'shipping_city' => $orderInfo->shipping_city,
                'shipping_state' => $orderInfo->shipping_state,
                'shipping_zipcode' => $orderInfo->shipping_zipcode,
                'order_items' => array()
            ];

            $orderItems = $this->orderModel->getOrderItemsById($order_id);

            foreach ($orderItems as $orderItem){
                $details = $this->productModel->getProductById($orderItem->product_id);
                $orderItem->product_title = $details->product_title;
                $orderItem->sale_price = $details->sale_price;
                $data['order_items'][] = $orderItem;
            }

            $this->view('orders/show', $data);
            
        }

        // Cancel an order from the show page
        public function cancel($order_id){
            if(!isLoggedIn()){
                redirect('users/login');
            }

            $orderInfo = $this->orderModel->getOrderById($order_id);
            $orderItems = $this->orderModel->getOrderItemsById($order_id);
            $user_id = $_SESSION['user_id'];
            $ordered_at = $orderInfo->ordered_at;
            $order_date = new DateTime($ordered_at);
            $order_date = $order_date->format('d-m-Y');

            if($orderInfo->user_id != $user_id){
                redirect('orders');
            }

            if($orderInfo->order_status == 'CANCELLED'){
                flash('order_message', 'This order has already been cancelled.');
                redirect('orders');
            }

            if ($this->orderModel->statusCancelled($order_id)){
                foreach ($orderItems as $orderItem){
                    $this->productModel->restoreQuantity($orderItem->product_id, $orderItem->quantity);
                    $details = $this->productModel->getProductById($orderItem->product_id);
                    $orderItem->product_title = $details->product_title;
                    $orderItem->sale_price = $details->sale_price;
                }
            }

            $emailOK = true;
            ob_start();
            include APPROOT . '/views/inc/cancel_email.php';
            $message = ob_get_clean();
            $to = $_SESSION['user_email'];
            $from = "noreply@spilledcoffee.net";
            $this->sendOrderCancellationEmail($to, $from, $message);

            flash('order_message', 'Your order has been cancelled successfully.');
            redirect('orders/index');
        }

        // Order cancellation function, sends email
        public function sendOrderCancellationEmail($to, $from, $message){
            $subject = "Order Cancellation Details";
            $headers[] = "MIME-Version: 1.0";
            $headers[] = "Content-type: text/html; charset=iso-8859-1";
            $headers[] = 'From: <'.$from.'>';

            mail($to,$subject,$message,implode("\r\n", $headers));
        }
    }