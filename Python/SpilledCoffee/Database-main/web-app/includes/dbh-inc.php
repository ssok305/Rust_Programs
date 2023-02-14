<?php

    $servname = "";
    $dbuser = "";
    $dbpsw = "";
    $dbname = "";

    $conn = mysqli_connect($servname, $dbuser, $dbpsw, $dbname);

    if (!$conn) {
        die("Connection failed: " . mysqli_connect_error());
    }
