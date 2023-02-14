<?php require APPROOT . '/views/inc/header_admin.php'; ?>
<div class="container-fluid m-0 p-0">
    <div class="row w-100">
        <div class="col-3" style="height: 100vh;">
            <div class="d-flex flex-column p-3 text-white bg-dark h-100" style="position: fixed; width: 280px;">
            <a href="/" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none">
                <svg class="bi me-2" width="40" height="32"><use xlink:href="#bootstrap"></use></svg>
                <span class="fs-4">Admin</span>
            </a>
            <hr>
            <ul class="nav nav-pills flex-column mb-auto">
                <li class="nav-item">
                <a href="<?php echo URLROOT; ?>/admins" class="nav-link text-white">
                    Home
                </a>
                </li>
                <li>
                <a href="<?php echo URLROOT; ?>/admins/reports" class="nav-link active">
                    Reports
                </a>
                </li>
                <li>
                <a href="<?php echo URLROOT; ?>" class="nav-link text-white">
                    Exit Dashboard
                </a>
                </li>
                <li>
                <a href="<?php echo URLROOT; ?>/users/logout" class="nav-link text-white">
                    Log Out
                </a>
                </li>
            </ul>
            <hr>
        </div>
    </div>
    <div class="col-9 p-5">
        <div class="container-fluid border border-2 mx-auto bg-white shadow w-100 h-100 rounded-3" style="border-radius: 20px;">
            <div class="container p-4 bg-secondary text-white rounded-3 mt-3">
                <p class="m-0 p-0 fs-4">Date Range:</p>
                <form action="<?php echo URLROOT; ?>/admins/reports" method="POST">
                    <br />Between <input type="date" name="date-range-start" id="date-range-start" class="rounded-3 <?php echo (!empty($data['date_err'])) ? 'is-invalid' : '';?>" />
                    and <input type="date" name="date-range-end" id="date-range-end" class="rounded-3 <?php echo (!empty($data['password_err'])) ? 'is-invalid' : '';?>" />
                    <span class="invalid-feedback bg-danger text-white rounded-3 p-2 mt-2"><?php echo $data['date_err']; ?></span>
                    <br class="m-0 p-0">
                    <label class="d-inline">Results:</label>
                    <input class="d-inline-block my-3 ms-4" type="radio" id="top-10" name="top" value="top-10" checked>
                    <label class="d-inline" for="top-10">Top 10</label>
                    <input class="d-inline my-3 ms-4" type="radio" id="top-25" name="top" value="top-25">
                    <label for="top-25">Top 25</label>
                    <?php if (!empty($data['top_err'])) : ?>
                        <span class="invalid-feedback bg-danger text-white rounded-3 p-2 mt-2"><?php echo $data['top_err']; ?></span>
                    <?php endif; ?>
                    <br class="m-0 p-0">
                    <label class="d-inline">Source:</label>
                    <input class="d-inline-block mt-1 mb-1 ms-4" type="radio" id="internal" name="source" value="internal" checked>
                    <label class="d-inline" for="internal">Internal</label>
                    <input class="d-inline my-1 ms-4" type="radio" id="external" name="source" value="external">
                    <label for="external">External</label>
                    <?php if (!empty($data['source_err'])) : ?>
                        <span class="invalid-feedback bg-danger text-white rounded-3 p-2 mt-2"><?php echo $data['source_err']; ?></span>
                    <?php endif; ?>
                    <div class="d-block">
                        <button type="submit" class="d-inline btn btn-success mt-3">Search</button>
                    </div>
                </form>
            </div>
            <!-- <?php print_r($_POST); ?> -->
            <!-- <?php print_r($data); ?> -->
        <?php if (isset($data['top_products']) && isset($data['top_customers']) && !empty($data['top_products'])) : ?>
            <script type="text/javascript">

                // Load the Visualization API and the corechart package.
                google.charts.load('current', {'packages':['corechart']});

                // Set a callback to run when the Google Visualization API is loaded.
                google.charts.setOnLoadCallback(drawTopProducts);
                google.charts.setOnLoadCallback(drawTopCustomers);

                // Callback that creates and populates a data table,
                // instantiates the pie chart, passes in the data and
                // draws it.
                function drawTopProducts() {

                    // Create the data table.
                    var data = new google.visualization.DataTable();
                    data.addColumn('string', 'Product ID');
                    data.addColumn('number', '# of Items Sold');
                    data.addRows([
                        <?php if (isset($data['top_customers'])) : ?>
                            <?php foreach ($data['top_products'] as $product) : ?>
                                <?php if (!empty($product->product_id)) : ?>
                                    [<?php echo '"'.$product->product_id.'", '.$product->total_quantity; ?>],
                                <?php endif; ?>
                            <?php endforeach; ?>
                        <?php endif; ?>
                    ]);

                    // Set chart options
                    var options = {
                        'title':'Most Popular Items (Quantity Sold)',
                        'titleTextStyle': {
                            color: "#333333",
                            fontName: "sans-serif",
                            fontSize: 22,
                            bold: false,
                            italic: false
                        },
                        'width':'100%',
                        'height': 800,
                        'colors': ['#d1a55a', '#e6693e'],
                        hAxis: {
                            viewWindow: {
                                min: 0
                            }
                        }
                    }

                    // Instantiate and draw our chart, passing in some options.
                    var chart = new google.visualization.BarChart(document.getElementById('top_products_chart'));
                    chart.draw(data, options);
                }

                function drawTopCustomers() {

                    // Create the data table.
                    var data = new google.visualization.DataTable();
                    data.addColumn('string', 'Customer ID');
                    data.addColumn('number', '# of Items Purchased');
                    data.addRows([
                        <?php if (isset($data['top_customers'])) : ?>
                            <?php foreach ($data['top_customers'] as $customer) : ?>
                                <?php if (!empty($customer->user_id)) : ?>
                                    [<?php echo '"'.$customer->user_id.'", '.$customer->total_quantity; ?>],
                                <?php endif; ?>
                            <?php endforeach; ?>
                        <?php endif; ?>
                    ]);

                    // Set chart options
                    var options = {
                        'title':'Top Customers (Total Items Purchased)',
                        'titleTextStyle': {
                            color: "#333333",
                            fontName: "sans-serif",
                            fontSize: 22,
                            bold: false,
                            italic: false
                        },
                        'width':'100%',
                        'height':800,
                        'colors': ['#d1a55a', '#e6693e'],
                    }

                    // Instantiate and draw our chart, passing in some options.
                    var chart = new google.visualization.BarChart(document.getElementById('top_customers_chart'));
                    chart.draw(data, options);
                }

                window.onresize = reDraw;
                function reDraw() {
                    drawTopCustomers();
                    drawTopProducts();
                }

            </script>
            <div class="container mx-auto overflow-auto border border-2 rounded-3 my-3 p-0">
                <?php if (empty($data['start_date'])) : ?>
                    <p class="m-3 fs-5">Showing all time results</p>
                <?php else : ?>
                    <p class="m-3 fs-5">Showing results for <?php echo $data['start_date']; ?> to <?php echo $data['end_date']; ?></p>
                <?php endif; ?>
            </div>
            <div class="container m-0 p-0 text-end">
                <button class="d-inline btn btn-success m-0" id="exportReports">Export</button>
            </div>
            <div id="capture">
                <div class="chart container mx-auto border border-2 rounded-3 my-3" id="top_products_chart"></div>
                <div class="chart container mx-auto border border-2 rounded-3 my-3" id="top_customers_chart"></div>
            </div>
        </div>
        <?php else: ?>
            <div class="container mx-auto text-center overflow-auto border border-2 rounded-3 my-3 p-0">
                <p class="pt-3 fs-3">There is no data to display.</p>
            </div>
        <?php endif; ?>

    </div>
</div>

<?php require APPROOT . '/views/inc/footer_admin.php'; ?>