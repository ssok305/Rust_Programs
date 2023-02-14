<!-- <div class="login-modal hidden">
    <div class="close-login-modal">&times;</div>
    <div class="login-modal-message" id="login-message">
        <p>That username/password combination does not exist. Try again or sign up!</p>
    </div>
    <div class="login-form">
        <form method="post" action="includes/login-inc.php">
            <label for="uname"><b>Username</b></label>
            <input type="text" placeholder="Username/Email..." name="uid" required>
            <label for="psw"><b>Password</b></label>
            <input type="password" placeholder="Password..." name="psw" required>
            <button type="submit" name="submit" class="btn modal-btn">Log in</button>
        </form>
    </div>
</div>
<div class="overlay hidden"></div> -->
<div class="login-modal" id="login-modal">
    <div class="close-login-modal">X</div>
    <div class="login-form">
        <form id="home-login" method="POST" action="includes/login-inc-test.php">
            <label for="uname"><b>Username</b></label>
            <input class = "login-input" type="text" placeholder="Username/Email..." name="uid" id="uid" required>
            <label for="psw"><b>Password</b></label>
            <input class = "login-input" type="password" placeholder="Password..." name="psw" id="psw" required>
            <div class="login-modal-message" id="login-message"></div>
            <button onclick="submitLogin()" type="submit" formmethod="POST" name="submit" id="submit" class="btn modal-btn">Log in</button>
        </form>
    </div>
</div>
<div class="overlay hidden" id="overlay"></div>