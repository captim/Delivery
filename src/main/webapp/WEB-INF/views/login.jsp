<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
    <head>
        <title>Login</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    </head>
    <body>
        <div class="row justify-content-center" style="padding-top: 60px">
            <div class="col-lg-3 col-md-6 col-10">
                <form>
                    <div class="mb-3">
                        <label for="usernameInput" class="form-label dy-3">Username</label>
                        <input type="text" class="form-control dy-3" id="usernameInput">
                    </div>
                    <div class="mb-3">
                        <label for="passwordInput" class="form-label dy-3">Password</label>
                        <input type="password" class="form-control dy-3" id="passwordInput">
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
</html>
<!--Как оказалось у спринга есть своя форма для логина, поэтому мы будем использовать её))), а эта будет как пример использования бутстрапа -->