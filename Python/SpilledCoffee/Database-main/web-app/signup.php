<div class="signup-modal hidden">
    <div class="close-signup-modal">&times;</div>
    <div class="signup-form">
        <form method="post" action="includes/signup-inc.php">
            <label for="fname"><b>First Name</b></label>
            <input type="text" placeholder="First name..." name="fname" required>
            <label for="email"><b>Email</b></label>
            <input type="text" placeholder="Email..." name="email" required>
            <label for="uname"><b>Username</b></label>
            <input type="text" placeholder="Username..." name="uname" required>
            <label for="psw"><b>Password</b></label>
            <input type="password" placeholder="Password..." name="psw" required>
            <label for="pswrep"><b>Repeat Password</b></label>
            <input type="password" placeholder="Repeat password..." name="pswrep" required>
            <button type="submit" name= "submit" class="btn modal-btn">Sign Up</button>
        </form>
    </div>
</div>
<div class="overlay hidden"></div>