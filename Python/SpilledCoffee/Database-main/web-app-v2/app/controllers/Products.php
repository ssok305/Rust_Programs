<?php

    // Product controller
    // Handles product page, individual product page, and add to cart

    class Products extends Controller {

        // Constructor
        public function __construct(){
            $this->productModel = $this->model('Product');
            $this->userModel = $this->model('User');
        }

        // Main product page
        public function index(){
            $products = $this->productModel->getAllProducts();

            $data = [
                'products' => $products
            ];

            $this->view('products/index', $data);
        }

        // Show individual product page
        public function show($id){
            $product = $this->productModel->getProductById($id);
            if (empty($product)){
                redirect('products/index');
            } else {

                $data = [
                    'product' => $product,
                ];

                $this->view('products/show', $data);
            }
        }

        // Add product to session cart
        public function addToCart($id){
            if ($_SERVER['REQUEST_METHOD'] == 'POST'){
                $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_NUMBER_INT);
                $product = $this->productModel->getProductById($id);
                $data = [
                    'product' => $product,
                    'product_id' => $id,
                    'quantity' => trim($_POST['quantity']),
                    'user_id' => $_SESSION['user_id'],
                    'product_err' => '',
                    'quantity_err' => ''
                ];

                // Validate product id

                // Validate quantity
                if (empty($data['quantity']) || $data['quantity'] == 0){
                    $data['quantity_err'] = 'You must select a quantity';
                }

                if (empty($data['product_err']) && empty($data['quantity_err'])){
                    // Validated
                    if (!isset($_SESSION['cart'])){
                        $_SESSION['guest_cart'][] = array($data['product_id'], $data['quantity']);
                    } else {
                        $_SESSION['cart'][] = array($data['product_id'], $data['quantity']);
                    }
                    $this->productModel->reduceQuantity($id, $data['quantity']);
                    flash('product_message', 'Added item to cart');
                    redirect('products');
                } else {
                    // Load view with errors
                    $this->view('products/show', $data);
                }
            } else {
                $product = $this->productModel->getProductById($id);

                $data = [
                    'product' => $product,
                ];

                $this->view('products/show', $data);
            }
        }
    }