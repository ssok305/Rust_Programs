<?php

    // Admin controller
    // Handles the admin dashboard and marketing reports page

    class Admins extends Controller {

        // Constructor
        public function __construct(){
            if(!isLoggedIn()){
                redirect('users/login');
            }

            if(!$_SESSION['ADMIN']){
                redirect('products/index');
            }

            $this->productModel = $this->model('Product');
            $this->userModel = $this->model('User');
            $this->orderModel = $this->model('Order');
            $this->adminModel = $this->model('Admin');
        }

        // Main admin dashboard
        public function index(){
            if ($_SERVER['REQUEST_METHOD'] == 'POST'){
                
                $startDate = $_POST['date-range-start'];
                $endDate = $_POST['date-range-end'];

                $startObj = new DateTime($startDate);
                $endObj = new DateTime($endDate);

                if ($startDate > $endDate){
                    $date_err = 'Your start date cannot be after your end date.';
                } else {
                    $date_err = '';
                }

                $data = array();

                if (empty($date_err)){

                    $data = [
                        'top_products' => $this->adminModel->getTopProductsAllTime($startDate, $endDate),
                        'start_date' => $startDate,
                        'end_date' => $endDate,
                        'date_err' => $date_err
                    ];
    
                    $this->view('admin/index', $data);
                } else {
                    $this->view('admin/index', $data);
                }

            } else {
                $user_id = $_SESSION['user_id'];
                $data = array();

                $orderItems = $this->orderModel->getAllOrderItems();
                $products = $this->productModel->getAllProducts();
                $popularList = array();
                foreach ($products as $product) {
                    $popularList[$product->product_id] = array();
                    $popularList[$product->product_id]['quantity'] = 0;
                }
                foreach ($orderItems as $item) {
                    foreach ($popularList as $key => $value) {
                        if ($item->product_id == $key) {
                            $popularList[$key]['quantity'] += $item->quantity;
                        }
                    }
                }
                foreach ($products as $product) {
                    foreach ($popularList as $key => $value) {
                        if ($product->product_id == $key) {
                            $popularList[$key]['title'] = $product->product_title;
                        }
                    }
                }
                arsort($popularList);
                $data['popular_list'] = $popularList;

                // $data['product_list'] = array();

                // foreach ($popularList as $key => $value) { 
                //     $data['product_list'][] = $this->productModel->getProductById($key);
                // }
                
                $this->view('admin/index', $data);
            }
        }

        // Reports page, shows top products and customers
        public function reports(){

            // If search button was pressed
            if ($_SERVER['REQUEST_METHOD'] == 'POST'){
                
                // Validate date range or set error message
                if (empty($_POST['date-range-start'])) {
                    $date_err = 'You must enter a start date and end date range.';
                } else {
                    $startDate = $_POST['date-range-start'];
                    $startObj = new DateTime($startDate);
                    $date_err = '';
                }

                if (empty($_POST['date-range-end'])) {
                    $date_err = 'You must enter a start date and end date range.';
                } else {
                    $endDate = $_POST['date-range-end'];
                    $endObj = new DateTime($endDate);
                    $date_err = '';
                }

                if (!empty($_POST['date-range-start']) && !empty($_POST['date-range-end']) && $startDate > $endDate){
                    $date_err = 'Your start date cannot be after your end date.';
                }

                // Validate results count or set error message
                if (!isset($_POST['top'])) {
                    $top_err = "You must select a number of results to return.";
                } else {
                    $top_err = "";
                    $limit = $_POST['top'];
                }

                if (!isset($_POST['source'])) {
                    $source_err = "You must choose a source.";
                } else {
                    $source_err = "";
                    $source = $_POST['source'];
                }

                $data = array();

                // If no errors, get marketing data from model and load view
                if (empty($date_err) && empty($top_err) && empty($source_err)){

                    $data = [
                        'start_date' => $startDate,
                        'end_date' => $endDate,
                        'date_err' => $date_err
                    ];

                    if ($limit == 'top-10') {
                        $data['top_products'] = $this->adminModel->getTop10Products($startDate, $endDate, $source);
                        $data['top_customers'] = $this->adminModel->getTop10Customers($startDate, $endDate, $source);
                    } else {
                        $data['top_products'] = $this->adminModel->getTop25Products($startDate, $endDate, $source);
                        $data['top_customers'] = $this->adminModel->getTop25Customers($startDate, $endDate, $source);
                    }
    
                    $this->view('admin/reports', $data);

                } else {

                    $data = [
                        'date_err' => $date_err,
                        'top_err' => $top_err,
                        'source_err' => $source_err
                    ];

                    $this->view('admin/reports', $data);
                }

            } else {
                $date_err = '';
                $top_err = '';

                $data = [
                    'top_products' => $this->adminModel->getTop10Products(),
                    'top_customers' => $this->adminModel->getTop10Customers(),
                    'total_sold' => $this->adminModel->getTotalItemsSold(),
                    'start_date' => '',
                    'end_date' => '',
                    'date_err' => $date_err,
                    'top_err' => $top_err
                ];

                $this->view('admin/reports', $data);

            }
        }
    }