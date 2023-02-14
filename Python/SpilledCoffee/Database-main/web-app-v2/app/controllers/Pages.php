<?php

    // Static pages that don't require functions

    class Pages extends Controller {

        // Constructor
        public function __construct(){
            
        }

        // Home page
        public function index(){
            $data = [
                'title' => 'WELCOME',
            ];

            $this->view('pages/index', $data);
        }

        // About page
        public function about(){
            $data = [
                'title' => 'About',
            ];

            $this->view('pages/about', $data);
        }

    }